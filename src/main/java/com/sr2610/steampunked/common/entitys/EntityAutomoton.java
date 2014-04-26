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
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.entitys.ai.EntityAICollectItem;
import com.sr2610.steampunked.common.entitys.ai.EntityAIMoveHome;
import com.sr2610.steampunked.common.utils.InventoryUtils;

public class EntityAutomoton extends EntityTameable implements IInventory {

	private ItemStack[] containerItems = new ItemStack[1];

	private boolean healthUpgrade = false;
	private boolean speedUpgrade = false;

	public int homeX;
	public int homeY;
	public int homeZ;
	public int side;
	private int healTimer;

	public EntityAutomoton(World world) {
		super(world);
		setSize(0.6F, 1F);
		func_110163_bv();
		getNavigator().setAvoidsWater(true);
		getNavigator().setCanSwim(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAILookIdle(this));

		tasks.addTask(3, new EntityAICollectItem(this));

		tasks.addTask(4, new EntityAIMoveHome(this));
		setCurrentItemOrArmor(0, getStackInSlot(0));

	

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
		if(getStackInSlot(0)!=null)
		System.out.println(		getStackInSlot(0).getItem().getUnlocalizedName());

		healTimer--;
		if (healTimer == 0) {
			healTimer = 60;
			heal(1.0F);
		}

		if (insertItemToInventory())
			markDirty();

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

	public void setDead() {

		for (int i = 0; i < this.getSizeInventory(); ++i) {
			ItemStack itemstack = this.getStackInSlot(i);

			if (itemstack != null) {
				float f = this.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

				while (itemstack.stackSize > 0) {
					int j = this.rand.nextInt(21) + 10;

					if (j > itemstack.stackSize) {
						j = itemstack.stackSize;
					}

					itemstack.stackSize -= j;
					EntityItem entityitem = new EntityItem(this.worldObj,
							this.posX + (double) f, this.posY + (double) f1,
							this.posZ + (double) f2, new ItemStack(
									itemstack.getItem(), j,
									itemstack.getItemDamage()));

					if (itemstack.hasTagCompound()) {
						entityitem.getEntityItem().setTagCompound(
								(NBTTagCompound) itemstack.getTagCompound()
										.copy());
					}

					float f3 = 0.05F;
					entityitem.motionX = (double) ((float) this.rand
							.nextGaussian() * f3);
					entityitem.motionY = (double) ((float) this.rand
							.nextGaussian() * f3 + 0.2F);
					entityitem.motionZ = (double) ((float) this.rand
							.nextGaussian() * f3);
					this.worldObj.spawnEntityInWorld(entityitem);
				}
			}
		}

		super.setDead();
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		par1NBTTagCompound.setInteger("X", homeX);
		par1NBTTagCompound.setInteger("Y", homeY);
		par1NBTTagCompound.setInteger("Z", homeZ);
		par1NBTTagCompound.setInteger("Side", side);

		for (int i = 0; i < this.containerItems.length; ++i) {
			if (this.containerItems[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.containerItems[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		homeX = par1NBTTagCompound.getInteger("X");
		homeY = par1NBTTagCompound.getInteger("Y");
		homeZ = par1NBTTagCompound.getInteger("Z");
		side = par1NBTTagCompound.getInteger("Side");

		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.containerItems = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.containerItems.length) {
				this.containerItems[j] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public ItemStack getStackInSlot(int par1) {
		return this.containerItems[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.containerItems[par1] != null) {
			ItemStack itemstack;

			if (this.containerItems[par1].stackSize <= par2) {
				itemstack = this.containerItems[par1];
				this.containerItems[par1] = null;
				return itemstack;
			} else {
				itemstack = this.containerItems[par1].splitStack(par2);

				if (this.containerItems[par1].stackSize == 0) {
					this.containerItems[par1] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.containerItems[par1] != null) {
			ItemStack itemstack = this.containerItems[par1];
			this.containerItems[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.containerItems[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	public void markDirty() {
	}

	private boolean insertItemToInventory() {
		IInventory iinventory = getOutputInventory();

		if (iinventory == null)
			return false;

		if (!(getDistance(homeX, homeY, homeZ) < 2.5D))
			return false;
		else {
			

			for (int i = 0; i < getSizeInventory(); ++i)
				if (getStackInSlot(i) != null) {
					

					ItemStack itemstack = getStackInSlot(i).copy();
					ItemStack itemstack1 = InventoryUtils.insertStack(
							iinventory, decrStackSize(i, 1), side);

					if (itemstack1 == null || itemstack1.stackSize == 0) {
						iinventory.markDirty();

						return true;
					}

					setInventorySlotContents(i, itemstack);
				}

			return false;
		}


	}

	private IInventory getOutputInventory() {
		return InventoryUtils.getInventoryAtLocation(worldObj, homeX, homeY,
				homeZ);
	}
	
	@Override
	protected void dropEquipment(boolean par1, int par2) {}

}
