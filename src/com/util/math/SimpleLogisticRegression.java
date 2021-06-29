package com.util.math;

import com.util.math.matrix.Matrix;
import com.util.math.matrix.Vector;

public class SimpleLogisticRegression {
	private Matrix m;

	public SimpleLogisticRegression(Matrix x) {
		m = x;
	} 

	Vector gradientDescent(double a,int loops) {
		int rows_count = m.getRowCount();
		int cols_count = m.getColCount();
		Vector result = new Vector(rows_count);
		
		for (int i = 0; i <= loops; i++) {
			for (int row = 0; row < rows_count; row++) {
				double total = 0;
				for (int col = 0; col < cols_count; col++) {
					total += m.getValue(row, col);
				}
				result.set(result.get(row) - a * total, row);
			}	
		}

		return result;
	}

	double function(Vector a, Vector b) {
		double result = b.get(0);
		for (int index = 1; index < a.getCount(); index++) {
			result += b.get(index) * a.get(index);
		}
		return 1/(1+Math.pow(Math.E, -result));
	}

	double function(Matrix m, Vector v, int i) {
		double result = v.get(i);
		int count = m.getColCount() * m.getRowCount();
		for (int index = 1; index < count; index++) {
			result += v.get(index) * m.getValue(index, i);
		}

		return 1/(1+Math.pow(Math.E, -result));
	}
}
