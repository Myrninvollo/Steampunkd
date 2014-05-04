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

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.entitys.ai.EntityAIMoveHome;
import com.sr2610.steampunked.common.entitys.ai.programs.EntityAICollectItem;
import com.sr2610.steampunked.common.entitys.ai.programs.EntityAIInsertItem;
import com.sr2610.steampunked.common.entitys.ai.programs.combat.AttackFilter;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.utils.InventoryUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityAutomoton extends EntityGolem implements IInventory {

	private ItemStack[] containerItems = new ItemStack[1];
	private static final IEntitySelector attackEntitySelector = new AttackFilter();

	private boolean healthUpgrade = false;
	private boolean speedUpgrade = false;
	public ItemStack carriedItem;

	public int homeX;
	public int homeY;
	public int homeZ;
	public int side;
	public int healTimer;
	private int programID;

	private float range = 4.0F;
	public String owner;
	private int attackTimer;
	private boolean hasProgram;
	private int programClient;

	public EntityAutomoton(World world) {
		super(world);
		setSize(0.6F, 1F);
		func_110163_bv();

		this.dataWatcher.addObject(30,
				Byte.valueOf((byte) (int) getMaxHealth()));

	}

	public boolean canConsumeStackPartially(ItemStack stack) {
		return InventoryUtils.testInventoryInsertion(this, stack) > 0;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public void updateCarried() {
		carriedItem = getStackInSlot(0);
		if (carriedItem != null) {
			getDataWatcher().updateObject(16, this.carriedItem.copy());
			getDataWatcher().setObjectWatched(16);
		} else {
			getDataWatcher().addObjectByDataType(16, 5);
			getDataWatcher().setObjectWatched(16);
		}
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
					.setBaseValue(0.7D);
		else
			getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(0.5D);

		this.getAttributeMap().registerAttribute(
				SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(
				1.0D);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (programID != 0)
			this.worldObj.setEntityState(this, (byte) 17);

		if (this.attackTimer > 0) {
			--this.attackTimer;
		}
		if (this.getHealth() != this.getMaxHealth() && this.healTimer > 0) {
			--this.healTimer;
		}
		if (healTimer == 0) {
			this.heal(1.0F);
			this.healTimer = 60;
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
		par1NBTTagCompound.setString("Owner", owner);

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
		owner = par1NBTTagCompound.getString("Owner");

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

	@Override
	protected void entityInit() {
		super.entityInit();

		getDataWatcher().addObjectByDataType(16, 5);
		
	}

	public void startUp() {
		switch (programID) {
		case 0:
			break;
		case 1:
			tasks.addTask(1, new EntityAICollectItem(this));
			tasks.addTask(3, new EntityAIInsertItem(this));
			tasks.addTask(2, new EntityAIMoveHome(this));
			break;

		case 2:
			this.tasks.addTask(1, new EntityAIAttackOnCollide(this,
					getEntityAttribute(SharedMonsterAttributes.movementSpeed)
							.getBaseValue(), true));
			this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this,
					getEntityAttribute(SharedMonsterAttributes.movementSpeed)
							.getBaseValue(), 32.0F));
			this.tasks.addTask(4, new EntityAIMoveHome(this));
			this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(
					this, EntityLiving.class, 0, false, true,
					attackEntitySelector));

		}
		
		markDirty();
	}

	public void setOwner(String displayName) {
		owner = displayName;
	}

	protected void collideWithEntity(Entity par1Entity) {
		if (par1Entity instanceof IMob && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase) par1Entity);
		}

		super.collideWithEntity(par1Entity);
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		float i = (float) this.getEntityAttribute(
				SharedMonsterAttributes.attackDamage).getBaseValue();
		if (attackTimer == 0) {
			this.worldObj.setEntityState(this, (byte) 4);
			return par1Entity.attackEntityFrom(
					DamageSource.causeMobDamage(this), i);
		} else
			return false;
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}

	@SideOnly(Side.CLIENT)
	public int getProgramID() {
		return this.programClient;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1) {
		if (par1 == 4) {
			this.attackTimer = 10;

		}

		else if (par1 == 17) {
			this.programClient = 1;

		} else {
			super.handleHealthUpdate(par1);
		}
	}

	protected String getHurtSound() {
		return "mob.irongolem.hit";
	}

	protected String getDeathSound() {
		return "mob.irongolem.death";
	}

	protected void func_145780_a(int x, int y, int z, Block block) {
		this.playSound("mob.irongolem.walk", 0.5F, 0.5F);
	}

	public ItemStack getCarriedForDisplay() {
		if (this.dataWatcher.getWatchableObjectItemStack(16) != null) {
			return this.dataWatcher.getWatchableObjectItemStack(16);
		}
		return null;
	}

}
