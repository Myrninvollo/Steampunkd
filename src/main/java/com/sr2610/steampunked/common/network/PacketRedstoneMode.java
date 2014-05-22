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
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.api.utils.IRedstoneControl;
import com.sr2610.steampunked.api.utils.IRedstoneControl.ControlMode;

public class PacketRedstoneMode extends AbstractPacket {

	int x, y, z, mode;

	public PacketRedstoneMode() {

	}

	public PacketRedstoneMode(int x, int y, int z, int mode) {
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

		if (te instanceof IRedstoneControl)
			((IRedstoneControl) te).setControl(getModeFromInt());
		
		System.out.println(mode);
	}

	private ControlMode getModeFromInt() {
		switch (mode){
		case 0: return ControlMode.DISABLED;
		case 1: return ControlMode.LOW;
		case 2: return ControlMode.HIGH;

		}
		return ControlMode.DISABLED;
	}
	

}
