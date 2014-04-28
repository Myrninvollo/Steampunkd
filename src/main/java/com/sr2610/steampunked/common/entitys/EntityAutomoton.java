/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.entitys;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.entitys.ai.EntityAIMoveHome;
import com.sr2610.steampunked.common.entitys.ai.programs.EntityAICollectItem;
import com.sr2610.steampunked.common.entitys.ai.programs.EntityAIInsertItem;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.utils.InventoryUtils;

public class EntityAutomoton extends EntityTameable implements IInventory {

	private ItemStack[] containerItems = new ItemStack[1];

	private boolean healthUpgrade = false;
	private boolean speedUpgrade = false;
	public ItemStack carriedItem;

	public int homeX;
	public int homeY;
	public int homeZ;
	public int side;
	private int healTimer;
	private int programID;

	public EntityAutomoton(World world) {
		super(world);
		setSize(0.6F, 1F);
		func_110163_bv();
		getNavigator().setAvoidsWater(true);
		getNavigator().setCanSwim(true);
	}

	public boolean canConsumeStackPartially(ItemStack stack) {
		return InventoryUtils.testInventoryInsertion(this, stack) > 0;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		return null;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public void updateCarried() {
		carriedItem = getStackInSlot(0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if (healthUpgrade)
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
					30.0D);
		else
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
					15.0D);

		if (speedUpgrade)
			getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(0.3D);
		else
			getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(0.5D);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		healTimer--;
		if (healTimer == 0) {
			healTimer = 60;
			heal(1.0F);
		}

	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

	@Override
	public void setDead() {

		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.1F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);

			if (itemstack != null)
				while (itemstack.stackSize > 0) {
					int j = rand.nextInt(21) + 10;

					if (j > itemstack.stackSize)
						j = itemstack.stackSize;

					itemstack.stackSize -= j;
					EntityItem entityitem = new EntityItem(worldObj, posX + f,
							posY + f1, posZ + f2, new ItemStack(
									itemstack.getItem(), j,
									itemstack.getItemDamage()));

					if (itemstack.hasTagCompound())
						entityitem.getEntityItem().setTagCompound(
								(NBTTagCompound) itemstack.getTagCompound()
										.copy());

					float f3 = 0.05F;
					entityitem.motionX = (float) rand.nextGaussian() * f3;
					entityitem.motionY = (float) rand.nextGaussian() * f3
							+ 0.2F;
					entityitem.motionZ = (float) rand.nextGaussian() * f3;
					worldObj.spawnEntityInWorld(entityitem);

				}

		}
		if (!worldObj.isRemote) {
			ItemStack is = new ItemStack(ModItems.punchcard, 1, programID);
			ItemStack is2 = new ItemStack(ModItems.spawner);
			EntityItem itemEntity = new EntityItem(worldObj, posX + f, posY
					+ f1, posZ + f2, is);
			if (programID != 0)
				worldObj.spawnEntityInWorld(itemEntity);
			itemEntity = new EntityItem(worldObj, posX + f, posY + f1, posZ
					+ f2, is2);
			worldObj.spawnEntityInWorld(itemEntity);
		}

		super.setDead();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		par1NBTTagCompound.setInteger("X", homeX);
		par1NBTTagCompound.setInteger("Y", homeY);
		par1NBTTagCompound.setInteger("Z", homeZ);
		par1NBTTagCompound.setInteger("Side", side);
		par1NBTTagCompound.setInteger("ProgramID", programID);

		for (int i = 0; i < containerItems.length; ++i)
			if (containerItems[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				containerItems[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		homeX = par1NBTTagCompound.getInteger("X");
		homeY = par1NBTTagCompound.getInteger("Y");
		homeZ = par1NBTTagCompound.getInteger("Z");
		side = par1NBTTagCompound.getInteger("Side");
		programID = par1NBTTagCompound.getInteger("ProgramID");

		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		containerItems = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < containerItems.length)
				containerItems[j] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
		}
		startUp();

	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return containerItems[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (containerItems[par1] != null) {
			ItemStack itemstack;

			if (containerItems[par1].stackSize <= par2) {
				itemstack = containerItems[par1];
				containerItems[par1] = null;
				return itemstack;
			} else {
				itemstack = containerItems[par1].splitStack(par2);

				if (containerItems[par1].stackSize == 0)
					containerItems[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (containerItems[par1] != null) {
			ItemStack itemstack = containerItems[par1];
			containerItems[par1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		containerItems[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void markDirty() {
		updateCarried();
	}

	@Override
	protected void dropEquipment(boolean par1, int par2) {
	}

	public void setProgram(int id) {
		programID = id;
		startUp();
	}

	public int getProgram() {
		return programID;
	}

	public void startUp() {
		switch (programID) {
		case 0:
			break;
		case 1:
			tasks.addTask(1, new EntityAICollectItem(this));
			tasks.addTask(3, new EntityAIInsertItem(this));
			tasks.addTask(2, new EntityAIMoveHome(this));

		}
	}
}
