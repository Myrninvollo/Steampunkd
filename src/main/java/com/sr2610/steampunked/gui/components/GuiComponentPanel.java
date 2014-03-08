/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [8 Mar 2014, 20:17:46 (GMT)]
 */
package com.sr2610.steampunked.gui.components;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public class GuiComponentPanel extends GuiComponentBox {

	public static final ISlotBackgroundRenderer normalSlot = new ISlotBackgroundRenderer() {
		@Override
		public void render(Gui gui, Slot slot) {
			gui.drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, 0, 20, 18, 18);
		}
	};

	public static final ISlotBackgroundRenderer bigSlot = new ISlotBackgroundRenderer() {
		@Override
		public void render(Gui gui, Slot slot) {
			gui.drawTexturedModalRect(slot.xDisplayPosition - 5, slot.yDisplayPosition - 5, 29, 20, 26, 26);
		}
	};

	public static final ISlotBackgroundRenderer noRenderSlot = new ISlotBackgroundRenderer() {
		@Override
		public void render(Gui gui, Slot slot) {}
	};

	private final Map<Integer, ISlotBackgroundRenderer> slotRenderers = Maps.newHashMap();

//	private WeakReference<Container> container;

	public GuiComponentPanel(int x, int y, int width, int height) {
		super(x, y, width, height, 0, 5, 0xFFFFFF);
	//	this.container = new WeakReference<Container>(container);
	}

	public void setSlotRenderer(int slotId, ISlotBackgroundRenderer renderer) {
		slotRenderers.put(slotId, renderer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Minecraft minecraft, int x, int y, int mouseX, int mouseY) {
		super.render(minecraft, x, y, mouseX, mouseY);
		GL11.glColor4f(1, 1, 1, 1);
	/*	if (container != null && container.get() != null) {
			for (Slot slot : (List<Slot>)container.get().inventorySlots) {
				Objects.firstNonNull(slotRenderers.get(slot.slotNumber), normalSlot).render(this, slot);
			}
		}*/
	}

}