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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntitySteamBlaster extends TileEntity {
	public double direction;
	public boolean mode;
	public float speed = 1.0F;

	public TileEntitySteamBlaster() {
		if (direction != 1.0D && direction != -1D)
			direction = 1.0D;

		mode = true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			return;

		if (worldObj.rand.nextInt(2) == 0)
			spawnParticle(worldObj, xCoord, yCoord, zCoord);

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(null,
				getDirection());

		if (!list.isEmpty())
			for (int i = 0; i < list.size(); i++) {
				Entity entity = (Entity) list.get(i);
				int j = getBlockMetadata();
				double d = 0.050000000000000003D;
				double d1 = 0.29999999999999999D;
				d *= direction;
				d *= speed;

				if (entity instanceof EntityItem) {
					d1 *= 1.8D;
					d *= 1.3D;
				}

				if (!(entity instanceof EntityItem) && !mode)
					d = 0.0D;

				if (entity instanceof EntityMinecart)
					d *= 0.5D;

				if (entity instanceof EntityFallingBlock && j == 1)
					d = 0.0D;

				if (!isPathClear(entity, j))
					continue;

				if (j == 0 && entity.motionY > -d1)
					entity.motionY += -d;

				if (j == 1 && entity.motionY < d1 * 0.5D)
					entity.motionY += d + 0.05;

				if (j == 2 && entity.motionZ > -d1)
					entity.motionZ += -d;

				if (j == 3 && entity.motionZ < d1)
					entity.motionZ += d;

				if (j == 4 && entity.motionX > -d1)
					entity.motionX += -d;

				if (j == 5 && entity.motionX < d1)
					entity.motionX += d;
			}
	}

	public boolean isPathClear(Entity entity, int i) {
		int j = 0;

		if (i == 0 || i == 1) {
			int k = MathHelper.floor_double(entity.posY);
			int j1 = k - yCoord;
			j = j1 <= 0 ? -j1 : j1;
		}

		if (i == 2 || i == 3) {
			int l = MathHelper.floor_double(entity.posZ);
			int k1 = l - zCoord;
			j = k1 <= 0 ? -k1 : k1;
		}

		if (i == 4 || i == 5) {
			int i1 = MathHelper.floor_double(entity.posX);
			int l1 = i1 - xCoord;
			j = l1 <= 0 ? -l1 : l1;
		}

		World world = worldObj;
		int i2 = xCoord;
		int j2 = yCoord;
		int k2 = zCoord;
		boolean flag = true;

		for (int l2 = 0; l2 < j; l2++) {
			if (i == 0)
				j2--;

			if (i == 1)
				j2++;

			if (i == 2)
				k2--;

			if (i == 3)
				k2++;

			if (i == 4)
				i2--;

			if (i == 5)
				i2++;

			if (world.isBlockNormalCubeDefault(i2, j2, k2, false))
				flag = false;
		}

		return flag;
	}

	public String getSliderDisplay() {
		float f = speed;
		f *= 100F;
		f = Math.round(f) / 100F;
		return String.valueOf(f);
	}

	public AxisAlignedBB getDirection() {
		int i = getBlockMetadata();
		worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		double d = 5D;

		if (i == 0)
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord - d, zCoord,
					xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D);

		if (i == 1)
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord,
					xCoord + 1.0D, yCoord + 1.0D + d, zCoord + 1.0D);

		if (i == 2)
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord - d,
					xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D);

		if (i == 3)
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord,
					xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D + d);

		if (i == 4)
			return AxisAlignedBB.getBoundingBox(xCoord - d, yCoord, zCoord,
					xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D);

		if (i == 5)
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord
					+ 1.0D + d, yCoord + 1.0D, zCoord + 1.0D);
		else
			return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord,
					xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D);
	}

	public void spawnParticle(World world, int par2, int par3, int par4) {
		double var7 = par2 + worldObj.rand.nextFloat();
		double var9 = par3 + worldObj.rand.nextFloat();
		double var11 = par4 + worldObj.rand.nextFloat();
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;
		var13 = (worldObj.rand.nextFloat() - 0.5D) * 0.5D; // velX
		var15 = (worldObj.rand.nextFloat() - 0.5D) * 0.5D; // velY
		var17 = (worldObj.rand.nextFloat() - 0.5D) * 0.5D; // velZ

		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		if (metadata == 0) {
			var13 = 0.0D;
			var17 = 0.0D;
			var15 = -(double) (worldObj.rand.nextFloat() * 0.6F);
		}

		if (metadata == 1) {
			var13 = 0.0D;
			var17 = 0.0D;
			var15 = worldObj.rand.nextFloat() * 0.6F;
		}

		if (metadata == 2) {
			var13 = 0.0D;
			var17 = -(double) (worldObj.rand.nextFloat() * 0.6F);
			var15 = 0.0D;
		}

		if (metadata == 4) {
			var13 = -(double) (worldObj.rand.nextFloat() * 0.6F);
			var17 = 0.0D;
			var15 = 0.0D;
		}

		if (metadata == 3) {
			var13 = 0.0D;
			var17 = worldObj.rand.nextFloat() * 0.6F;
			var15 = 0.0D;
		}

		if (metadata == 5) {
			var13 = worldObj.rand.nextFloat() * 0.6F;
			var17 = 0.0D;
			var15 = 0.0D;
		}
		for (int i = 0; i < 2; i++)
			world.spawnParticle("cloud", var7, var9, var11, var13, var15, var17);

	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		speed = nbttagcompound.getFloat("speed");
		direction = nbttagcompound.getDouble("direction");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("speed", speed);

		nbttagcompound.setDouble("direction", direction);
	}

}