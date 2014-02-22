package com.sr2610.steampunked.entity.automatons;

import java.util.List;

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
	public ItemStack stack = this.getHeldItem();
	private boolean attackMobs;
	private boolean pickup;
	private boolean hasProgram;
	private int healTimer;

	public boolean rFlame;
	public boolean lFlame;
	public double range;
	public double maxHealth;

	private static final IEntitySelector attackEntitySelector = new AttackFilter();

	public EntityAutomaton(World par1World) {
		super(par1World);
		setSize(0.6F, 1F);
		attackMobs = false;
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(4, new EntityAIHurt(this, false));

		targetTasks.addTask(5, new EntityAiNearestTarget(this,
				EntityLiving.class, 0, false, true, attackEntitySelector));

		this.tasks.addTask(6, new EntityAICollectItem(this));

		this.tasks.addTask(7, new EntityAIMoveHome(this));
		this.setCurrentItemOrArmor(0, this.getStackInSlot(0));

	}

	protected void dropEquipment(boolean par1, int par2) {
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setBaseValue(0.30000001192092896D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(20.0);
	}

	public boolean isAIEnabled() {
		return true;
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte) 4);
		boolean flag = par1Entity.attackEntityFrom(
				DamageSource.causeMobDamage(this), (float) (2.5));
		return flag;

	}

	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.maxHealth > 20) {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
					.setBaseValue(maxHealth);

		}


		if (this.attackTimer > 0) {
			--this.attackTimer;
		}
		if (this.healTimer == 0) {
			healTimer = 60;
			this.heal(1.0F);
		}
		if (this.worldObj != null && !this.worldObj.isRemote) {
			--this.transferCooldown;
			healTimer--;
			if (!this.isCoolingDown()) {
				this.setTransferCooldown(0);
				this.updateAuto();
			}
		}
	}

	public boolean updateAuto() {
		if (this.worldObj != null && !this.worldObj.isRemote) {
			if (!this.isCoolingDown()) {
				boolean flag = this.insertItemToInventory();

				if (flag) {
					this.setTransferCooldown(2);
					this.markDirty();
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	protected void collideWithEntity(Entity par1Entity) {
		if (par1Entity instanceof IMob && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase) par1Entity);
		}

		super.collideWithEntity(par1Entity);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	protected void dropFewItems(boolean par1, int par2) {
		if (this.getHeldItem() != null) {
			this.dropItem(this.getHeldItem().getItem(),
					this.getHeldItem().stackSize);
		}

	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("X", this.homeX);
		par1NBTTagCompound.setInteger("Y", this.homeY);
		par1NBTTagCompound.setInteger("Z", this.homeZ);
		par1NBTTagCompound.setBoolean("Pickup", this.getPickup());
		par1NBTTagCompound.setBoolean("Attack", this.getAttackMobs());
		par1NBTTagCompound.setBoolean("has", this.hasProgram);
		par1NBTTagCompound.setInteger("Side", this.side);
		par1NBTTagCompound.setDouble("Range", this.range);
		par1NBTTagCompound.setDouble("MaxHealth", this.maxHealth);
		par1NBTTagCompound.setBoolean("rFlame", this.rFlame);
		par1NBTTagCompound.setBoolean("lFlame", this.lFlame);
		par1NBTTagCompound
				.setInteger("TransferCooldown", this.transferCooldown);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.autoItemStacks.length; ++i) {
			if (this.autoItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.autoItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		homeX = (par1NBTTagCompound.getInteger("X"));
		homeY = (par1NBTTagCompound.getInteger("Y"));
		homeZ = (par1NBTTagCompound.getInteger("Z"));
		pickup = (par1NBTTagCompound.getBoolean("Pickup"));
		attackMobs = (par1NBTTagCompound.getBoolean("Attack"));
		hasProgram = (par1NBTTagCompound.getBoolean("has"));
		side = (par1NBTTagCompound.getInteger("Side"));
		range = (par1NBTTagCompound.getDouble("Range"));
		maxHealth = (par1NBTTagCompound.getDouble("MaxHealth"));
		rFlame = (par1NBTTagCompound.getBoolean("RightFlame"));
		lFlame = (par1NBTTagCompound.getBoolean("LeftFlame"));

		this.transferCooldown = par1NBTTagCompound
				.getInteger("TransferCooldown");
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.autoItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.autoItemStacks.length) {
				this.autoItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	private boolean insertItemToInventory() {
		IInventory iinventory = this.getOutputInventory();

		if (iinventory == null) {
			return false;
		}

		if (!(this.getDistance(homeX, homeY, homeZ) < 2.0D))
			return false;
		else {
			for (int i = 0; i < this.getSizeInventory(); ++i) {
				if (this.getStackInSlot(i) != null) {

					ItemStack itemstack = this.getStackInSlot(i).copy();
					ItemStack itemstack1 = insertStack(iinventory,
							this.decrStackSize(i, 1), side);

					if (itemstack1 == null || itemstack1.stackSize == 0) {
						iinventory.markDirty();
						return true;
					}

					this.setInventorySlotContents(i, itemstack);
				}
			}

			return false;
		}
		
	}

	private IInventory getOutputInventory() {
		return getInventoryAtLocation(this.worldObj, (double) (this.homeX),
				(double) (this.homeY), (double) (this.homeZ));
	}

	public static ItemStack insertStack(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2) {
		if (par0IInventory instanceof ISidedInventory && par2 > -1) {
			ISidedInventory isidedinventory = (ISidedInventory) par0IInventory;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(par2);

			for (int j = 0; j < aint.length && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++j) {
				par1ItemStack = transferItems(par0IInventory, par1ItemStack,
						aint[j], par2);
			}
		} else {
			int k = par0IInventory.getSizeInventory();

			for (int l = 0; l < k && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++l) {
				par1ItemStack = transferItems(par0IInventory, par1ItemStack, l,
						par2);
			}
		}

		if (par1ItemStack != null && par1ItemStack.stackSize == 0) {
			par1ItemStack = null;
		}

		return par1ItemStack;
	}

	private static ItemStack transferItems(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2, int par3) {
		ItemStack itemstack1 = par0IInventory.getStackInSlot(par2);

		if (canInsertItemToInventory(par0IInventory, par1ItemStack, par2, par3)) {
			boolean flag = false;
			if (itemstack1 == null) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max >= par1ItemStack.stackSize) {
					par0IInventory
							.setInventorySlotContents(par2, par1ItemStack);
					par1ItemStack = null;
				} else {
					par0IInventory.setInventorySlotContents(par2,
							par1ItemStack.splitStack(max));
				}
				flag = true;
			} else if (areItemStacksEqualItem(itemstack1, par1ItemStack)) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max > itemstack1.stackSize) {
					int l = Math.min(par1ItemStack.stackSize, max
							- itemstack1.stackSize);
					par1ItemStack.stackSize -= l;
					itemstack1.stackSize += l;
					flag = l > 0;
				}
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
				: (par0ItemStack.getItemDamage() != par1ItemStack
						.getItemDamage() ? false
						: (par0ItemStack.stackSize > par0ItemStack
								.getMaxStackSize() ? false : ItemStack
								.areItemStackTagsEqual(par0ItemStack,
										par1ItemStack)));
	}

	public int getSizeInventory() {
		return this.autoItemStacks.length;
	}

	public ItemStack getStackInSlot(int par1) {
		return this.autoItemStacks[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.autoItemStacks[par1] != null) {
			ItemStack itemstack;

			if (this.autoItemStacks[par1].stackSize <= par2) {
				itemstack = this.autoItemStacks[par1];
				this.autoItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.autoItemStacks[par1].splitStack(par2);

				if (this.autoItemStacks[par1].stackSize == 0) {
					this.autoItemStacks[par1] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.autoItemStacks[par1] != null) {
			ItemStack itemstack = this.autoItemStacks[par1];
			this.autoItemStacks[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.autoItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		this.setCurrentItemOrArmor(0, this.getStackInSlot(0));

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return true;
	}

	public void openChest() {
	}

	public void closeChest() {
	}

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

				if (l instanceof BlockChest) {
					iinventory = ((BlockChest) l).func_149951_m(par0World, i,
							j, k);
				}
			}
		}

		if (iinventory == null) {
			List list = par0World.getEntitiesWithinAABBExcludingEntity(
					(Entity) null,
					AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5,
							par1 + 1.0D, par3 + 1.0D, par5 + 1.0D),
					IEntitySelector.selectInventories);

			if (list != null && list.size() > 0) {
				iinventory = (IInventory) list.get(par0World.rand.nextInt(list
						.size()));
			}
		}

		return iinventory;
	}

	public void setTransferCooldown(int par1) {
		this.transferCooldown = par1;
	}

	public boolean isCoolingDown() {
		return this.transferCooldown > 0;
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
		this.hasProgram = has;
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
			this.setPickup(true);
			break;
		case 2:
			this.setAttackMobs(true);
			break;
		}
		setProgram(true);

	}

	public void setMaxHealth(float f) {
		maxHealth = f;
	}

	

}