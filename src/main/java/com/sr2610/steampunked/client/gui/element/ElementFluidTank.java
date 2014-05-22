package com.sr2610.steampunked.client.gui.element;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;

import com.sr2610.steampunked.api.utils.StringHelper;
import com.sr2610.steampunked.client.gui.GuiBase;
import com.sr2610.steampunked.client.gui.GuiProps;
import com.sr2610.steampunked.client.gui.RenderHelper;

public class ElementFluidTank extends ElementBase {

	public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(
			GuiProps.PATH_ELEMENTS + "FluidTank.png");
	public static final int DEFAULT_SCALE = 60;

	protected IFluidTank tank;
	protected int gaugeType;

	public ElementFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank) {

		super(gui, posX, posY);
		this.tank = tank;

		texture = DEFAULT_TEXTURE;
		texW = 64;
		texH = 64;

		sizeX = 16;
		sizeY = DEFAULT_SCALE;
	}

	public ElementFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank,
			String texture) {

		super(gui, posX, posY);
		this.tank = tank;

		this.texture = new ResourceLocation(texture);
		texW = 64;
		texH = 64;

		sizeX = 16;
		sizeY = DEFAULT_SCALE;
	}

	public ElementFluidTank setGauge(int gaugeType) {

		this.gaugeType = gaugeType;
		return this;
	}

	@Override
	public void draw() {

		if (!visible)
			return;
		int amount = getScaled();

		gui.drawFluid(posX, posY + sizeY - amount, tank.getFluid(), sizeX,
				amount);
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, 32 + gaugeType * 16, 1, sizeX, sizeY);
	}

	@Override
	public void addTooltip(List<String> list) {

		if (tank.getFluid() != null && tank.getFluidAmount() > 0)
			list.add(StringHelper.getFluidName(tank.getFluid()));
		list.add("" + tank.getFluidAmount() + " / " + tank.getCapacity()
				+ " mB");
	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		return false;
	}

	int getScaled() {

		return tank.getFluidAmount() * sizeY / tank.getCapacity();
	}

}
