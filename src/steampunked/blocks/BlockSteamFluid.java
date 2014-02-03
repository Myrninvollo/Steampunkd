package steampunked.blocks;

import java.util.Random;

import steampunked.core.tabs.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.BlockFluidFinite;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFluid extends BlockFluidFinite {

	@SideOnly(Side.CLIENT)
	public Icon stillIcon;
	@SideOnly(Side.CLIENT)
	public Icon flowingIcon;

	public BlockSteamFluid(int Id) {
		super(Id, ModBlocks.steam, ModBlocks.materialSteam);
		this.quantaPerBlock = 6;
		this.setTickRate(5);
		setCreativeTab(null);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side <= 1)
			return stillIcon;
		else
			return flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		this.stillIcon = register.registerIcon("steampunked:steam_still");
		this.flowingIcon = register.registerIcon("steampunked:steam_still");
	}

}
