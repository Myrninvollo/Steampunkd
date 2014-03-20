/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPipe extends ModelBase {
	ModelRenderer middle;
	ModelRenderer bottom;
	ModelRenderer top;
	ModelRenderer front;
	ModelRenderer back;
	ModelRenderer left;
	ModelRenderer right;

	public ModelPipe() {
		textureWidth = 64;
		textureHeight = 32;

		middle = new ModelRenderer(this, 0, 0);
		middle.addBox(0F, 0F, 0F, 12, 12, 12);
		middle.setRotationPoint(-6F, 10F, -6F);
		middle.setTextureSize(64, 32);
		middle.mirror = true;
		setRotation(middle, 0F, 0F, 0F);
		bottom = new ModelRenderer(this, 0, 0);
		bottom.addBox(0F, 0F, 0F, 12, 4, 12);
		bottom.setRotationPoint(-6F, 22F, -6F);
		bottom.setTextureSize(64, 32);
		bottom.mirror = true;
		setRotation(bottom, 0F, 0F, 0F);
		top = new ModelRenderer(this, 0, 0);
		top.addBox(0F, 0F, 0F, 12, 4, 12);
		top.setRotationPoint(-6F, 6F, -6F);
		top.setTextureSize(64, 32);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		front = new ModelRenderer(this, 0, 0);
		front.addBox(0F, 0F, 0F, 12, 12, 4);
		front.setRotationPoint(-6F, 10F, -10F);
		front.setTextureSize(64, 32);
		front.mirror = true;
		setRotation(front, 0F, 0F, 0F);
		back = new ModelRenderer(this, 0, 0);
		back.addBox(0F, 0F, 0F, 12, 12, 4);
		back.setRotationPoint(-6F, 10F, 6F);
		back.setTextureSize(64, 32);
		back.mirror = true;
		setRotation(back, 0F, 0F, 0F);
		left = new ModelRenderer(this, 0, 0);
		left.addBox(0F, 0F, 0F, 4, 12, 12);
		left.setRotationPoint(6F, 10F, -6F);
		left.setTextureSize(64, 32);
		left.mirror = true;
		setRotation(left, 0F, 0F, 0F);
		right = new ModelRenderer(this, 0, 0);
		right.addBox(0F, 0F, 0F, 4, 12, 12);
		right.setRotationPoint(-10F, 10F, -6F);
		right.setTextureSize(64, 32);
		right.mirror = true;
		setRotation(right, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		middle.render(f5);
		bottom.render(f5);
		top.render(f5);
		front.render(f5);
		back.render(f5);
		left.render(f5);
		right.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void renderPart(String string, Float scale) {

		if (string == "Top")
			top.render(scale);
		if (string == "Back")
			back.render(scale);
		if (string == "Front")
			front.render(scale);
		if (string == "Right")
			right.render(scale);
		if (string == "Left")
			left.render(scale);
		if (string == "Bottom")
			bottom.render(scale);
		if (string == "Middle")
			middle.render(scale);

	}

}
