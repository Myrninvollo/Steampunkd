package com.sr2610.steampunked.api.utils;

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
