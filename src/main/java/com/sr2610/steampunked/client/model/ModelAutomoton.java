/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.common.entitys.EntityAutomoton;

public class ModelAutomoton extends ModelBase {
	ModelRenderer head;
	ModelRenderer neck;
	ModelRenderer body;
	ModelRenderer lShoulder;
	ModelRenderer rShoulder;
	ModelRenderer rArm;
	ModelRenderer lArm;
	ModelRenderer lThigh;
	ModelRenderer rLeg;
	ModelRenderer rThigh;
	ModelRenderer lLeg;
	ModelRenderer punchcard;

	public ModelAutomoton() {
		textureWidth = 128;
		textureHeight = 32;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3F, -7F, -4F, 6, 6, 6);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		neck = new ModelRenderer(this, 38, 19);
		neck.addBox(-2F, -1F, -3F, 4, 1, 4);
		neck.setRotationPoint(0F, 0F, 0F);
		neck.setTextureSize(128, 32);
		neck.mirror = true;
		setRotation(neck, 0F, 0F, 0F);
		body = new ModelRenderer(this, 37, 0);
		body.addBox(-4F, -6F, -3F, 8, 10, 4);
		body.setRotationPoint(0F, 6F, 0F);
		body.setTextureSize(128, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		lShoulder = new ModelRenderer(this, 73, 0);
		lShoulder.addBox(0F, -1F, -1F, 1, 2, 2);
		lShoulder.setRotationPoint(4F, 2F, -1F);
		lShoulder.setTextureSize(128, 32);
		lShoulder.mirror = true;
		setRotation(lShoulder, 0F, 0F, 0F);
		rShoulder = new ModelRenderer(this, 65, 0);
		rShoulder.addBox(-1F, -1F, -1F, 1, 2, 2);
		rShoulder.setRotationPoint(-4F, 2F, -1F);
		rShoulder.setTextureSize(128, 32);
		rShoulder.mirror = true;
		setRotation(rShoulder, 0F, 0F, 0F);
		rArm = new ModelRenderer(this, 0, 16);
		rArm.addBox(-4F, -4F, -2F, 4, 12, 4);
		rArm.setRotationPoint(-5F, 2F, -1F);
		rArm.setTextureSize(128, 32);
		rArm.mirror = true;
		setRotation(rArm, 0F, 0F, 0F);
		lArm = new ModelRenderer(this, 19, 16);
		lArm.addBox(0F, -4F, -2F, 4, 12, 4);
		lArm.setRotationPoint(5F, 2F, -1F);
		lArm.setTextureSize(128, 32);
		lArm.mirror = true;
		setRotation(lArm, 0F, 0F, 0F);
		lThigh = new ModelRenderer(this, 99, 10);
		lThigh.addBox(-1F, 0F, -1F, 2, 4, 2);
		lThigh.setRotationPoint(2F, 10F, -1F);
		lThigh.setTextureSize(128, 32);
		lThigh.mirror = true;
		setRotation(lThigh, 0F, 0F, 0F);
		rLeg = new ModelRenderer(this, 82, 19);
		rLeg.addBox(-2F, 4F, -2F, 4, 8, 4);
		rLeg.setRotationPoint(-2F, 10F, -1F);
		rLeg.setTextureSize(128, 32);
		rLeg.mirror = true;
		setRotation(rLeg, 0F, 0F, 0F);
		rThigh = new ModelRenderer(this, 86, 10);
		rThigh.addBox(-1F, 0F, -1F, 2, 4, 2);
		rThigh.setRotationPoint(-2F, 10F, -1F);
		rThigh.setTextureSize(128, 32);
		rThigh.mirror = true;
		setRotation(rThigh, 0F, 0F, 0F);
		lLeg = new ModelRenderer(this, 64, 19);
		lLeg.addBox(-2F, 4F, -2F, 4, 8, 4);
		lLeg.setRotationPoint(2F, 10F, -1F);
		lLeg.setTextureSize(128, 32);
		lLeg.mirror = true;
		setRotation(lLeg, 0F, 0F, 0F);
		punchcard = new ModelRenderer(this, 86, 0);
		punchcard.addBox(-2F, 0F, 0F, 4, 1, 1);
		punchcard.setRotationPoint(0F, -5F, 2F);
		punchcard.setTextureSize(128, 32);
		punchcard.mirror = true;
		setRotation(punchcard, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	   f5= f5/2;
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    GL11.glPushMatrix();
	GL11.glTranslated(0, 0.8, 0);
    head.render(f5);
    neck.render(f5);
    body.render(f5);
    lShoulder.render(f5);
    rShoulder.render(f5);
    rArm.render(f5);
    lArm.render(f5);
    lThigh.render(f5);
    rLeg.render(f5);
    rThigh.render(f5);
    lLeg.render(f5);
    punchcard.render(f5);
	GL11.glPopMatrix();
  }

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		lLeg.rotateAngleX = -1.5F * getAngle(f, 13.0F) * f1;
		lThigh.rotateAngleX = -1.5F * getAngle(f, 13.0F) * f1;
		rLeg.rotateAngleX = 1.5F * getAngle(f, 13.0F) * f1;
		rThigh.rotateAngleX = 1.5F * getAngle(f, 13.0F) * f1;
		lLeg.rotateAngleY = 0.0F;
		lThigh.rotateAngleY = 0.0F;
		rLeg.rotateAngleY = 0.0F;
		rThigh.rotateAngleY = 0.0F;

	}

	private float getAngle(float par1, float par2) {
		return (Math.abs((par1 % par2) - (par2 * 0.5F)) - (par2 * 0.25F))
				/ (par2 * 0.25F);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase,
			float par2, float par3, float par4) {
		final EntityAutomoton entity = (EntityAutomoton) par1EntityLivingBase;
		entity.updateCarried();
		
		if(entity.getProgram()!=0)
			punchcard.isHidden=false;
		else
			punchcard.isHidden=true;

		if (entity.carriedItem != null) {
			rArm.rotateAngleX = -0.6F;
			rArm.rotateAngleY = 0.0F;
			lArm.rotateAngleX = -0.6F;
			lArm.rotateAngleY = 0.0F;

		} else {

			rArm.rotateAngleX = MathHelper.cos((par2 * 0.6662F)
					+ (float) Math.PI)
					* 2.0F * par3 * 0.5F;
			lArm.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 2.0F * par3
					* 0.5F;
			rArm.rotateAngleZ = 0.0F;
			lArm.rotateAngleZ = 0.0F;
		}

	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
