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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;

import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketPunchcardMaker extends AbstractPacket {

	int x, y, z;
	ItemStack contents;

	public PacketPunchcardMaker() {

	}

	public PacketPunchcardMaker(int x, int y, int z, ItemStack contents) {
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

		if (te instanceof TileEntityPunchardMaker) {
			ItemStack is = ((TileEntityPunchardMaker) te).getStackInSlot(3);
			is.stackSize--;
			((TileEntityPunchardMaker) te)
					.setInventorySlotContents(2, contents);
			((TileEntityPunchardMaker) te).setInventorySlotContents(0, null);
			((TileEntityPunchardMaker) te).setInventorySlotContents(3, is);

		}
	}
}
