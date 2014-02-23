package com.sr2610.steampunked.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ModelTankAutomoton extends ModelBase {
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

	ModelRenderer RightPanelSide;
	ModelRenderer BackPanel;
	ModelRenderer RightPanelF;
	ModelRenderer LeftPanelF;
	ModelRenderer LeftPanelSide;
	ModelRenderer PanelBot;
	ModelRenderer PanelTop;
	ModelRenderer Inside;
	ModelRenderer Glass;

	public ModelTankAutomoton() {
		textureWidth = 64;
		textureHeight = 32;

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
		ShaftLeft = new ModelRenderer(this, 35, 0);
		ShaftLeft.addBox(4F, 1F, -1F, 1, 2, 2);
		ShaftLeft.setRotationPoint(0F, 0F, 0F);
		ShaftLeft.setTextureSize(64, 32);
		ShaftLeft.mirror = true;
		Neck = new ModelRenderer(this, 0, 13);
		Neck.addBox(0F, 0F, 0F, 4, 1, 2);
		Neck.setRotationPoint(-2F, 0F, -1F);
		Neck.setTextureSize(64, 32);
		Neck.mirror = true;

		RightPanelSide = new ModelRenderer(this, 58, 0);
		RightPanelSide.addBox(0F, 0F, 0F, 1, 10, 2);
		RightPanelSide.setRotationPoint(-4F, 0F, 3F);
		RightPanelSide.setTextureSize(64, 32);
		RightPanelSide.mirror = true;
		BackPanel = new ModelRenderer(this, 40, 0);
		BackPanel.addBox(0F, 0F, 0F, 8, 11, 1);
		BackPanel.setRotationPoint(-4F, -1F, 2F);
		BackPanel.setTextureSize(64, 32);
		RightPanelF = new ModelRenderer(this, 32, 0);
		RightPanelF.addBox(0F, 0F, 0F, 3, 10, 1);
		RightPanelF.setRotationPoint(-4F, 0F, 5F);
		RightPanelF.setTextureSize(64, 32);
		RightPanelF.mirror = true;
		LeftPanelF = new ModelRenderer(this, 32, 0);
		LeftPanelF.addBox(0F, 0F, 0F, 3, 10, 1);
		LeftPanelF.setRotationPoint(1F, 0F, 5F);
		LeftPanelF.setTextureSize(64, 32);
		LeftPanelF.mirror = true;
		LeftPanelSide = new ModelRenderer(this, 58, 0);
		LeftPanelSide.addBox(0F, 0F, 0F, 1, 10, 2);
		LeftPanelSide.setRotationPoint(3F, 0F, 3F);
		LeftPanelSide.setTextureSize(64, 32);
		LeftPanelSide.mirror = true;
		PanelBot = new ModelRenderer(this, 42, 12);
		PanelBot.addBox(0F, 0F, 0F, 8, 1, 3);
		PanelBot.setRotationPoint(-4F, 9F, 3F);
		PanelBot.setTextureSize(64, 32);
		PanelBot.mirror = true;
		PanelTop = new ModelRenderer(this, 42, 12);
		PanelTop.addBox(0F, 0F, 0F, 8, 1, 3);
		PanelTop.setRotationPoint(-4F, -1F, 3F);
		PanelTop.setTextureSize(64, 32);
		PanelTop.mirror = true;
		Inside = new ModelRenderer(this, 0, 0);
		Inside.addBox(0F, 0F, 0F, 6, 9, 2);
		Inside.setRotationPoint(-3F, 0F, 3F);
		Inside.setTextureSize(64, 32);
		Inside.mirror = true;
		Glass = new ModelRenderer(this, 20, 0);
		Glass.addBox(0F, 0F, 0F, 2, 9, 1);
		Glass.setRotationPoint(-1F, 0F, 5F);
		Glass.setTextureSize(64, 32);
		Glass.mirror = true;

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

		RightPanelSide.render(f5);
		BackPanel.render(f5);
		RightPanelF.render(f5);
		LeftPanelF.render(f5);
		LeftPanelSide.render(f5);
		PanelBot.render(f5);
		PanelTop.render(f5);
		// Inside.render(f5);
		// Glass.render(f5);

		GL11.glPopMatrix();

	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI)
				* 2.0F * f1 * 0.5F;
		leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		rightarm.rotateAngleZ = 0.0F;
		leftarm.rotateAngleZ = 0.0F;
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

}
