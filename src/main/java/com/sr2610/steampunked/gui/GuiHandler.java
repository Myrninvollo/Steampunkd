/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.inventory.container.ContainerInjector;
import com.sr2610.steampunked.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.inventory.container.ContainerSteamFurnace;
import com.sr2610.steampunked.inventory.container.ContainerTinkerBench;
import com.sr2610.steampunked.tileentities.TileEntityInjector;
import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;
import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
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

		case 5:
			return new ContainerPunchcardmaker(
					(TileEntityPunchardMaker) tile_entity, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
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
		case 5:
			return new GuiPunchardMaker((TileEntityPunchardMaker) tile_entity,
					player.inventory);
		}
		return null;
	}
}
