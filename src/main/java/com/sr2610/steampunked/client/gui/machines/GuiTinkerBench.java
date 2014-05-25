/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.machines;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.sr2610.steampunked.client.gui.GuiBaseAdv;
import com.sr2610.steampunked.client.gui.element.ElementFluidTank;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;
import com.sr2610.steampunked.common.inventory.container.ContainerTinkerBench;
import com.sr2610.steampunked.common.items.automotons.ItemChasis;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityTinkerBench;

public class GuiTinkerBench extends GuiBaseAdv {

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/tinkerbench.png");

	private static int rotate = 0;

	private final TileEntityTinkerBench bench;

	public EntityAutomoton ae;

	public GuiTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		super(new ContainerTinkerBench(cs, player_inv), TEXTURE);
		ySize = 217;
		xSize = 216;
		bench = cs;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Automaton Tinkering Bench", 5, 6, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		ae = new EntityAutomoton(mc.theWorld);
		check();
		drawEntity(guiLeft + 51 + 55, guiTop + 75, 30,
				(float) (guiLeft + 51 + 60) - xSize, (float) (guiTop + 75 - 50)
						- ySize, ae);

	}

	private void check() {
		if (bench.getStackInSlot(0) != null
				&& bench.getStackInSlot(0).getItem() instanceof ItemChasis)
			ae.setInvisible(false);
		else
			ae.setInvisible(true);
	}

	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementFluidTank(this, 193, 19, bench.GetTank(0)));

	}

	@Override
	protected void mouseClickMove(int par1, int par2, int par3, long par4) {
		rotate = -par1;

		super.mouseClickMove(par1, par2, par3, par4);
	}

	public void drawEntity(int par0, int par1, int par2, float par3,
			float par4, EntityLivingBase par5EntityLivingBase) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(par0, par1, 50.0F);
		GL11.glScalef(-par2, par2, par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		final float f2 = par5EntityLivingBase.renderYawOffset;
		final float f3 = par5EntityLivingBase.rotationYaw;
		final float f4 = par5EntityLivingBase.rotationPitch;
		final float f5 = par5EntityLivingBase.prevRotationYawHead;
		final float f6 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(par4 / 40.0F)) * 20.0F, 1.0F, 0.0F,
				0.0F);
		par5EntityLivingBase.renderYawOffset = par5EntityLivingBase.rotationYaw = par5EntityLivingBase.prevRotationYaw = par5EntityLivingBase.prevRotationYawHead = par5EntityLivingBase.rotationYawHead = rotate;
		GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset - 0.75F, 0.0F);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase,
				0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par5EntityLivingBase.renderYawOffset = f2;
		par5EntityLivingBase.rotationYaw = f3;
		par5EntityLivingBase.rotationPitch = f4;
		par5EntityLivingBase.prevRotationYawHead = f5;
		par5EntityLivingBase.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		rotate++;

	}

}
