/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractPacket {

	/**
	 * Encode the packet data into the ByteBuf stream. Complex data sets may
	 * need specific data handlers (See
	 * 
	 * @link{cpw.mods.fml.common.network.ByteBuffUtils )
	 * 
	 * @param ctx
	 *            channel context
	 * @param buffer
	 *            the buffer to encode into
	 */
	public abstract void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer);

	/**
	 * Decode the packet data from the ByteBuf stream. Complex data sets may
	 * need specific data handlers (See
	 * 
	 * @link{cpw.mods.fml.common.network.ByteBuffUtils )
	 * 
	 * @param ctx
	 *            channel context
	 * @param buffer
	 *            the buffer to decode from
	 */
	public abstract void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer);

	/**
	 * Handle a packet on the client side. Note this occurs after decoding has
	 * completed.
	 * 
	 * @param player
	 *            the player reference
	 */
	public abstract void handleClientSide(EntityPlayer player);

	/**
	 * Handle a packet on the server side. Note this occurs after decoding has
	 * completed.
	 * 
	 * @param player
	 *            the player reference
	 */
	public abstract void handleServerSide(EntityPlayer player);
}
