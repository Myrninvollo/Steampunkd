/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.utils;

public class Vector3fMax {
	private float xMin;
	private float yMin;
	private float zMin;

	private float xMax;
	private float yMax;
	private float zMax;

	public Vector3fMax(float xMin, float yMin, float zMin, float xMax,
			float yMax, float zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;

		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
	}

	public float getXMin() {
		return xMin;
	}

	public float getYMin() {
		return yMin;
	}

	public float getZMin() {
		return zMin;
	}

	public void setXMin(float newX) {
		xMin = newX;
	}

	public void setYMin(float newY) {
		yMin = newY;
	}

	public void setZMin(float newZ) {
		zMin = newZ;
	}

	public float getXMax() {
		return xMax;
	}

	public float getYMax() {
		return yMax;
	}

	public float getZMax() {
		return zMax;
	}

	public void setXMax(float newX) {
		xMax = newX;
	}

	public void setYMax(float newY) {
		yMax = newY;
	}

	public void setZMax(float newZ) {
		zMax = newZ;
	}
}