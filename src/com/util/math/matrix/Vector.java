package com.util.math.matrix;

public class Vector {
	private double x[];
	
	public Vector(double x[]) {
		this.x = x.clone();
	}

	public Vector(int d) {
		this.x = new double[d];
	}

	public double get(int i) {
		return x[i];
	}

	public boolean set(double v, int i) {
		if (i >= x.length) {
			return false;
		}
		x[i] = v;
		return true;
	}

	public int getCount() {
		return x.length;
	}
}
