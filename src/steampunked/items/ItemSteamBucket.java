package steampunked.items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.ItemFluidContainer;
import steampunked.blocks.ModBlocks;
import steampunked.core.tabs.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteamBucket extends ItemFluidContainer {

	public ItemSteamBucket(int par1) {
		super(par1);
		this.maxStackSize = 1;
		capacity = 1000;
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		MovingObjectPosition movingobjectposition = this
				.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
						true);

		if (movingobjectposition == null) {
			return par1ItemStack;
		} else {
			FillBucketEvent event = new FillBucketEvent(par3EntityPlayer,
					par1ItemStack, par2World, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return par1ItemStack;
			}

			if (event.getResult() == Event.Result.ALLOW) {
				if (par3EntityPlayer.capabilities.isCreativeMode) {
					return par1ItemStack;
				}

				if (--par1ItemStack.stackSize <= 0) {
					return event.result;
				}

				if (!par3EntityPlayer.inventory
						.addItemStackToInventory(event.result)) {
					par3EntityPlayer.dropPlayerItem(event.result);
				}

				return par1ItemStack;
			}

			if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				{

					if (movingobjectposition.sideHit == 0) {
						--j;
					}

					if (movingobjectposition.sideHit == 1) {
						++j;
					}

					if (movingobjectposition.sideHit == 2) {
						--k;
					}

					if (movingobjectposition.sideHit == 3) {
						++k;
					}

					if (movingobjectposition.sideHit == 4) {
						--i;
					}

					if (movingobjectposition.sideHit == 5) {
						++i;
					}

					if (!par3EntityPlayer.canPlayerEdit(i, j, k,
							movingobjectposition.sideHit, par1ItemStack)) {
						return par1ItemStack;
					}

					if (this.tryPlaceContainedLiquid(par2World, i, j, k)
							&& !par3EntityPlayer.capabilities.isCreativeMode) {
						return new ItemStack(Item.bucketEmpty);
					}
				}
			}

			return par1ItemStack;
		}
	}

	public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3,
			int par4) {

		{
			Material material = par1World.getBlockMaterial(par2, par3, par4);
			boolean flag = !material.isSolid();

			if (!par1World.isAirBlock(par2, par3, par4) && !flag) {
				return false;
			} else {
				if (!par1World.isRemote && flag && !material.isLiquid()) {
					par1World.destroyBlock(par2, par3, par4, true);
				}

				par1World.setBlock(par2, par3, par4,
						ModBlocks.BlockFluidSteam.blockID, 0, 3);
			}

			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon("steampunked:steam_bucket");
	}
}
