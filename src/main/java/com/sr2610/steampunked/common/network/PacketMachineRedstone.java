package com.sr2610.steampunked.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.tileentities.TileEntityMachine;

public class PacketMachineRedstone extends AbstractPacket {

	int x, y, z, mode;

	public PacketMachineRedstone() {

	}

	public PacketMachineRedstone(int x, int y, int z, int mode) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(mode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		mode = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		final World world = player.worldObj;
		final TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof TileEntityMachine)
			((TileEntityMachine) te).setRedstoneMode(mode);
	}
}
