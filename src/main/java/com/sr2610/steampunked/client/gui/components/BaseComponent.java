/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.components;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.common.lib.Reference;

public abstract class BaseComponent extends Gui {

	public final static ResourceLocation TEXTURE_SHEET = new ResourceLocation(
			Reference.ModID, "textures/gui/components.png");

	protected void bindComponentsSheet() {
		bindTextureToClient(TEXTURE_SHEET);
	}

	public enum TabColor {
		blue(0x8784c8), lightblue(0x84c7c8), green(0x84c892), yellow(0xc7c884), red(
				0xFF0000), purple(0xc884bf);

		private int color;

		TabColor(int col) {
			color = col;
		}

		public int getColor() {
			return color;
		}
	}

	protected String name = null;
	protected int x;
	protected int y;
	protected boolean renderChildren = true;
	protected boolean enabled = true;
	private boolean hasMouse = false;

	public BaseComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public String getName() {
		return name;
	}

	public BaseComponent setName(String name) {
		this.name = name;
		return this;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Is this component currently capturing (seeing) the mouse.
	 * 
	 * @return true if the last isMouseOver call was true
	 */
	public boolean capturingMouse() {
		return hasMouse;
	}

	/**
	 * If the mouse position is inside this component
	 * 
	 * @param mouseX
	 *            X position relative from this components parent
	 * @param mouseY
	 *            Y position relative from this components parent
	 * @return true if the X and Y are inside this components area
	 */
	protected boolean isMouseOver(int mouseX, int mouseY) {
		return hasMouse = mouseX >= x && mouseX < x + getWidth() && mouseY >= y
				&& mouseY < y + getHeight();
	}

	private final List<IComponentListener> listeners = new ArrayList<IComponentListener>();
	public List<BaseComponent> components = new ArrayList<BaseComponent>();

	public BaseComponent addComponent(BaseComponent component) {
		components.add(component);
		return this;
	}

	public BaseComponent childByName(String componentName) {
		if (componentName == null)
			return null;
		for (final BaseComponent component : components)
			if (componentName.equals(component.getName()))
				return component;
		return null;
	}

	public BaseComponent addListener(IComponentListener listener) {
		if (listeners.contains(listener))
			return this;
		listeners.add(listener);
		return this;
	}

	public void removeListener(IComponentListener listener) {
		if (!listeners.contains(listener))
			return;
		listeners.remove(listener);
	}

	public void clearListeners() {
		listeners.clear();
	}

	public void render(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY) {
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled())
					component.render(minecraft, offsetX + x, offsetY + y,
							mouseX - x, mouseY - y);
	}

	public void renderOverlay(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY) {
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled())
					component.renderOverlay(minecraft, offsetX + x,
							offsetY + y, mouseX - x, mouseY - y);
	}

	public void keyTyped(char par1, int par2) {
		invokeListenersKeyTyped(par1, par2);
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled())
					component.keyTyped(par1, par2);
	}

	public void mouseClicked(int mouseX, int mouseY, int button) {
		invokeListenersMouseDown(mouseX, mouseY, button);
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled()
						&& component.isMouseOver(mouseX, mouseY))
					component.mouseClicked(mouseX - component.x, mouseY
							- component.y, button);
	}

	public void mouseClickMove(int mouseX, int mouseY, int button, long time) {
		invokeListenersMouseDrag(mouseX, mouseY, button, time);
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled()
						&& component.isMouseOver(mouseX, mouseY))
					component.mouseClickMove(mouseX - component.x, mouseY
							- component.y, button, time);
	}

	public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
		if (button >= 0)
			invokeListenersMouseUp(mouseX, mouseY, button);
		else
			invokeListenersMouseMove(mouseX, mouseY);
		if (renderChildren)
			for (final BaseComponent component : components)
				if (component != null && component.isEnabled()
						&& component.isMouseOver(mouseX, mouseY))
					component.mouseMovedOrUp(mouseX - component.x, mouseY
							- component.y, button);
	}

	private void invokeListenersMouseDown(int offsetX, int offsetY, int button) {

		if (isMouseOver(offsetX + x, offsetY + y))
			for (final IComponentListener listener : listeners)
				listener.componentMouseDown(this, offsetX, offsetY, button);
	}

	private void invokeListenersMouseDrag(int offsetX, int offsetY, int button,
			long time) {
		if (isMouseOver(offsetX + x, offsetY + y))
			for (final IComponentListener listener : listeners)
				listener.componentMouseDrag(this, offsetX, offsetY, button,
						time);
	}

	private void invokeListenersMouseMove(int offsetX, int offsetY) {
		if (isMouseOver(offsetX + x, offsetY + y))
			for (final IComponentListener listener : listeners)
				listener.componentMouseMove(this, offsetX, offsetY);
	}

	private void invokeListenersKeyTyped(char par1, int par2) {
		for (final IComponentListener listener : listeners)
			listener.componentKeyTyped(this, par1, par2);
	}

	private void invokeListenersMouseUp(int offsetX, int offsetY, int button) {
		if (isMouseOver(offsetX + x, offsetY + y))
			for (final IComponentListener listener : listeners)
				listener.componentMouseUp(this, offsetX, offsetY, button);
	}

	public void bindTextureToClient(ResourceLocation texture) {
		if (texture != null)
			if (Minecraft.getMinecraft() != null)
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}

}
