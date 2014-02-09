package com.sr2610.steampunked.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.google.common.io.ByteArrayDataInput;

public abstract class TileEntityMachine extends TileEntity implements IInventory
{
    protected abstract boolean isFluidFuel(FluidStack fuel);

	  /**
	   * Links an item slot to a tank for filling/draining containers.
	   */
	  public class ContainerSlot
	  {
	    public final boolean fill;
	    public final int tank_slot;
	    public final int slot;
	    
	    public ContainerSlot(int container_tank,int container_slot,boolean container_fill)
	    {
	      tank_slot = container_tank;
	      slot = container_slot;
	      fill = container_fill;
	    }
	    
	    public void Update()
	    {
	      ItemStack stack = getStackInSlot(slot);
	      if(stack == null || !(stack.getItem() instanceof IFluidContainerItem))
	      {
	        return;
	      }
	      IFluidContainerItem fluid_cont = (IFluidContainerItem)stack.getItem();
	      
	      FluidTank tank = GetTank(tank_slot);
	      if(fill)
	      {
	        FluidStack drained = tank.drain(25, false);
	        if(drained == null || drained.amount == 0)
	        {
	          return;
	        }
	        int filled = fluid_cont.fill(stack, drained, false);
	        if(filled == 0)
	        {
	          return;
	        }
	        drained = tank.drain(filled, true);
	        fluid_cont.fill(stack, drained, true);
	        UpdateTank(tank_slot);
	        UpdateInventoryItem(slot);
	      } else
	      {
	        FluidStack drained = fluid_cont.drain(stack, 25, false);
	        if(drained == null || drained.amount == 0)
	        {
	          return;
	        }
	        
	        int filled = tank.fill(drained, false);
	        if(filled == 0)
	        {
	          return;
	        }
	        drained = fluid_cont.drain(stack, filled, true);
	        tank.fill(drained, true);
	        UpdateTank(tank_slot);
	        UpdateInventoryItem(slot);
	      }
	    }
	  }
	  
	  private List<ContainerSlot> conatiner_slots;
	  private NBTTagCompound packet;
	  private boolean do_update;
	  private boolean initialized;
	  
	  protected boolean last_redstone_signal;
	  protected boolean redstone_signal;

	  
	  
	  protected final void AddContainerSlot(ContainerSlot cs)
	  {
	    conatiner_slots.add(cs);
	  }

	  protected abstract void UpdateEntityClient();

	  protected abstract void UpdateEntityServer();

	  public abstract FluidTank GetTank(int slot);
	  
	  public abstract int GetTankCount();

	  
	  public TileEntityMachine()
	  {
	    conatiner_slots = new ArrayList<ContainerSlot>();
	    
	    initialized = false;
	  }
	  
	  @Override
	  public void invalidate()
	  {
	    initialized = false;
	    super.invalidate();
	  }


	  
	  
	  
	  protected final void UpdateTank(int slot)
	  {
	    if(packet == null)
	    {
	      return;
	    }
	    WriteTankToNBT(packet,slot);
	    do_update = true;
	  }
	  
	  protected final void UpdateInventoryItem(int slot)
	  {
	    if(packet == null)
	    {
	      return;
	    }
	    WriteInventoryItemToNBT(packet,slot);
	    do_update = true;
	  }

	  protected final void WriteTankToNBT(NBTTagCompound compound,int slot)
	  {
	    NBTTagCompound tag = new NBTTagCompound();
	    GetTank(slot).writeToNBT(tag);
	    compound.setTag("Tank_" + String.valueOf(slot), tag);
	  }

	  protected final void WriteInventoryItemToNBT(NBTTagCompound compound,int slot)
	  {
	    ItemStack is = getStackInSlot(slot);
	    NBTTagCompound tag = new NBTTagCompound();
	    if(is != null)
	    {
	      tag.setBoolean("empty", false);
	      is.writeToNBT(tag);
	    } else
	    {
	      tag.setBoolean("empty", true);
	    }
	    compound.setTag("Item_" + String.valueOf(slot), tag);
	  }


	  @Override
	  public void readFromNBT(NBTTagCompound compound)
	  {
	    super.readFromNBT(compound);

	    int i;
	    for(i = 0; i < GetTankCount(); i++)
	    {
	      NBTTagCompound tag = (NBTTagCompound)compound.getTag("Tank_" + String.valueOf(i));
	      if(tag != null)
	      {
	        FluidTank tank = GetTank(i);
	        tank.setFluid(null);
	        tank.readFromNBT(tag);
	      }
	    }
	    
	    for(i = 0; i < getSizeInventory(); i++)
	    {
	      NBTTagCompound tag = (NBTTagCompound)compound.getTag("Item_" + String.valueOf(i));
	      if(tag != null)
	      {
	        ItemStack stack = null;
	        if(!tag.getBoolean("empty"))
	        {
	          stack = ItemStack.loadItemStackFromNBT(tag);
	        }
	        setInventorySlotContents(i, stack);
	      }
	    }
	  }
	  
	


	  @Override
	  public void writeToNBT(NBTTagCompound compound)
	  {
	    int i;
	    super.writeToNBT(compound);
	    for(i = 0; i < GetTankCount(); i++)
	    {
	      WriteTankToNBT(compound,i);
	    }
	    for(i = 0; i < getSizeInventory(); i++)
	    {
	      WriteInventoryItemToNBT(compound,i);
	    }
	  }

	  protected final void UpdateValue(String name,int value)
	  {
	    if(packet == null)
	    {
	      return;
	    }
	    packet.setInteger(name, value);

	    do_update = true;
	  }
	  
	  protected final void UpdateNBTTag(String name,NBTTagCompound compound)
	  {
	    if(packet == null)
	    {
	      return;
	    }
	    packet.setTag(name, compound);
	    do_update = true;
	  }

	  @Override
	  public  void updateEntity()
	  {
	    if(!(initialized || isInvalid()))
	    {
	      UpdateRedstone();
	    }
	    
	   

	    if(!worldObj.isRemote)
	    {
	    	this.update();
	      packet = new NBTTagCompound();
	      super.writeToNBT(packet);
	      do_update = false;
	      for(ContainerSlot cs:conatiner_slots)
	      {
	        cs.Update();
	      }
	      UpdateEntityServer();
	     
	      packet = null;
	    } else
	    {
	      UpdateEntityClient();
	    }
	    last_redstone_signal = redstone_signal;
	  }

	  public void update() {
		
	}


	  
	  public void UpdateRedstone()
	  {
	    redstone_signal = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	  }
	  
	  
	}