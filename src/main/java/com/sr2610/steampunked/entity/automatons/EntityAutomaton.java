package com.sr2610.steampunked.entity.automatons;

import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.sr2610.steampunked.entity.ai.AttackFilter;
import com.sr2610.steampunked.entity.ai.EntityAICollectItem;
import com.sr2610.steampunked.entity.ai.EntityAIHurt;
import com.sr2610.steampunked.entity.ai.EntityAIMoveHome;
import com.sr2610.steampunked.entity.ai.EntityAiNearestTarget;

public class EntityAutomaton extends EntityTameable implements IInventory {

	private int attackTimer;
	public int homeX;
	public int homeY;
	public int homeZ;
	public int side;
	private int transferCooldown = -1;
	private ItemStack[] autoItemStacks = new ItemStack[1];
	public ItemStack stack = getHeldItem();
	private boolean attackMobs;
	private boolean pickup;
	private boolean hasProgram;
	private int healTimer;

	public boolean rFlame;
	public boolean lFlame;
	public double range;
	public double maxHealth;
	public double speed;
	private static final UUID speedBoostUUID = UUID
			.fromString("49455A49-7EC5-45BA-B886-3B80B23A1718");

	private static final IEntitySelector attackEntitySelector = new AttackFilter();
	private static final AttributeModifier speedBoost = new AttributeModifier(
			speedBoostUUID, "Speed Boost", 0.5D, 1);

	public EntityAutomaton(World par1World) {
		super(par1World);
		setSize(0.6F, 1F);
		attackMobs = false;
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, speed, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(4, new EntityAIHurt(this, false));

		targetTasks.addTask(5, new EntityAiNearestTarget(this,
				EntityLiving.class, 0, false, true, attackEntitySelector));

		tasks.addTask(6, new EntityAICollectItem(this));

		tasks.addTask(7, new EntityAIMoveHome(this));
		setCurrentItemOrArmor(0, getStackInSlot(0));

	}

