/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [16 Mar 2014, 21:35:24 (GMT)]
 */
package com.sr2610.steampunked.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import com.sr2610.steampunked.client.model.ModelMechBoots;
import com.sr2610.steampunked.lib.Reference;

public class BootsItemRenderer implements IItemRenderer {
	
	
	protected ModelMechBoots model;

	public BootsItemRenderer() {
		model = new ModelMechBoots();
	}

	

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case ENTITY:
			return true;
		case EQUIPPED:
			return true;
		case EQUIPPED_FIRST_PERSON:
			return true;
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      Float f = 0.15F;
      
      Minecraft.getMinecraft().renderEngine
				.bindTexture(new ResourceLocation(Reference.ModID,
						"/textures/models/mechboots.png"));
      GL11.glPushMatrix();
      GL11.glRotatef(180F, 1, 0, 0);
      GL11.glRotatef(270F, 0, 1, 0);
      GL11.glTranslatef(0, -4F, 0);
     
      model.bipedHead.showModel = false;
		model.bipedHeadwear.showModel = false;
		model.bipedBody.showModel = false;
		model.bipedRightArm.showModel = false;
		model.bipedLeftArm.showModel = false;
		switch (type) {

		case ENTITY:
			   model.render(null, f, f, f, f, f, f);
			      GL11.glPopMatrix();
			break;
		case EQUIPPED:
			 GL11.glTranslatef(1, 0, 0);
			   model.render(null, f, f, f, f, f, f);
			      GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			   model.render(null, f, f, f, f, f, f);
			      GL11.glPopMatrix();
			break;
		case INVENTORY:
		      GL11.glTranslatef(0, 0.5F, 0);
			   model.render(null, f, f, f, f, f, f);
			      GL11.glPopMatrix();
			break;
		default:
			break;
		}


	}

}
