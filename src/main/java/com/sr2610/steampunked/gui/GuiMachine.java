package com.sr2610.steampunked.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer {

	protected abstract ResourceLocation GetGUITexture();

	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;

	public GuiMachine(Container container) {
		super(container);
	}

	/**
	 * Draw part of an icon
	 * 
	 * @param x
	 *            X coordinate to draw to.
	 * @param y
	 *            Y coordinate to draw to.
	 * @param icon
	 *            Icon to draw
	 * @param width
	 *            Width to draw.
	 * @param height
	 *            Height to draw.
	 * @param icon_x
	 *            X coordinate offset in the icon.
	 * @param icon_y
	 *            Y coordinate offset in the icon.
	 */
	private void drawTexturedModelRectFromIconPartial(int x, int y, IIcon icon,
			int width, int height, int icon_x, int icon_y) {
		Tessellator tessellator = Tessellator.instance;

		double min_u = icon.getInterpolatedU(icon_x);
		double min_v = icon.getInterpolatedV(icon_y);
		double max_u = icon.getInterpolatedU(icon_x + width);
		double max_v = icon.getInterpolatedV(icon_y + height);

		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, zLevel, min_u, max_v);
		tessellator
				.addVertexWithUV(x + width, y + height, zLevel, max_u, max_v);
		tessellator.addVertexWithUV(x + width, y, zLevel, max_u, min_v);
		tessellator.addVertexWithUV(x, y, zLevel, min_u, min_v);
		tessellator.draw();
	}

	protected void DisplayTankTooltip(int x, int y, FluidTank tank) {
		List<String> list = new ArrayList<String>();
		FluidStack stack = tank.getFluid();
		if (stack != null && stack.amount > 0) {

			list.add(stack.getFluid().getLocalizedName());
			list.add(String.valueOf(stack.amount) + " / "
					+ String.valueOf(tank.getCapacity()) + " mB");
		} else
			list.add("Empty");
		drawHoveringText(list, x, y, fontRendererObj);
	}

	/**
	 * Draw a tank in the GUI.
	 * 
	 * @param window_x
	 *            X coordinate of the GUI window.
	 * @param window_y
	 *            Y coordinate of the GUI window.
	 * @param x
	 *            X coordinate of the tank in the GUI window.
	 * @param y
	 *            Y coordinate of the tank in the GUI window.
	 * @param tank_height
	 *            Height of the tank in the GUI.
	 * @param overlay_x
	 *            X coordinate of overlay of the tank drawn in front of the
	 *            fluid.
	 * @param overlay_y
	 *            Y coordinate of overlay of the tank drawn in front of the
	 *            fluid.
	 * @param tank
	 *            Tank to draw.
	 */
	protected void DisplayTank(int window_x, int window_y, int x, int y,
			int tank_height, int overlay_x, int overlay_y, FluidTank tank) {
		FluidStack liquid = tank.getFluid();
		if (liquid == null)
			return;
		int start = 0;

		IIcon liquidIcon = null;
		Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getBlock().getIcon(1, 1) != null)
			liquidIcon = fluid.getBlock().getIcon(1, 1);
		mc.renderEngine.bindTexture(BLOCK_TEXTURE);

		int h = liquid.amount * tank_height / tank.getCapacity();

		if (liquidIcon != null)
			while (true) {
				int i;

				if (h > 16) {
					i = 16;
					h -= 16;
				} else {
					i = h;
					h = 0;
				}

				if (i > 0)
					drawTexturedModelRectFromIconPartial(window_x + x, window_y
							+ y + tank_height - i - start, liquidIcon, 16, i,
							0, 16 - i);
				start += 16;

				if (i == 0 || h == 0)
					break;
			}

		mc.renderEngine.bindTexture(GetGUITexture());
		drawTexturedModalRect(window_x + x, window_y + y, overlay_x, overlay_y,
				16, tank_height);
	}

}