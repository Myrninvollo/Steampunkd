package steampunked.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import steampunked.client.model.ModelAutomoton;
import steampunked.entity.automatons.EntityAutomoton;

@SideOnly(Side.CLIENT)
public class RenderAutomoton extends RenderLiving {

	private static final ResourceLocation textures = new ResourceLocation(
			"steampunked:textures/models/Automoton.png");

	private final ModelAutomoton model;

	public RenderAutomoton() {
		super(new ModelAutomoton(), 0.25F);
		this.model = (ModelAutomoton) super	.mainModel;
	}

	public void doRenderAutomoton(EntityAutomoton par1EntityAutomoton,
			double par2, double par4, double par6, float par8, float par9) {
		
		super.doRenderLiving(par1EntityAutomoton, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getAutomotonTextures(
			EntityAutomoton par1EntityAutomoton) {
		return textures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getAutomotonTextures((EntityAutomoton) entity);
	}
	
	 protected void renderItems(EntityAutomoton par1Auto, float par2)
	    {
	        float f1 = 1.0F;
	        GL11.glColor3f(f1, f1, f1);
	        super.renderEquippedItems(par1Auto, par2);
	        ItemStack itemstack = par1Auto.getHeldItem();

	        if (itemstack != null)
	        {
	            GL11.glPushMatrix();
	            float f2;
                f2 = 0.5F;


	            GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);

	            if (itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
	            {
	                GL11.glTranslatef(0.12F, 0.4F, -0.4F);
	                f2 *= 0.75F;
	                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
	                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
	                GL11.glScalef(f2/2, -f2/2, f2/2);
	            }
	   
	            else
	            {
	                f2 = 0.375F;
	                GL11.glTranslatef(0.125F, 0.4F, -0.4F);
	                GL11.glScalef(f2/2, f2/2, f2/2);
	                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
	                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
	                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
	            }
	            
	            this.model.rightarm.postRender(0.0625F);
	            this.model.leftarm.postRender(0.0625F);

	         
	            this.renderManager.itemRenderer.renderItem(par1Auto, itemstack, 0);

	            if (itemstack.getItem().requiresMultipleRenderPasses())
	            {
	                this.renderManager.itemRenderer.renderItem(par1Auto, itemstack, 1);
	            }

	            GL11.glPopMatrix();
	        }
	    }


	 
	    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	    {
	        this.renderItems((EntityAutomoton)par1EntityLivingBase, par2);
	    }
	    
	 
	 
}
