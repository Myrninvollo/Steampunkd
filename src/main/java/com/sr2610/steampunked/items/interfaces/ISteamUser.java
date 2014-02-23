package com.sr2610.steampunked.items.interfaces;

import net.minecraft.item.ItemStack;

public interface ISteamUser {

	/**
	 * Gets the current charge level of the item.
	 * 
	 * @return the current charge level of the item.
	 */
	public abstract int getCurrentSteam(ItemStack itemStack);

	/**
	 * Gets the maximum charge level of the item.
	 * 
	 * @return the maximum charge level of the item.
	 */
	public abstract int getMaxSteam();

	/**
	 * Recharges the item represented by the ItemStack. Implementations should
	 * first check that the ItemStack is of the correct item type, then apply
	 * the charge (typically removing damage), and finally return the amount of
	 * energy used.
	 * 
	 * @param target
	 *            The ItemStack to recharge
	 * @param energyAvailable
	 *            The amount of energy put into the item in this invocation.
	 * @return The amount of energy consumed. If <code>energyAvailable</code>
	 *         was greater than the amount of energy needed to completely charge
	 *         this item, then the value returned will be less than
	 *         <code>energyAvailable</code>.
	 */
	public int charge(ItemStack target, int energyAvailable);

	public void addCharge(int charge, ItemStack stack);

}