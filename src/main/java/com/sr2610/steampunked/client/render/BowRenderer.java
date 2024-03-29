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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BowRenderer implements IItemRenderer {
	Minecraft mc = Minecraft.getMinecraft();
	private final RenderBlocks renderBlocksInstance = new RenderBlocks();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED
				|| type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		final EntityLivingBase living = (EntityLivingBase) data[1];
		for (int i = 0; i < item.getItem()
				.getRenderPasses(item.getItemDamage()) + 1; i++)
			renderItem(living, item, i, type);
	}

	public void renderItem(EntityLivingBase living, ItemStack stack,
			int renderPass, ItemRenderType type) {
		GL11.glPushMatrix();

		IIcon icon = null; // living.getItemIcon(stack, renderPass);
		if (living instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) living;
			if (player.getItemInUse() != null)
				icon = stack.getItem().getIcon(stack, renderPass, player,
						player.getItemInUse(), player.getItemInUseCount());
			else
				icon = living.getItemIcon(stack, renderPass);
		} else
			icon = living.getItemIcon(stack, renderPass);

		if (icon == null) {
			GL11.glPopMatrix();
			return;
		}
		final TextureManager texturemanager = mc.getTextureManager();
		texturemanager.bindTexture(texturemanager.getResourceLocation(stack
				.getItemSpriteNumber()));

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			GL11.glTranslatef(0.6F, 0.5F, 0.5F);
		else {
			GL11.glRotatef(180.0F, 0F, 0F, 1.0F);
			GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.75F);
			GL11.glTranslatef(-0.6F, -0.25F, 1.0F);
			GL11.glScalef(1.5F, 1.5F, 1.5F);
		}

		final Tessellator tessellator = Tessellator.instance;
		final float f = icon.getMinU();
		final float f1 = icon.getMaxU();
		final float f2 = icon.getMinV();
		final float f3 = icon.getMaxV();
		final float f4 = 0.0F;
		final float f5 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		final float f6 = 1.5F;
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3,
				icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();
	}

}
