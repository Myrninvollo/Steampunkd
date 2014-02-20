package com.sr2610.steampunked.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.inventory.container.ContainerTinkerBench;
import com.sr2610.steampunked.items.ItemChasis;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.items.interfaces.IUpgrade;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketTinkerTable;
import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

public class GuiTinkerBench extends GuiMachine {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/tinkerbench.png");

	private static int rotate = 0;

	private TileEntityTinkerBench injectorInventory;

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 193;
	private static final int TANK_Y = 35 - 16;

	private static final int TANK_OVERLAY_X = 216;
	private static final int TANK_OVERLAY_Y = 35 - 16;

	private IInventory player_inventory;
	public EntityAutomaton ae;

	public GuiTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		super(new ContainerTinkerBench(cs, player_inv));
		player_inventory = player_inv;
		ySize = 217;
		xSize = 216;
		injectorInventory = cs;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Automaton Tinkering Bench", 5, 6, 0x404040);
		fontRendererObj.drawString("Inventory", 25, (ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUI_TEXTURE);
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

		DisplayTank(window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, injectorInventory.GetTank(0));
		ae = new EntityAutomaton(this.mc.theWorld);
		check();
		drawEntity(guiLeft + 51 + 55, guiTop + 75, 30,
				(float) (guiLeft + 51 + 60) - this.xSize,
				(float) (guiTop + 75 - 50) - this.ySize, ae);

	}

	private void check() {
		if (injectorInventory.getStackInSlot(0) != null
				&& injectorInventory.getStackInSlot(0).getItem() instanceof ItemChasis) {

			ae.setInvisible(false);
		} else {
			ae.setInvisible(true);
		}
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y)) {
			DisplayTankTooltip(mouse_x, mouse_y, injectorInventory.GetTank(0));
		}

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
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;

	}

	protected void mouseClickMove(int par1, int par2, int par3, long par4) {
		rotate = -par1;

		super.mouseClickMove(par1, par2, par3, par4);
	}

	public void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			if (injectorInventory.getStackInSlot(0) != null
					&& injectorInventory.getStackInSlot(0).getItem() instanceof ItemChasis) {
				ItemStack stack = new ItemStack(ModItems.spawner, 1, 0);
			/*	for (int i = 2; i < 6; ++i) {
					if (injectorInventory.getStackInSlot(i) != null
							&& injectorInventory.getStackInSlot(i).getItem() instanceof IUpgrade
							&& injectorInventory.getStackInSlot(i)
									.getItemDamage() == 0){
					NBTTagCompound nbt = stack.stackTagCompound;
				
					nbt.setDouble("Range", 10.0);
					
					stack.setTagCompound(nbt);
					}
					
				}*/
				injectorInventory.setInventorySlotContents(8, stack);
				updateServer(stack);
				injectorInventory.markDirty();
			}
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
		GL11.glTranslatef((float) par0, (float) par1, 50.0F);
		GL11.glScalef((float) (-par2), (float) par2, (float) par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = par5EntityLivingBase.renderYawOffset;
		float f3 = par5EntityLivingBase.rotationYaw;
		float f4 = par5EntityLivingBase.rotationPitch;
		float f5 = par5EntityLivingBase.prevRotationYawHead;
		float f6 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((double) (par4 / 40.0F))) * 20.0F,
				1.0F, 0.0F, 0.0F);
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

	public void updateScreen() {
		super.updateScreen();
		rotate++;

	}

}
