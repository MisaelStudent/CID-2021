package com.util.math;

public class SimpleMath {
    public static double absolute(double n) {
	long bitmask = 0x7FFFFFFFFFFFFFFFL;
	long v = Double.doubleToLongBits(n);
	return Double.longBitsToDouble(v & bitmask);
    }

    public static double square(double n) {
	return n * n;
    }
}
