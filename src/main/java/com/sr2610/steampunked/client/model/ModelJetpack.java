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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJetpack extends ModelBiped {
	ModelRenderer leftshoulder;
	ModelRenderer body;
	ModelRenderer rightshoulder;
	ModelRenderer strapright;
	ModelRenderer strapleft;
	ModelRenderer backplate;
	ModelRenderer enginerighttop;
	ModelRenderer bolt1;
	ModelRenderer rightarm;
	ModelRenderer rightarm2;
	ModelRenderer engineplate;
	ModelRenderer bolt2;
	ModelRenderer bolt3;
	ModelRenderer bolt4;
	ModelRenderer leftengine;
	ModelRenderer enginelefttop;
	ModelRenderer backbody;
	ModelRenderer strut;

	public ModelJetpack() {
		textureWidth = 128;
		textureHeight = 64;

		leftshoulder = new ModelRenderer(this, 16, 45);
		leftshoulder.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
		leftshoulder.setRotationPoint(5F, 2F, 0F);
		leftshoulder.setTextureSize(128, 64);
		leftshoulder.mirror = true;
		setRotation(leftshoulder, 0F, 0F, 0F);
		leftshoulder.mirror = false;
		body = new ModelRenderer(this, 16, 25);
		body.addBox(-4F, 1F, -4F, 8, 6, 2);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightshoulder = new ModelRenderer(this, 16, 45);
		rightshoulder.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
		rightshoulder.setRotationPoint(-5F, 2F, 0F);
		rightshoulder.setTextureSize(128, 64);
		rightshoulder.mirror = true;
		setRotation(rightshoulder, 0F, 0F, 0F);
		strapright = new ModelRenderer(this, 16, 36);
		strapright.addBox(-5F, 4F, -3F, 1, 3, 6);
		strapright.setRotationPoint(0F, 0F, 0F);
		strapright.setTextureSize(128, 64);
		strapright.mirror = true;
		setRotation(strapright, 0F, 0F, 0F);
		strapleft = new ModelRenderer(this, 16, 36);
		strapleft.addBox(4F, 4F, -3F, 1, 3, 6);
		strapleft.setRotationPoint(0F, 0F, 0F);
		strapleft.setTextureSize(128, 64);
		strapleft.mirror = true;
		setRotation(strapleft, 0F, 0F, 0F);
		backplate = new ModelRenderer(this, 36, 45);
		backplate.addBox(-4F, 1F, 2F, 8, 11, 2);
		backplate.setRotationPoint(0F, 0F, 0F);
		backplate.setTextureSize(128, 64);
		backplate.mirror = true;
		setRotation(backplate, 0F, 0F, 0F);
		enginerighttop = new ModelRenderer(this, 91, 9);
		enginerighttop.addBox(-8.5F, 2F, 5.5F, 3, 1, 3);
		enginerighttop.setRotationPoint(0F, 0F, 0F);
		enginerighttop.setTextureSize(128, 64);
		enginerighttop.mirror = true;
		setRotation(enginerighttop, 0F, 0F, 0.2617994F);
		bolt1 = new ModelRenderer(this, 12, 0);
		bolt1.addBox(1.5F, 5.5F, 11F, 1, 1, 1);
		bolt1.setRotationPoint(0F, 0F, 0F);
		bolt1.setTextureSize(128, 64);
		bolt1.mirror = true;
		setRotation(bolt1, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 75, 11);
		rightarm.addBox(-9F, 3F, 5F, 4, 5, 4);
		rightarm.setRotationPoint(0F, 0F, 0F);
		rightarm.setTextureSize(128, 64);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0.2617994F);
		rightarm2 = new ModelRenderer(this, 91, 13);
		rightarm2.addBox(-8.5F, 0F, 6F, 5, 2, 2);
		rightarm2.setRotationPoint(0F, 0F, 0F);
		rightarm2.setTextureSize(128, 64);
		rightarm2.mirror = true;
		setRotation(rightarm2, 0F, 0F, 0F);
		engineplate = new ModelRenderer(this, 16, 0);
		engineplate.addBox(-3F, 1F, 9.5F, 6, 6, 2);
		engineplate.setRotationPoint(0F, 0F, 0F);
		engineplate.setTextureSize(128, 64);
		engineplate.mirror = true;
		setRotation(engineplate, 0F, 0F, 0F);
		bolt2 = new ModelRenderer(this, 12, 0);
		bolt2.addBox(-2.5F, 5.5F, 11F, 1, 1, 1);
		bolt2.setRotationPoint(0F, 0F, 0F);
		bolt2.setTextureSize(128, 64);
		bolt2.mirror = true;
		setRotation(bolt2, 0F, 0F, 0F);
		bolt3 = new ModelRenderer(this, 12, 0);
		bolt3.addBox(1.5F, 1.5F, 11F, 1, 1, 1);
		bolt3.setRotationPoint(0F, 0F, 0F);
		bolt3.setTextureSize(128, 64);
		bolt3.mirror = true;
		setRotation(bolt3, 0F, 0F, 0F);
		bolt4 = new ModelRenderer(this, 12, 0);
		bolt4.addBox(-2.5F, 1.5F, 11F, 1, 1, 1);
		bolt4.setRotationPoint(0F, 0F, 0F);
		bolt4.setTextureSize(128, 64);
		bolt4.mirror = true;
		setRotation(bolt4, 0F, 0F, 0F);

		leftengine = new ModelRenderer(this, 75, 11);
		leftengine.addBox(5F, 3F, 5F, 4, 5, 4);
		leftengine.setRotationPoint(0F, 0F, 0F);
		leftengine.setTextureSize(128, 64);
		leftengine.mirror = true;
		setRotation(leftengine, 0F, 0F, -0.2617994F);
		leftengine.mirror = false;
		enginelefttop = new ModelRenderer(this, 91, 9);
		enginelefttop.addBox(5.5F, 2F, 5.5F, 3, 1, 3);
		enginelefttop.setRotationPoint(0F, 0F, 0F);
		enginelefttop.setTextureSize(128, 64);
		enginelefttop.mirror = true;
		setRotation(enginelefttop, 0F, 0F, -0.2617994F);
		backbody = new ModelRenderer(this, 32, 0);
		backbody.addBox(-3.5F, -1F, 4F, 7, 11, 7);
		backbody.setRotationPoint(0F, 0F, 0F);
		backbody.setTextureSize(128, 64);
		backbody.mirror = true;
		setRotation(backbody, 0F, 0F, 0F);
		strut = new ModelRenderer(this, 91, 13);
		strut.addBox(3.5F, 0F, 6F, 5, 2, 2);
		strut.setRotationPoint(0F, 0F, 0F);
		strut.setTextureSize(128, 64);
		strut.mirror = true;
		setRotation(strut, 0F, 0F, 0F);

	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float swingTime, float swingAmpl,
			float rightArmAngle, float headAngleX, float headAngleY, float scale) {

		leftshoulder.render(scale);
		body.render(scale);
		rightshoulder.render(scale);
		strapright.render(scale);
		strapleft.render(scale);
		backplate.render(scale);
		enginerighttop.render(scale);
		bolt1.render(scale);
		rightarm.render(scale);
		rightarm2.render(scale);
		engineplate.render(scale);
		bolt2.render(scale);
		bolt3.render(scale);
		bolt4.render(scale);
		leftengine.render(scale);
		enginelefttop.render(scale);
		backbody.render(scale);
		strut.render(scale);

	}

}
