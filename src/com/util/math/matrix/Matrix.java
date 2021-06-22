package com.util.math.matrix;

public class Matrix {
	private double x[][];
	private int rows;
	private int cols;	

	public Matrix(int row, int col) {
		this.rows = row;
		this.cols = col;
		x = new double[row][col];
	}

	public Matrix setMatrixArray(double x[][]) {
		this.x = x.clone();
		return this;
	}

	public Matrix clone() {
		Matrix r = new Matrix(rows, cols);
		r.setMatrixArray(x);
		return r;
	}

	private void replaceCol(double y[], int col) {
		if (x.length != y.length) {
			return;
		}
		for (int i = 0; i < x.length; i++) {
			x[i][col] = y[i];
		}
	}

	public Matrix createAndReplacedCol(double y[], int col) {
		Matrix r = this.clone();
		r.replaceCol(y, col);
		return r;
	}

	private double calcRowInc(int index) {
		int    j = index;
		double r = 1;
		for (int i = 0; i < x.length; i++) {
			r = r * x[(j++)%x.length][i];
		}
		return r;
	}

	private double calcRowDec(int index) {
		int    j = index;
		double r = 1;
		for (int i = x.length-1; i >= 0; i--) {
			r = r * x[(j++)%x.length][i];
		}
		return r;
	}

	public double calcDeterminant() {
		double r1 = 0;
		double r2 = 0;

		for (int i = 0; i < x.length; i++) {
			r1 += this.calcRowInc(i); 
			r2 += this.calcRowDec(i); 
		}

		return r1 - r2;
	}

	private void setValue(int row, int col, double value) {
		x[row][col] = value;
	}

	private double getValue(int row, int col) {
		return x[row][col];
	}

	public Matrix transpose() {
		Matrix r = this.clone();

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				r.setValue(col, row, this.getValue(row, col));
			}
		}

		return r;
	}

	public int getRowCount() {
		return rows;
	}

	public int getColCount() {
		return cols;
	}

	public Matrix mul(Matrix m) {
		Matrix r = this.clone();
		int other_cols = m.getColCount();
		for (int row = 0; row < rows; row++) {
			for (int other_col = 0; other_col < other_cols; other_col++) {
				double total = 0;
				for (int col = 0; col < cols; col++) {
					total += (this.getValue(row, col) * 
					          m.getValue(col, other_col));
				}				
				r.setValue(row, other_col, total);
			}
		}	
		return r;	
	}

	public Matrix identidy() {
		Matrix r = new Matrix(rows, cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (row == col) {
					r.setValue(row, col, 1);
					continue;
				}				
				r.setValue(row, col, 0);
			}
		}
		return r;
	}

	public Matrix inverse() {
		Matrix r = this.identidy();
		Matrix c = this.clone();
		for (int row = 0; row < rows; row++) {
			double inverse = 1/this.getValue(row, row);
			for (int col = 0; col < rows; col++) {
				c.setValue(row, col, c.getValue(row, col) * inverse);
				r.setValue(row, col, r.getValue(row, col) * inverse);
			}

			for (int i = 0; i < rows; i++) {
				if (i != row) {
					double neg = c.getValue(i, row) * -1;	
					for (int j = 0; j < rows; j++) {
						c.setValue(i, j, 
						           c.getValue(i, j) + 
							   (neg * c.getValue(row, j)));
						r.setValue(i, j, 
						           r.getValue(i, j) + 
							   (neg * r.getValue(row, j)));
					}

				}
			}
		}
		return r;
	}
}
