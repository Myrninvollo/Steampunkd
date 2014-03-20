/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.sr2610.steampunked.gui.components.BaseComponent;
import com.sr2610.steampunked.gui.components.GuiComponentPanel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer {

	protected abstract ResourceLocation GetGUITexture();

	protected BaseComponent root;

	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;

	public GuiMachine(Container container) {
		super(container);
		root = createRoot();
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

	protected BaseComponent createRoot() {
		return new GuiComponentPanel(0, 0, xSize, ySize);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		root.mouseClicked(x - guiLeft, y - guiTop, button);
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		super.mouseMovedOrUp(x, y, button);
		root.mouseMovedOrUp(x - guiLeft, y - guiTop, button);

	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long time) {
		super.mouseClickMove(mouseX, mouseY, button, time);
		root.mouseClickMove(mouseX - guiLeft, mouseY - guiTop, button, time);
	}

	public void preRender(float mouseX, float mouseY) {
		root.mouseMovedOrUp((int) mouseX - guiLeft, (int) mouseY - guiTop, -1);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		root.keyTyped(par1, par2);
	}

	public void postRender(int mouseX, int mouseY) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX,
			int mouseY) {
		preRender(mouseX, mouseY);
		GL11.glPushMatrix();
		GL11.glTranslated(guiLeft, guiTop, 0);
		root.render(mc, 0, 0, mouseX - guiLeft, mouseY - guiTop);
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		postRender(mouseX, mouseY);

	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();

		root.renderOverlay(mc, guiLeft, guiTop, par1 - guiLeft, par2 - guiTop);
		GL11.glPopMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	protected String getInfo(int infoNo) {
		switch (infoNo) {
		case 0:
			return StatCollector
					.translateToLocal("steampunked.gui.ignoreRedstone");
		case 1:
			return StatCollector
					.translateToLocal("steampunked.gui.lowRedstone");
		case 2:
			return StatCollector
					.translateToLocal("steampunked.gui.highRedstone");
		default:
			return "Unexpected, Place and Replace";
		}
	}

}
