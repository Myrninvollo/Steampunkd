/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.tileentities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySteamBlaster extends TileEntity {

	public void updateEntity() {
		if (!worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord,
				this.zCoord)) {
			addEntityYSpeed();
		}
	}

	public void addEntityYSpeed() {
		AxisAlignedBB area;
		int range=1;
		switch (this.blockMetadata) {
		case 0:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord, (double) this.yCoord-1,
							(double) this.zCoord, (double) (this.xCoord),
							(double) (this.yCoord-1), (double) (this.zCoord))
					.expand(range, range, range);
		case 1:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord, (double) this.yCoord+1,
							(double) this.zCoord, (double) (this.xCoord),
							(double) (this.yCoord+1),
							(double) (this.zCoord))
					.expand(range, range, range);

		case 2:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord, (double) this.yCoord,
							(double) this.zCoord-1, (double) (this.xCoord),
							(double) (this.yCoord),
							(double) (this.zCoord-1))
					.expand(range, range, range);

		case 3:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord, (double) this.yCoord,
							(double) this.zCoord+1, (double) (this.xCoord),
							(double) (this.yCoord),
							(double) (this.zCoord+1))
					.expand(range, range, range);
		case 4:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord-1, (double) this.yCoord,
							(double) this.zCoord, (double) (this.xCoord-1),
							(double) (this.yCoord),
							(double) (this.zCoord))
					.expand(range, range, range);
		case 5:
			area = AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord+1, (double) this.yCoord,
							(double) this.zCoord, (double) (this.xCoord+1),
							(double) (this.yCoord),
							(double) (this.zCoord))
					.expand(range+4, range, range);
		default:
			area =  AxisAlignedBB
					.getAABBPool()
					.getAABB((double) this.xCoord, (double) this.yCoord,
							(double) this.zCoord, (double) (this.xCoord),
							(double) (this.yCoord),
							(double) (this.zCoord))
					.expand(range, range, range);

		}
		List inarea = this.worldObj.getEntitiesWithinAABB(Entity.class, area);
		Iterator iterator = inarea.iterator();
		Entity entity;

		

		while (iterator.hasNext()) {
			entity = (Entity) iterator.next();
			if (this.getBlockMetadata() == 0) {
				entity.motionY=-1;
			} else if (this.getBlockMetadata() == 1) {
				entity.motionY=+1;
			} else if (this.getBlockMetadata() == 2) {
				entity.motionZ=-1;
			} else if (this.getBlockMetadata() == 3) {
				entity.motionZ=+1;
			}else if (this.getBlockMetadata() == 4) {
				entity.motionX=-1;
			}else if (this.getBlockMetadata() == 5) {
				entity.motionX=+1;
			}

		}
	}
}
