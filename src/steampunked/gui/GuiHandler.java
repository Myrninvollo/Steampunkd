package steampunked.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steampunked.inventory.container.ContainerInjector;
import steampunked.inventory.container.ContainerSteamBoiler;
import steampunked.inventory.container.ContainerSteamFurnace;
import steampunked.inventory.container.ContainerTinkerBench;
import steampunked.tileentities.TileEntityInjector;
import steampunked.tileentities.TileEntitySteamBoiler;
import steampunked.tileentities.TileEntitySteamFurnace;
import steampunked.tileentities.TileEntityTinkerBench;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new ContainerInjector((TileEntityInjector) tile_entity,
					player.inventory);

		case 1:
			return new ContainerSteamFurnace(player.inventory,
					(TileEntitySteamFurnace) tile_entity);

		case 2:
			return new ContainerSteamBoiler(
					(TileEntitySteamBoiler) tile_entity, player.inventory);

		case 4:
			return new ContainerTinkerBench(
					(TileEntityTinkerBench) tile_entity, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new GuiInjector((TileEntityInjector) tile_entity,
					player.inventory);

		case 1:
			return new GuiSteamFurnace((TileEntitySteamFurnace) tile_entity,
					player.inventory);

		case 2:
			return new GuiSteamBoiler((TileEntitySteamBoiler) tile_entity,
					player.inventory);

		case 4:
			return new GuiTinkerBench((TileEntityTinkerBench) tile_entity,
					player.inventory);
		}
		return null;
	}
}