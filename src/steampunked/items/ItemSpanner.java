package steampunked.items;

import java.util.HashSet;
import java.util.Set;

import steampunked.blocks.BlockInjector;
import steampunked.blocks.BlockSteamFurnace;
import steampunked.core.tabs.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.ForgeDirection;

public class ItemSpanner extends ItemTool {
	public static final Block[] blocksEffectiveAgainst = new Block[] {
			Block.daylightSensor, Block.dispenser, Block.dropper,
			Block.redstoneLampActive, Block.redstoneLampIdle, Block.pistonBase,
			Block.pistonExtension, Block.pistonStickyBase };
	private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();
	private boolean passSneakClick = true;

	public ItemSpanner(int i) {
		super(i, 1F, EnumToolMaterial.IRON, blocksEffectiveAgainst);
		setFull3D();
		this.setMaxDamage(-1);
		shiftRotations.add(BlockDropper.class);
		shiftRotations.add(BlockDispenser.class);
		shiftRotations.add(BlockHopper.class);
		shiftRotations.add(BlockFurnace.class);
		shiftRotations.add(BlockChest.class);
		shiftRotations.add(BlockInjector.class);
		shiftRotations.add(BlockSteamFurnace.class);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	private boolean isShiftRotation(Class<? extends Block> cls) {
		for (Class<? extends Block> shift : shiftRotations) {
			if (shift.isAssignableFrom(cls))
				return true;
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		int blockId = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockId];

		if (block == null)
			return false;

		if (player.isSneaking() != isShiftRotation(block.getClass()))
			return false;

		if (block.rotateBlock(world, x, y, z,
				ForgeDirection.getOrientation(side))) {
			player.swingItem();
			return !world.isRemote;
		}
		return false;
	}

	@Override
	public boolean shouldPassSneakingClickToBlock(World par2World, int par4,
			int par5, int par6) {
		return true;
	}

	public Item setPassSneakClick(boolean passClick) {
		this.passSneakClick = passClick;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("steampunked:spanner");
	}

}
