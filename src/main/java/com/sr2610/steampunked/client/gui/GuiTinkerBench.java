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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;
import com.sr2610.steampunked.common.inventory.container.ContainerTinkerBench;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.items.automotons.ItemChasis;
import com.sr2610.steampunked.common.items.interfaces.IUpgrade;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.network.PacketTinkerTable;
import com.sr2610.steampunked.common.tileentities.TileEntityTinkerBench;

public class GuiTinkerBench extends GuiMachine {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/tinkerbench.png");

	private static int rotate = 0;

	private final TileEntityTinkerBench injectorInventory;

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 193;
	private static final int TANK_Y = 35 - 16;

	private static final int TANK_OVERLAY_X = 216;
	private static final int TANK_OVERLAY_Y = 35 - 16;

	public EntityAutomoton ae;

	public GuiTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		super(new ContainerTinkerBench(cs, player_inv));
		ySize = 217;
		xSize = 216;
		injectorInventory = cs;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Automaton Tinkering Bench", 5, 6, 0x404040);
		fontRendererObj.drawString("Inventory", 25, ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUI_TEXTURE);
		final int window_x = (width - xSize) / 2;
		final int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

		DisplayTank(window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, injectorInventory.GetTank(0));
		ae = new EntityAutomoton(mc.theWorld);
		check();
		drawEntity(guiLeft + 51 + 55, guiTop + 75, 30,
				(float) (guiLeft + 51 + 60) - xSize, (float) (guiTop + 75 - 50)
						- ySize, ae);

	}

	private void check() {
		if (injectorInventory.getStackInSlot(0) != null
				&& injectorInventory.getStackInSlot(0).getItem() instanceof ItemChasis)
			ae.setInvisible(false);
		else
			ae.setInvisible(true);
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y))
			DisplayTankTooltip(mouse_x, mouse_y, injectorInventory.GetTank(0));

	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(1, width / 2 + 40, height / 2 - 35, 40,
				20, "Craft"));

	}

	@Override
	protected void mouseClickMove(int par1, int par2, int par3, long par4) {
		rotate = -par1;

		super.mouseClickMove(par1, par2, par3, par4);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 1)
			if (injectorInventory.getStackInSlot(0) != null
					&& injectorInventory.getStackInSlot(0).getItem() instanceof ItemChasis
					&& injectorInventory.getStackInSlot(1) != null) {
				final ItemStack stack = new ItemStack(ModItems.spawner, 1, 0);
				for (int i = 2; i < 6; ++i)
					if (injectorInventory.getStackInSlot(i) != null
							&& injectorInventory.getStackInSlot(i).getItem() instanceof IUpgrade) {
						if (!stack.hasTagCompound())
							stack.setTagCompound(new NBTTagCompound());

						final NBTTagCompound nbttagcompound = stack
								.getTagCompound();
						if (injectorInventory.getStackInSlot(i).getItemDamage() == 0) {
							final NBTTagDouble nbttagdouble = (NBTTagDouble) nbttagcompound
									.getTag("Range");

							if (nbttagdouble == null)
								stack.setTagInfo("Range",
										new NBTTagDouble(10.0));
						}

						if (injectorInventory.getStackInSlot(i).getItemDamage() == 2) {
							final NBTTagDouble nbttagdoubleHealth = (NBTTagDouble) nbttagcompound
									.getTag("MaxHealth");

							if (nbttagdoubleHealth == null)
								stack.setTagInfo("MaxHealth", new NBTTagDouble(
										40.0));
						}
						if (injectorInventory.getStackInSlot(i).getItemDamage() == 1) {
							final NBTTagDouble nbttagdoubleSpeed = (NBTTagDouble) nbttagcompound
									.getTag("Speed");

							if (nbttagdoubleSpeed == null)
								stack.setTagInfo("Speed", new NBTTagDouble(0.8));
						}
					}
				injectorInventory.setInventorySlotContents(8, stack);
				updateServer(stack);
				injectorInventory.markDirty();
			}
	}

	void updateServer(ItemStack stack) {
		Steampunked.packetPipeline.sendToServer(new PacketTinkerTable(
				injectorInventory.xCoord, injectorInventory.yCoord,
				injectorInventory.zCoord, stack));

	}

	public static void drawEntity(int par0, int par1, int par2, float par3,
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
