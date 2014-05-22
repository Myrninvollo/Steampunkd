/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.api.utils;

public interface IRedstoneControl extends IRedstoneCache {

	public static enum ControlMode {
		DISABLED(true), LOW(false), HIGH(true);

		private final boolean state;

		private ControlMode(boolean state) {

			this.state = state;
		}

		public boolean isDisabled() {

			return this == DISABLED;
		}

		public boolean isLow() {

			return this == LOW;
		}

		public boolean isHigh() {

			return this == HIGH;
		}

		public boolean getState() {

			return state;
		}

		public static ControlMode stepForward(ControlMode curControl) {

			return curControl == DISABLED ? LOW : curControl == HIGH ? DISABLED
					: HIGH;
		}

		public static ControlMode stepBackward(ControlMode curControl) {

			return curControl == DISABLED ? HIGH : curControl == HIGH ? LOW
					: DISABLED;
		}
	}

	public void setControl(ControlMode control);

	public ControlMode getControl();

}
