package com.sr2610.steampunked.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.common.entitys.EntityAutomoton;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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

	public int pass = 0;

	public ModelAutomoton() {
		textureWidth = 128;
		textureHeight = 32;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3F, -7F, -4F, 6, 6, 6);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 32);
		head.mirror = true;
		neck = new ModelRenderer(this, 38, 19);
		neck.addBox(-2F, -1F, -3F, 4, 1, 4);
		neck.setRotationPoint(0F, 0F, 0F);
		neck.setTextureSize(128, 32);
		neck.mirror = true;
		body = new ModelRenderer(this, 37, 0);
		body.addBox(-4F, -6F, -3F, 8, 10, 4);
		body.setRotationPoint(0F, 6F, 0F);
		body.setTextureSize(128, 32);
		body.mirror = true;
		lShoulder = new ModelRenderer(this, 73, 0);
		lShoulder.addBox(0F, -1F, -1F, 1, 2, 2);
		lShoulder.setRotationPoint(4F, 2F, -1F);
		lShoulder.setTextureSize(128, 32);
		lShoulder.mirror = true;
		rShoulder = new ModelRenderer(this, 65, 0);
		rShoulder.addBox(-1F, -1F, -1F, 1, 2, 2);
		rShoulder.setRotationPoint(-4F, 2F, -1F);
		rShoulder.setTextureSize(128, 32);
		rShoulder.mirror = true;
		rArm = new ModelRenderer(this, 0, 16);
		rArm.addBox(-4F, -4F, -2F, 4, 12, 4);
		rArm.setRotationPoint(-5F, 2F, -1F);
		rArm.setTextureSize(128, 32);
		rArm.mirror = true;
		lArm = new ModelRenderer(this, 19, 16);
		lArm.addBox(0F, -4F, -2F, 4, 12, 4);
		lArm.setRotationPoint(5F, 2F, -1F);
		lArm.setTextureSize(128, 32);
		lArm.mirror = true;
		lThigh = new ModelRenderer(this, 99, 10);
		lThigh.addBox(-1F, 0F, -1F, 2, 4, 2);
		lThigh.setRotationPoint(2F, 10F, -1F);
		lThigh.setTextureSize(128, 32);
		lThigh.mirror = true;
		rLeg = new ModelRenderer(this, 82, 19);
		rLeg.addBox(-2F, 4F, -2F, 4, 8, 4);
		rLeg.setRotationPoint(-2F, 10F, -1F);
		rLeg.setTextureSize(128, 32);
		rLeg.mirror = true;
		rThigh = new ModelRenderer(this, 86, 10);
		rThigh.addBox(-1F, 0F, -1F, 2, 4, 2);
		rThigh.setRotationPoint(-2F, 10F, -1F);
		rThigh.setTextureSize(128, 32);
		rThigh.mirror = true;
		lLeg = new ModelRenderer(this, 64, 19);
		lLeg.addBox(-2F, 4F, -2F, 4, 8, 4);
		lLeg.setRotationPoint(2F, 10F, -1F);
		lLeg.setTextureSize(128, 32);
		lLeg.mirror = true;
		punchcard = new ModelRenderer(this, 86, 0);
		punchcard.addBox(-2F, 0F, 0F, 4, 1, 1);
		punchcard.setRotationPoint(0F, -5F, 2F);
		punchcard.setTextureSize(128, 32);
		punchcard.mirror = true;
	}

	private float getAngle(float par1, float par2) {
		return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F)
				/ (par2 * 0.25F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {

		par7 = par7 / 2;
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.8, 0);
		head.render(par7);
		neck.render(par7);
		body.render(par7);
		lShoulder.render(par7);
		rShoulder.render(par7);
		rArm.render(par7);
		lArm.render(par7);
		lThigh.render(par7);
		rLeg.render(par7);
		rThigh.render(par7);
		lLeg.render(par7);
		punchcard.render(par7);
		GL11.glPopMatrix();
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {

		lLeg.rotateAngleX = -1.5F * getAngle(par1, 13.0F) * par2;
		lThigh.rotateAngleX = -1.5F * getAngle(par1, 13.0F) * par2;
		rLeg.rotateAngleX = 1.5F * getAngle(par1, 13.0F) * par2;
		rThigh.rotateAngleX = 1.5F * getAngle(par1, 13.0F) * par2;
		lLeg.rotateAngleY = 0.0F;
		lThigh.rotateAngleY = 0.0F;
		rLeg.rotateAngleY = 0.0F;
		rThigh.rotateAngleY = 0.0F;
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the
	 * setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase,
			float par2, float par3, float par4) {
		EntityAutomoton automoton = (EntityAutomoton) par1EntityLivingBase;
		int i = automoton.getAttackTimer();
		int p = automoton.getProgramID();

		if (p > 0)
			punchcard.isHidden = false;
		else
			punchcard.isHidden = true;

		if (i > 0) {
			rArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(i - par4, 10.0F);
			lArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(i - par4, 10.0F);
		} else {
			ItemStack itemstack = automoton.getCarriedForDisplay();

			if (itemstack != null) {
				rArm.rotateAngleX = -1.0F;
				lArm.rotateAngleX = -1.0F;
			} else {
				rArm.rotateAngleX = (-0.2F + 1.5F * func_78172_a(par2, 13.0F))
						* par3;
				lArm.rotateAngleX = (-0.2F - 1.5F * func_78172_a(par2, 13.0F))
						* par3;
			}
		}

	}

	private float func_78172_a(float par1, float par2) {
		return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F)
				/ (par2 * 0.25F);
	}
}
