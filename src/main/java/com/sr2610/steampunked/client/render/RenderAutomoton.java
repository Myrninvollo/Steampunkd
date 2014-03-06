package com.sr2610.steampunked.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.client.model.ModelAutomoton;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAutomoton extends RenderLiving {

	private static final ResourceLocation textures = new ResourceLocation(
			"steampunked:textures/models/Automoton.png");

	public RenderAutomoton() {
		super(new ModelAutomoton(), 0.25F);
	}

	public void doRenderAutomoton(EntityAutomaton par1EntityAutomoton,
			double par2, double par4, double par6, float par8, float par9) {

		super.doRender(par1EntityAutomoton, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getAutomotonTextures(
			EntityAutomaton par1EntityAutomoton) {
		return textures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getAutomotonTextures((EntityAutomaton) entity);
	}

	protected void renderEquippedItems(EntityAutomaton par1EntityWitch,
			float par2) {
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		super.renderEquippedItems(par1EntityWitch, par2);
		ItemStack itemstack = par1EntityWitch.getHeldItem();

		if (itemstack != null) {
			GL11.glPushMatrix();
			float f1;

			GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);
			if (itemstack.getItem() instanceof ItemBlock
					&& RenderBlocks.renderItemIn3d(Block.getBlockFromItem(
							itemstack.getItem()).getRenderType())) {
				f1 = 0.5F;
				GL11.glTranslatef(0.12F, 0.4F, -0.4F);
				f1 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1 / 2, -f1 / 2, f1 / 2);
			} else if (itemstack.getItem() == Items.bow) {
				f1 = 0.625F;
				GL11.glTranslatef(0.12F, 0.4F, -0.4F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1 / 2, -f1 / 2, f1 / 2);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (itemstack.getItem().isFull3D()) {
				f1 = 0.625F;

				if (itemstack.getItem().shouldRotateAroundWhenRendering()) {
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.12F, 0.4F, -0.4F);
				}

				func_82410_b();
				GL11.glScalef(f1 / 2, -f1 / 2, f1 / 2);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				f1 = 0.375F;
				GL11.glTranslatef(0.12F, 0.4F, -0.4F);
				GL11.glScalef(f1 / 2, f1 / 2, f1 / 2);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
			renderManager.itemRenderer
					.renderItem(par1EntityWitch, itemstack, 0);

			if (itemstack.getItem().requiresMultipleRenderPasses())
				renderManager.itemRenderer.renderItem(par1EntityWitch,
						itemstack, 1);

			GL11.glPopMatrix();
		}
	}

	protected void func_82410_b() {
		GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase,
			float par2) {
		this.renderEquippedItems((EntityAutomaton) par1EntityLivingBase, par2);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase par1EntityLivingBase,
			float par2, float par3, float par4) {
		GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);

		String s = EnumChatFormatting
				.getTextWithoutFormattingCodes(par1EntityLivingBase
						.getCommandSenderName());
		if (s == "SR2610")
			GL11.glTranslatef(0.0F, par1EntityLivingBase.height + 0.1F, 0.0F);

		super.rotateCorpse(par1EntityLivingBase, par2, par3, par4);
	}
}
