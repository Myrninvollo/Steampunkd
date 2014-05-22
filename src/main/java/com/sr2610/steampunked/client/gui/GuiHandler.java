/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.client.gui.handbook.GuiHandbook;
import com.sr2610.steampunked.common.inventory.container.ContainerInjector;
import com.sr2610.steampunked.common.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.common.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.common.inventory.container.ContainerSteamFurnace;
import com.sr2610.steampunked.common.inventory.container.ContainerTinkerBench;
import com.sr2610.steampunked.common.tileentities.TileEntityInjector;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.common.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		final TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new ContainerInjector(player.inventory,
					(TileEntityInjector) tile_entity);

		case 1:
			return new ContainerSteamFurnace(player.inventory,
					(TileEntitySteamFurnace) tile_entity);

		case 2:
			return new ContainerSteamBoiler(
					(TileEntitySteamBoiler) tile_entity, player.inventory);

		case 4:
			return new ContainerTinkerBench(
					(TileEntityTinkerBench) tile_entity, player.inventory);

		case 5:
			return new ContainerPunchcardmaker(player.inventory,
					(TileEntityPunchardMaker) tile_entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		final TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new GuiInjector(player.inventory, tile_entity);

		case 1:
			return new GuiSteamFurnace((TileEntitySteamFurnace) tile_entity,
					player.inventory);

		case 2:
			return new GuiSteamBoiler((TileEntitySteamBoiler) tile_entity,
					player.inventory);

		case 4:
			return new GuiTinkerBench((TileEntityTinkerBench) tile_entity,
					player.inventory);
		case 5:
			return new GuiPunchardMaker(player.inventory, tile_entity);

		case 6:
			return GuiHandbook.currentOpenHandbook;
		}
		return null;
	}
}
