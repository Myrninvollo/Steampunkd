package com.sr2610.steampunked.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketTinkerTable extends AbstractPacket {

	int x, y, z;
	ItemStack contents;

	public PacketTinkerTable() {

	}

	public PacketTinkerTable(int x, int y, int z, ItemStack contents) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.contents = contents;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		ByteBufUtils.writeItemStack(buffer, contents);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		contents = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		World world = player.worldObj;
		TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof TileEntityTinkerBench) {
			((TileEntityTinkerBench) te).setInventorySlotContents(8, contents);
			((TileEntityTinkerBench) te).setInventorySlotContents(0, null);

		}
	}
}