	@Override
	protected void dropEquipment(boolean par1, int par2) {
		if (getHeldItem() != null)

			entityDropItem(getHeldItem(), 1);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
				0.25);
		getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(20.0);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		attackTimer = 10;
		worldObj.setEntityState(this, (byte) 4);
		boolean flag = par1Entity.attackEntityFrom(
				DamageSource.causeMobDamage(this), (float) 2.5);
		return flag;

	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (maxHealth > 20)
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
					maxHealth);

		if (speed > 0.0) {
			IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(speedBoost);

			if (speed > 0.25)
				iattributeinstance.applyModifier(speedBoost);
			else
				iattributeinstance.removeModifier(speedBoost);
		}

		if (attackTimer > 0)
			--attackTimer;
		if (healTimer == 0) {
			healTimer = 60;
			heal(1.0F);
		}
		if (worldObj != null && !worldObj.isRemote) {
			--transferCooldown;
			healTimer--;
			if (!isCoolingDown()) {
				setTransferCooldown(0);
				updateAuto();
			}
		}
	}

	public boolean updateAuto() {
		if (worldObj != null && !worldObj.isRemote) {
			if (!isCoolingDown()) {
				boolean flag = insertItemToInventory();

				if (flag) {
					setTransferCooldown(2);
					markDirty();
					return true;
				}
			}

			return false;
		} else
			return false;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity) {
		if (par1Entity instanceof IMob && getRNG().nextInt(20) == 0)
			setAttackTarget((EntityLivingBase) par1Entity);

		super.collideWithEntity(par1Entity);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("X", homeX);
		par1NBTTagCompound.setInteger("Y", homeY);
		par1NBTTagCompound.setInteger("Z", homeZ);
		par1NBTTagCompound.setBoolean("Pickup", getPickup());
		par1NBTTagCompound.setBoolean("Attack", getAttackMobs());
		par1NBTTagCompound.setBoolean("has", hasProgram);
		par1NBTTagCompound.setInteger("Side", side);
		par1NBTTagCompound.setDouble("Range", range);
		par1NBTTagCompound.setDouble("MaxHealth", maxHealth);
		par1NBTTagCompound.setBoolean("rFlame", rFlame);
		par1NBTTagCompound.setBoolean("lFlame", lFlame);
		par1NBTTagCompound.setInteger("TransferCooldown", transferCooldown);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < autoItemStacks.length; ++i)
			if (autoItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				autoItemStacks[i].writeToNBT(nbttagcompound1);
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
		pickup = par1NBTTagCompound.getBoolean("Pickup");
		attackMobs = par1NBTTagCompound.getBoolean("Attack");
		hasProgram = par1NBTTagCompound.getBoolean("has");
		side = par1NBTTagCompound.getInteger("Side");
		range = par1NBTTagCompound.getDouble("Range");
		maxHealth = par1NBTTagCompound.getDouble("MaxHealth");
		rFlame = par1NBTTagCompound.getBoolean("RightFlame");
		lFlame = par1NBTTagCompound.getBoolean("LeftFlame");

		transferCooldown = par1NBTTagCompound.getInteger("TransferCooldown");
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		autoItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < autoItemStacks.length)
				autoItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
		}

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
					ItemStack itemstack1 = insertStack(iinventory,
							decrStackSize(i, 1), side);

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
		return getInventoryAtLocation(worldObj, homeX, homeY, homeZ);
	}

	public static ItemStack insertStack(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2) {
		if (par0IInventory instanceof ISidedInventory && par2 > -1) {
			ISidedInventory isidedinventory = (ISidedInventory) par0IInventory;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(par2);

			for (int j = 0; j < aint.length && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++j)
				par1ItemStack = transferItems(par0IInventory, par1ItemStack,
						aint[j], par2);
		} else {
			int k = par0IInventory.getSizeInventory();

			for (int l = 0; l < k && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++l)
				par1ItemStack = transferItems(par0IInventory, par1ItemStack, l,
						par2);
		}

		if (par1ItemStack != null && par1ItemStack.stackSize == 0)
			par1ItemStack = null;

		return par1ItemStack;
	}

	private static ItemStack transferItems(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2, int par3) {
		ItemStack itemstack1 = par0IInventory.getStackInSlot(par2);

		if (canInsertItemToInventory(par0IInventory, par1ItemStack, par2, par3))
			if (itemstack1 == null) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max >= par1ItemStack.stackSize) {
					par0IInventory
							.setInventorySlotContents(par2, par1ItemStack);
					par1ItemStack = null;
				} else
					par0IInventory.setInventorySlotContents(par2,
							par1ItemStack.splitStack(max));
			} else if (areItemStacksEqualItem(itemstack1, par1ItemStack)) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max > itemstack1.stackSize) {
					int l = Math.min(par1ItemStack.stackSize, max
							- itemstack1.stackSize);
					par1ItemStack.stackSize -= l;
					itemstack1.stackSize += l;
				}
			}

		return par1ItemStack;
	}

	private static boolean canInsertItemToInventory(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2, int par3) {
		return !par0IInventory.isItemValidForSlot(par2, par1ItemStack) ? false
				: !(par0IInventory instanceof ISidedInventory)
						|| ((ISidedInventory) par0IInventory).canInsertItem(
								par2, par1ItemStack, par3);
	}

	private static boolean areItemStacksEqualItem(ItemStack par0ItemStack,
			ItemStack par1ItemStack) {
		return par0ItemStack.getItem() != par1ItemStack.getItem() ? false
				: par0ItemStack.getItemDamage() != par1ItemStack
						.getItemDamage() ? false
						: par0ItemStack.stackSize > par0ItemStack
								.getMaxStackSize() ? false : ItemStack
								.areItemStackTagsEqual(par0ItemStack,
										par1ItemStack);
	}

	@Override
	public int getSizeInventory() {
		return autoItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return autoItemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (autoItemStacks[par1] != null) {
			ItemStack itemstack;

			if (autoItemStacks[par1].stackSize <= par2) {
				itemstack = autoItemStacks[par1];
				autoItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = autoItemStacks[par1].splitStack(par2);

				if (autoItemStacks[par1].stackSize == 0)
					autoItemStacks[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (autoItemStacks[par1] != null) {
			ItemStack itemstack = autoItemStacks[par1];
			autoItemStacks[par1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		autoItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		setCurrentItemOrArmor(0, getStackInSlot(0));

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return true;
	}

	public void openChest() {
	}

	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		return true;
	}

	public static IInventory getInventoryAtLocation(World par0World,
			double par1, double par3, double par5) {
		IInventory iinventory = null;
		int i = MathHelper.floor_double(par1);
		int j = MathHelper.floor_double(par3);
		int k = MathHelper.floor_double(par5);
		TileEntity tileentity = par0World.getTileEntity(i, j, k);

		if (tileentity != null && tileentity instanceof IInventory) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				Block l = par0World.getBlock(i, j, k);

				if (l instanceof BlockChest)
					iinventory = ((BlockChest) l).func_149951_m(par0World, i,
							j, k);
			}
		}

		if (iinventory == null) {
			List list = par0World.getEntitiesWithinAABBExcludingEntity(
					(Entity) null,
					AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5,
							par1 + 1.0D, par3 + 1.0D, par5 + 1.0D),
					IEntitySelector.selectInventories);

			if (list != null && list.size() > 0)
				iinventory = (IInventory) list.get(par0World.rand.nextInt(list
						.size()));
		}

		return iinventory;
	}

	public void setTransferCooldown(int par1) {
		transferCooldown = par1;
	}

	public boolean isCoolingDown() {
		return transferCooldown > 0;
	}

	public boolean getPickup() {
		return pickup;
	}

	public void setPickup(boolean pickup) {
		this.pickup = pickup;
	}

	public boolean getAttackMobs() {
		return attackMobs;
	}

	public void setAttackMobs(boolean attackMobs) {
		this.attackMobs = attackMobs;
	}

	public void setProgram(boolean has) {
		hasProgram = has;
	}

	public boolean getProgram() {
		return hasProgram;
	}

	@Override
	public String getInventoryName() {
		return "AutoInv";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {
	}

	public void setProgram(int programID) {
		switch (programID) {
		case 1:
			setPickup(true);
			break;
		case 2:
			setAttackMobs(true);
			break;
		}
		setProgram(true);

	}

	public void setMaxHealth(float f) {
		maxHealth = f;
	}

}