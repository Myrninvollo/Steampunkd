package com.sr2610.steampunked.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.client.model.ModelPipe;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityPipe;

public class PipeRendererTESR extends TileEntitySpecialRenderer {

	private final ModelPipe model;

	public PipeRendererTESR() {
		model = new ModelPipe();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.3F, (float) z + 0.5F);

		final ResourceLocation textures = new ResourceLocation(Reference.ModID
				+ ":textures/blocks/models/pipe.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		renderModelAt(te, x, y, z, scale);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderModelAt(TileEntity tileEntity, double d, double d1,
			double d2, float scale) {
		if (tileEntity instanceof TileEntityPipe) {
			((TileEntityPipe) tileEntity).checkPipeConnections();
			final boolean[] adjecentConnections = ((TileEntityPipe) tileEntity).pipeConnectionsBuffer;
			final float f = 0.05F;

			if (adjecentConnections[0] == true)
				model.renderPart("Top", f);

			if (adjecentConnections[1] == true)
				model.renderPart("Bottom", f);

			if (adjecentConnections[2] == true)
				model.renderPart("Front", f);

			if (adjecentConnections[3] == true)
				model.renderPart("Back", f);

			if (adjecentConnections[4] == true)
				model.renderPart("Right", f);

			if (adjecentConnections[5] == true)
				model.renderPart("Left", f);

			model.renderPart("Middle", f);

		}

	}

}
