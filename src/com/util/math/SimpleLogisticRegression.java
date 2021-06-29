package com.util.math;

import com.util.math.matrix.Matrix;
import com.util.math.matrix.Vector;

public class SimpleLogisticRegression {
	private Matrix m;
	private double range;
	private int    loops;
	private double rate;
	private Vector categorial;

	public SimpleLogisticRegression(Matrix x, Vector y) {
		m = x;
		range = 0.5;
		loops = 100;
		rate = 0.01;
		categorial = y;
	}

	public String calculate(Vector inputs) {
		Vector w = gradientDescent(categorial);
		double p = vectorFunction(inputs, w);
		boolean classification = inRange(p);

		return String.format("P -> %f, Y -> %s", p, classification ? "True" : "False");
	}

	Vector gradientDescent(Vector y) {
		int rows_count = m.getRowCount();
		int cols_count = m.getColCount();
		Vector result = new Vector(rows_count);

		for (int i = 0; i <= loops; i++) {
			for (int row = 0; row < rows_count; row++) {
				double total = 0;
				for (int col = 0; col < cols_count; col++) {
					total += (matrixFunction(m, result, col) - y.get(col)) * m.getValue(row, col);
				}
				result.set(result.get(row) - rate * total, row);
			}	
		}

		return result;
	}

	boolean inRange(double value) {
		return value > range;
	}

	double vectorFunction(Vector a, Vector b) {
		double result = b.get(0);
		for (int index = 1; index < a.getCount(); index++) {
			result += b.get(index) * a.get(index);
		}
		return 1/(1+Math.pow(Math.E, -result));
	}

	double matrixFunction(Matrix m, Vector v, int i) {
		double result = v.get(0);
		int count = m.getRowCount();
		for (int index = 1; index < count; index++) {
			result += m.getValue(index, i) * v.get(index);
		}

		return 1/(1+Math.pow(Math.E, -result));
	}
}
