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

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

public class ModelAutomoton extends ModelBase {
	ModelRenderer head;
	ModelRenderer body;
	public ModelRenderer rightarm;
	public ModelRenderer leftarm;

	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer rightThigh;
	ModelRenderer leftThigh;
	ModelRenderer ShaftRight;
	ModelRenderer ShaftLeft;
	ModelRenderer Neck;

	ModelRenderer rightFlameBottom;
	ModelRenderer rightFlameCan;
	ModelRenderer rightFlameTop;
	ModelRenderer rightFlameShaft;
	ModelRenderer leftFlameTop;
	ModelRenderer leftFlameCan;
	ModelRenderer leftFlameShaft;
	ModelRenderer leftFlameBottom;
	ModelRenderer rightFlameEnd;
	ModelRenderer leftFlameEnd;

	public ModelAutomoton() {
		textureWidth = 256;
		textureHeight = 128;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 6, 6, 6);
		head.setRotationPoint(1F, 2F, 1F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		body = new ModelRenderer(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 10, 4);
		body.setRotationPoint(0F, 1F, 0F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		rightarm = new ModelRenderer(this, 40, 16);
		rightarm.addBox(-4F, -2F, -2F, 4, 12, 4);
		rightarm.setRotationPoint(-5F, 1F, 0F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		leftarm = new ModelRenderer(this, 40, 16);
		leftarm.addBox(1F, -2F, -2F, 4, 12, 4);
		leftarm.setRotationPoint(4F, 1F, 0F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		rightleg = new ModelRenderer(this, 0, 16);
		rightleg.addBox(-2F, 0F, -2F, 4, 11, 4);
		rightleg.setRotationPoint(-2F, 13F, 0F);
		rightleg.setTextureSize(64, 32);
		rightleg.mirror = true;
		leftleg = new ModelRenderer(this, 0, 16);
		leftleg.addBox(-2F, 0F, -2F, 4, 11, 4);
		leftleg.setRotationPoint(2F, 13F, 0F);
		leftleg.setTextureSize(64, 32);
		leftleg.mirror = true;

		rightThigh = new ModelRenderer(this, 56, 24);
		rightThigh.addBox(-2F, -3F, -1F, 2, 2, 2);
		rightThigh.setRotationPoint(-1F, 14F, 0F);
		rightThigh.setTextureSize(64, 32);
		rightThigh.mirror = true;
		leftThigh = new ModelRenderer(this, 56, 24);
		leftThigh.addBox(-2F, -3F, -1F, 2, 2, 2);
		leftThigh.setRotationPoint(3F, 14F, 0F);
		leftThigh.setTextureSize(64, 32);
		leftThigh.mirror = true;

		ShaftRight = new ModelRenderer(this, 25, 5);
		ShaftRight.addBox(-5F, 1F, -1F, 1, 2, 2);
		ShaftRight.setRotationPoint(0F, 0F, 0F);
		ShaftRight.setTextureSize(64, 32);
		ShaftRight.mirror = true;
		ShaftLeft = new ModelRenderer(this, 25, 5);
		ShaftLeft.addBox(4F, 1F, -1F, 1, 2, 2);
		ShaftLeft.setRotationPoint(0F, 0F, 0F);
		ShaftLeft.setTextureSize(64, 32);
		ShaftLeft.mirror = true;
		Neck = new ModelRenderer(this, 0, 13);
		Neck.addBox(0F, 0F, 0F, 4, 1, 2);
		Neck.setRotationPoint(-2F, 0F, -1F);
		Neck.setTextureSize(64, 32);
		Neck.mirror = true;

		rightFlameBottom = new ModelRenderer(this, 120, 0);
		rightFlameBottom.addBox(-5F, 6F, -1F, 4, 4, 4);
		rightFlameBottom.setRotationPoint(-4F, 2F, 0F);
		rightFlameBottom.setTextureSize(64, 32);
		rightFlameBottom.mirror = true;
		rightFlameCan = new ModelRenderer(this, 160, 0);
		rightFlameCan.addBox(-4F, -2F, -4F, 2, 4, 2);
		rightFlameCan.setRotationPoint(-4F, 2F, 0F);
		rightFlameCan.setTextureSize(64, 32);
		rightFlameCan.mirror = true;
		rightFlameTop = new ModelRenderer(this, 70, 0);
		rightFlameTop.addBox(-5F, -3F, -2F, 4, 6, 4);
		rightFlameTop.setRotationPoint(-4F, 2F, 0F);
		rightFlameTop.setTextureSize(64, 32);
		rightFlameTop.mirror = true;
		rightFlameShaft = new ModelRenderer(this, 100, 0);
		rightFlameShaft.addBox(-4F, 3F, -1F, 2, 3, 2);
		rightFlameShaft.setRotationPoint(-4F, 2F, 0F);
		rightFlameShaft.setTextureSize(64, 32);
		rightFlameShaft.mirror = true;
		leftFlameTop = new ModelRenderer(this, 70, 0);
		leftFlameTop.addBox(0F, -3F, -2F, 4, 6, 4);
		leftFlameTop.setRotationPoint(5F, 2F, 0F);
		leftFlameTop.setTextureSize(64, 32);
		leftFlameTop.mirror = true;
		leftFlameCan = new ModelRenderer(this, 160, 0);
		leftFlameCan.addBox(2F, -2F, -4F, 2, 4, 2);
		leftFlameCan.setRotationPoint(4F, 2F, 0F);
		leftFlameCan.setTextureSize(64, 32);
		leftFlameCan.mirror = true;
		leftFlameShaft = new ModelRenderer(this, 100, 0);
		leftFlameShaft.addBox(1F, 3F, -1F, 2, 3, 2);
		leftFlameShaft.setRotationPoint(5F, 2F, 0F);
		leftFlameShaft.setTextureSize(64, 32);
		leftFlameShaft.mirror = true;
		leftFlameBottom = new ModelRenderer(this, 120, 0);
		leftFlameBottom.addBox(0F, 6F, -1F, 4, 4, 4);
		leftFlameBottom.setRotationPoint(5F, 2F, 0F);
		leftFlameBottom.setTextureSize(64, 32);
		leftFlameBottom.mirror = true;

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		f5 = f5 / 2;
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.75, 0);
		head.render(f5);
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		rightThigh.render(f5);
		leftThigh.render(f5);
		ShaftRight.render(f5);
		ShaftLeft.render(f5);
		Neck.render(f5);

		rightFlameBottom.render(f5);
		rightFlameTop.render(f5);
		rightFlameShaft.render(f5);
		GL11.glPopMatrix();

	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		leftleg.rotateAngleX = -1.5F * getAngle(f, 13.0F) * f1;
		leftThigh.rotateAngleX = -1.5F * getAngle(f, 13.0F) * f1;
		rightleg.rotateAngleX = 1.5F * getAngle(f, 13.0F) * f1;
		rightThigh.rotateAngleX = 1.5F * getAngle(f, 13.0F) * f1;
		leftleg.rotateAngleY = 0.0F;
		leftThigh.rotateAngleY = 0.0F;
		rightleg.rotateAngleY = 0.0F;
		rightThigh.rotateAngleY = 0.0F;

	}

	private float getAngle(float par1, float par2) {
		return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F)
				/ (par2 * 0.25F);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase,
			float par2, float par3, float par4) {
		EntityAutomaton entity = (EntityAutomaton) par1EntityLivingBase;
		hideParts(entity);

		if (entity.getHeldItem() != null) {
			rightarm.rotateAngleX = -0.6F;
			rightarm.rotateAngleY = 0.0F;
			leftarm.rotateAngleX = -0.6F;
			leftarm.rotateAngleY = 0.0F;

		} else {

			rightarm.rotateAngleX = MathHelper.cos(par2 * 0.6662F
					+ (float) Math.PI)
					* 2.0F * par3 * 0.5F;
			leftarm.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 2.0F * par3
					* 0.5F;
			rightarm.rotateAngleZ = 0.0F;
			leftarm.rotateAngleZ = 0.0F;
		}

	}

	public void hideParts(EntityAutomaton entity) {
		if (entity.rFlame == true) {
			rightFlameBottom.isHidden = false;
			rightFlameTop.isHidden = false;
			rightFlameShaft.isHidden = false;

		} else {
			rightFlameBottom.isHidden = true;
			rightFlameTop.isHidden = true;
			rightFlameShaft.isHidden = true;
		}

	}
}
