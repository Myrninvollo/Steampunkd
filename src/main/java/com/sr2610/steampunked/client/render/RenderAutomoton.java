/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.client.model.ModelAutomoton;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAutomoton extends RenderLiving {

	private static final ResourceLocation textures = new ResourceLocation(
			"steampunked:textures/models/Automoton.png");

	public RenderAutomoton() {
		super(new ModelAutomoton(), 0.25F);

	}

	public void doRenderAutomoton(EntityAutomoton par1EntityAutomoton,
			double par2, double par4, double par6, float par8, float par9) {

		super.doRender(par1EntityAutomoton, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getAutomotonTextures(
			EntityAutomoton par1EntityAutomoton) {
		return textures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getAutomotonTextures((EntityAutomoton) entity);
	}

	protected void renderEquippedItems(EntityAutomoton entity, float par2) {
		{
			float f1 = 1.0F;
			GL11.glColor3f(f1, f1, f1);
			super.renderEquippedItems(entity, par2);
			ItemStack itemstack = entity.getCarriedForDisplay();

			if (itemstack != null) {
				GL11.glPushMatrix();
				float f2;
				f2 = 0.5F;

				GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);

				if (itemstack.getItem() instanceof ItemBlock) {
					GL11.glTranslatef(0.06F, 0.4F, -0.4F);
					f2 *= 0.75F;
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f2 / 2, -f2 / 2, f2 / 2);
				}

				else {
					f2 = 0.375F;
					GL11.glTranslatef(0.15F, 0.4F, -0.4F);
					GL11.glScalef(f2 / 2, f2 / 2, f2 / 2);
					GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-140.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(110.0F, 0.0F, 0.0F, 1.0F);
				}

				this.renderManager.itemRenderer
						.renderItem(entity, itemstack, 0);

				if (itemstack.getItem().requiresMultipleRenderPasses()) {
					this.renderManager.itemRenderer.renderItem(entity,
							itemstack, 1);
				}

				GL11.glPopMatrix();
			}
		}
	}

	protected void func_82410_b() {
		GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase,
			float par2) {
		this.renderEquippedItems((EntityAutomoton) par1EntityLivingBase, par2);
	}
}
