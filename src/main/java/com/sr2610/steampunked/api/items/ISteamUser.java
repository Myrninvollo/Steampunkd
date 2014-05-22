package com.sr2610.steampunked.api.items;

import net.minecraft.item.ItemStack;

public interface ISteamUser {

	/**
	 * Gets the current steam level of the item.
	 * 
	 * @return the current steam level of the item.
	 */
	public abstract int getCurrentSteam(ItemStack itemStack);

	/**
	 * Gets the maximum steam of the item.
	 * 
	 * @return the maximum steam of the item.
	 */
	public abstract int getMaxSteam();

	/**
	 * Resteams the item represented by the ItemStack. Implementations should
	 * first check that the ItemStack is of the correct item type, then apply
	 * the steam (typically removing damage), and finally return the amount of
	 * steam used.
	 * 
	 * @param target
	 *            The ItemStack to refuel
	 * @param energyAvailable
	 *            The amount of energy put into the item in this invocation.
	 * @return The amount of energy consumed. If <code>steamAvailable</code> was
	 *         greater than the amount of steam needed to completely fill this
	 *         item, then the value returned will be less than
	 *         <code>steamAvailable</code>.
	 */
	public int fill(ItemStack target, int energyAvailable);

	public void addSteam(int steam, ItemStack stack);

}
