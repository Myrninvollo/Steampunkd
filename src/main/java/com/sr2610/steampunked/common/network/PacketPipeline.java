/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import com.sr2610.steampunked.Steampunked;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@ChannelHandler.Sharable
public class PacketPipeline extends
		MessageToMessageCodec<FMLProxyPacket, AbstractPacket> {

	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private final LinkedList<Class<? extends AbstractPacket>> packets = new LinkedList<Class<? extends AbstractPacket>>();
	private boolean isPostInitialised = false;

	/**
	 * Register your packet with the pipeline. Discriminators are automatically
	 * set.
	 * 
	 * @param clazz
	 *            the class to register
	 * 
	 * @return whether registration was successful. Failure may occur if 256
	 *         packets have been registered or if the registry already contains
	 *         this packet
	 */
	public boolean registerPacket(Class<? extends AbstractPacket> clazz) {
		if (packets.size() > 256)
			// You should log here!!
			return false;

		if (packets.contains(clazz))
			// You should log here!!
			return false;

		if (isPostInitialised)
			// You should log here!!
			return false;

		packets.add(clazz);
		return true;
	}

	// In line encoding of the packet, including discriminator setting
	@Override
	protected void encode(ChannelHandlerContext ctx, AbstractPacket msg,
			List<Object> out) throws Exception {
		final ByteBuf buffer = Unpooled.buffer();
		final Class<? extends AbstractPacket> clazz = msg.getClass();
		if (!packets.contains(msg.getClass()))
			throw new NullPointerException("No Packet Registered for: "
					+ msg.getClass().getCanonicalName());

		final byte discriminator = (byte) packets.indexOf(clazz);
		buffer.writeByte(discriminator);
		msg.encodeInto(ctx, buffer);
		final FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(),
				ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		out.add(proxyPacket);
	}

	// In line decoding and handling of the packet
	@Override
	protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg,
			List<Object> out) throws Exception {
		final ByteBuf payload = msg.payload();
		final byte discriminator = payload.readByte();
		final Class<? extends AbstractPacket> clazz = packets
				.get(discriminator);
		if (clazz == null)
			throw new NullPointerException(
					"No packet registered for discriminator: " + discriminator);

		final AbstractPacket pkt = clazz.newInstance();
		pkt.decodeInto(ctx, payload.slice());

		EntityPlayer player;
		switch (FMLCommonHandler.instance().getEffectiveSide()) {
		case CLIENT:
			player = getClientPlayer();
			pkt.handleClientSide(player);
			break;

		case SERVER:
			final INetHandler netHandler = ctx.channel()
					.attr(NetworkRegistry.NET_HANDLER).get();
			player = ((NetHandlerPlayServer) netHandler).playerEntity;
			pkt.handleServerSide(player);
			break;

		default:
		}

		out.add(pkt);
	}

	// Method to call from FMLInitializationEvent
	public void initalise() {
		channels = NetworkRegistry.INSTANCE.newChannel(Steampunked.STEAMPUNKED,
				this);
		registerPackets();
	}

	public void registerPackets() {

		registerPacket(PacketTinkerTable.class);
		registerPacket(PacketPunchcardMaker.class);
		registerPacket(PacketRedstoneMode.class);

	}

	// Method to call from FMLPostInitializationEvent
	// Ensures that packet discriminators are common between server and client
	// by using logical sorting
	public void postInitialise() {
		if (isPostInitialised)
			return;

		isPostInitialised = true;
		Collections.sort(packets,
				new Comparator<Class<? extends AbstractPacket>>() {

					@Override
					public int compare(Class<? extends AbstractPacket> clazz1,
							Class<? extends AbstractPacket> clazz2) {
						int com = String.CASE_INSENSITIVE_ORDER.compare(
								clazz1.getCanonicalName(),
								clazz2.getCanonicalName());
						if (com == 0)
							com = clazz1.getCanonicalName().compareTo(
									clazz2.getCanonicalName());

						return com;
					}
				});
	}

	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	/**
	 * Send this message to everyone.
	 * <p/>
	 * Adapted from CPW's code in
	 * cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 */
	public void sendToAll(AbstractPacket message) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to the specified player.
	 * <p/>
	 * Adapted from CPW's code in
	 * cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 * @param player
	 *            The player to send it to
	 */
	public void sendTo(AbstractPacket message, EntityPlayerMP player) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channels.get(Side.SERVER)
				.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 * <p/>
	 * Adapted from CPW's code in
	 * cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 * @param point
	 *            The
	 *            {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint}
	 *            around which to send
	 */
	public void sendToAllAround(AbstractPacket message,
			NetworkRegistry.TargetPoint point) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		channels.get(Side.SERVER)
				.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to everyone within the supplied dimension.
	 * <p/>
	 * Adapted from CPW's code in
	 * cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 * @param dimensionId
	 *            The dimension id to target
	 */
	public void sendToDimension(AbstractPacket message, int dimensionId) {
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		channels.get(Side.SERVER)
				.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)
				.set(dimensionId);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to the server.
	 * <p/>
	 * Adapted from CPW's code in
	 * cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 */
	public void sendToServer(AbstractPacket message) {
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeAndFlush(message);
	}
}
