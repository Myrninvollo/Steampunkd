package com.sr2610.steampunked.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMechBoots extends ModelBiped
{
  //fields
    ModelRenderer frontRight;
    ModelRenderer baseLeft;
    ModelRenderer strutLeftFront;
    ModelRenderer frontLeft;
    ModelRenderer baseRight;
    ModelRenderer strutLeftback;
    ModelRenderer strutRightFront;
    ModelRenderer strutRightBack;
  
  public ModelMechBoots()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      frontRight = new ModelRenderer(this, 0, 0);
      frontRight.addBox(-2F, 6.5F, -11F, 4, 1, 4);
      frontRight.setRotationPoint(0F, 0F, 0F);
      frontRight.setTextureSize(64, 32);
      frontRight.mirror = true;
      setRotation(frontRight, 0.5948578F, 0F, 0F);
      baseLeft = new ModelRenderer(this, 25, 16);
      baseLeft.addBox(-2F, 10F, -3F, 4, 2, 6);
      baseLeft.setRotationPoint(0F, 0F, 0F);
      baseLeft.setTextureSize(64, 32);
      baseLeft.mirror = true;
      setRotation(baseLeft, 0F, 0F, 0F);
      strutLeftFront = new ModelRenderer(this, 38, 26);
      strutLeftFront.addBox(2F, 9F, -1F, 1, 3, 1);
      strutLeftFront.setRotationPoint(0F, 0F, 0F);
      strutLeftFront.setTextureSize(64, 32);
      strutLeftFront.mirror = true;
      setRotation(strutLeftFront, 0F, 0F, 0F);
      frontLeft = new ModelRenderer(this, 0, 6);
      frontLeft.addBox(-2F, 6.5F, -11F, 4, 1, 4);
      frontLeft.setRotationPoint(0F, 0F, 0F);
      frontLeft.setTextureSize(64, 32);
      frontLeft.mirror = true;
      setRotation(frontLeft, 0.5948578F, 0F, 0F);
      baseRight = new ModelRenderer(this, 25, 0);
      baseRight.addBox(-2F, 10F, -3F, 4, 2, 6);
      baseRight.setRotationPoint(0F, 0F, 0F);
      baseRight.setTextureSize(64, 32);
      baseRight.mirror = true;
      setRotation(baseRight, 0F, 0F, 0F);
      strutLeftback = new ModelRenderer(this, 32, 26);
      strutLeftback.addBox(2F, 8F, 1F, 1, 4, 1);
      strutLeftback.setRotationPoint(0F, 0F, 0F);
      strutLeftback.setTextureSize(64, 32);
      strutLeftback.mirror = true;
      setRotation(strutLeftback, 0F, 0F, 0F);
      strutRightFront = new ModelRenderer(this, 38, 26);
      strutRightFront.addBox(-3F, 9F, -1F, 1, 3, 1);
      strutRightFront.setRotationPoint(0F, 0F, 0F);
      strutRightFront.setTextureSize(64, 32);
      strutRightFront.mirror = true;
      setRotation(strutRightFront, 0F, 0F, 0F);
      strutRightBack = new ModelRenderer(this, 32, 26);
      strutRightBack.addBox(-3F, 8F, 1F, 1, 4, 1);
      strutRightBack.setRotationPoint(0F, 0F, 0F);
      strutRightBack.setTextureSize(64, 32);
      strutRightBack.mirror = true;
      setRotation(strutRightBack, 0F, 0F, 0F);
      this.bipedLeftLeg.addChild(strutLeftFront);
      this.bipedLeftLeg.addChild(strutLeftback);
      this.bipedLeftLeg.addChild(baseLeft);
      this.bipedLeftLeg.addChild(frontLeft);
      
      this.bipedRightLeg.addChild(strutRightFront);
      this.bipedRightLeg.addChild(strutRightBack);
      this.bipedRightLeg.addChild(baseRight);
      this.bipedRightLeg.addChild(frontRight);

  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,entity);
  }

}
