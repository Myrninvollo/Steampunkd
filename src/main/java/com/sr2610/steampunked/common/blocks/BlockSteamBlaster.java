/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.tileentities.TileEntitySteamBlaster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamBlaster extends BlockContainer {

	protected BlockSteamBlaster(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntitySteamBlaster();
	}
	
	   protected Random rand = new Random();
	    @SideOnly(Side.CLIENT)
	    protected IIcon top;
	    @SideOnly(Side.CLIENT)
	    protected IIcon sideActive;
	    @SideOnly(Side.CLIENT)
	    protected IIcon topActive;

	 

	    /**
	     * How many world ticks before ticking
	     */
	    public int tickRate(World world)
	    {
	        return 4;
	    }

	    /**
	     * Called whenever the block is added into the world. Args: world, x, y, z
	     */
	    public void onBlockAdded(World world, int x, int y, int z)
	    {
	        super.onBlockAdded(world, x, y, z);
	        this.onAdded(world, x, y, z);
	    }

	    private void onAdded(World world, int x, int y, int z)
	    {
	        if (!world.isRemote)
	        {
	            Block block = world.getBlock(x, y, z - 1);
	            Block block1 = world.getBlock(x, y, z + 1);
	            Block block2 = world.getBlock(x - 1, y, z);
	            Block block3 = world.getBlock(x + 1, y, z);
	            byte b0 = 3;

	            if (block.func_149730_j() && !block1.func_149730_j())
	            {
	                b0 = 3;
	            }

	            if (block1.func_149730_j() && !block.func_149730_j())
	            {
	                b0 = 2;
	            }

	            if (block2.func_149730_j() && !block3.func_149730_j())
	            {
	                b0 = 5;
	            }

	            if (block3.func_149730_j() && !block2.func_149730_j())
	            {
	                b0 = 4;
	            }

	            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
	        }
	    }

	    /**
	     * Gets the block's texture. Args: side, meta
	     */
	    @SideOnly(Side.CLIENT)
	    public IIcon getIcon(int side, int meta)
	    {
	        int k = meta & 7;
	        return side == k ? (k != 1 && k != 0 ? this.sideActive : this.topActive) : (k != 1 && k != 0 ? (side != 1 && side != 0 ? this.blockIcon : this.top) : this.top);
	    }

	    @SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister ir)
	    {
	        this.blockIcon = ir.registerIcon("furnace_side");
	        this.top = ir.registerIcon("furnace_top");
	        this.sideActive = ir.registerIcon(this.getTextureName() + "_front_horizontal");
	        this.topActive = ir.registerIcon(this.getTextureName() + "_front_vertical");
	    }
	  

	    /**
	     * Called when the block is placed in the world.
	     */
	    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack)
	    {
	        int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
	        world.setBlockMetadataWithNotify(x, y, z, l, 2);

	        if (itemstack.hasDisplayName())
	        {
	            ((TileEntityDispenser)world.getTileEntity(x, y, z)).func_146018_a(itemstack.getDisplayName());
	        }
	    }

	   

	    public static IPosition getDirection(IBlockSource block)
	    {
	        EnumFacing enumfacing = getFront(block.getBlockMetadata());
	        double d0 = block.getX() + 0.7D * (double)enumfacing.getFrontOffsetX();
	        double d1 = block.getY() + 0.7D * (double)enumfacing.getFrontOffsetY();
	        double d2 = block.getZ() + 0.7D * (double)enumfacing.getFrontOffsetZ();
	        return new PositionImpl(d0, d1, d2);
	    }

	    public static EnumFacing getFront(int p_149937_0_)
	    {
	        return EnumFacing.getFront(p_149937_0_ & 7);
	    }

	    /**
	     * If this returns true, then comparators facing away from this block will use the value from
	     * getComparatorInputOverride instead of the actual redstone signal strength.
	     */
	    public boolean hasComparatorInputOverride()
	    {
	        return true;
	    }

	    /**
	     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	     * strength when this block inputs to a comparator.
	     */
	    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
	    {
	        return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
	    }
	    
	    public static void doDispense(World par0World, ItemStack par1ItemStack, int par2, EnumFacing par3EnumFacing, IPosition par4IPosition)
	    {
	        double d0 = par4IPosition.getX();
	        double d1 = par4IPosition.getY();
	        double d2 = par4IPosition.getZ();
	        EntityItem entityitem = new EntityItem(par0World, d0, d1 - 0.3D, d2, par1ItemStack);
	        double d3 = par0World.rand.nextDouble() * 0.1D + 0.2D;
	        entityitem.motionX = (double)par3EnumFacing.getFrontOffsetX() * d3;
	        entityitem.motionY = 0.20000000298023224D;
	        entityitem.motionZ = (double)par3EnumFacing.getFrontOffsetZ() * d3;
	        entityitem.motionX += par0World.rand.nextGaussian() * 0.007499999832361937D * (double)par2;
	        entityitem.motionY += par0World.rand.nextGaussian() * 0.007499999832361937D * (double)par2;
	        entityitem.motionZ += par0World.rand.nextGaussian() * 0.007499999832361937D * (double)par2;
	        par0World.spawnEntityInWorld(entityitem);
	    }
	    
	  /* public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	    {
			//par5Entity.motionY=0.5;

			if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
				par5Entity.motionY=-1;
			} else if (par1World.getBlockMetadata(par2, par3, par4) == 1) {
				par5Entity.motionY=+1;
			} else if (par1World.getBlockMetadata(par2, par3, par4) == 2) {
				par5Entity.motionZ=-1;
			} else if (par1World.getBlockMetadata(par2, par3, par4) == 3) {
				par5Entity.motionZ=+1;
			}else if (par1World.getBlockMetadata(par2, par3, par4) == 4) {
				par5Entity.motionX=-1;
			}else if (par1World.getBlockMetadata(par2, par3, par4) == 5) {
				par5Entity.motionX=+1;
			}



	    }
	    
	 //   public boolean isOpaqueCube()
	    {
	        return false;
	    }

		public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	    {
	        return null;
	    }*/
	

}
