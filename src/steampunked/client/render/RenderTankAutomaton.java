package steampunked.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import steampunked.client.model.ModelTankAutomoton;
import steampunked.entity.automatons.EntityTankAutomoton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTankAutomaton extends RenderLiving {

	private static final ResourceLocation textures = new ResourceLocation(
			"steampunked:textures/models/AutomotonTank.png");

	private final ModelTankAutomoton model;

	public RenderTankAutomaton() {
		super(new ModelTankAutomoton(), 0.25F);
		this.model = (ModelTankAutomoton) super.mainModel;
	}

	public void doRenderAutomoton(EntityTankAutomoton par1EntityAutomoton,
			double par2, double par4, double par6, float par8, float par9) {

	}

	protected ResourceLocation getAutomotonTextures(
			EntityTankAutomoton par1EntityAutomoton) {
		return textures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getAutomotonTextures((EntityTankAutomoton) entity);
	}

}