package com.challenge.six;

import com.util.math.matrix.Matrix;

public class MultiLinearRegressionCramer {
	private static final int BETHAS_COUNT = 3; 
	private double bethas[];
	private Matrix x;
	private double y[];

	public static void main(String args[]) {
		double y[] = {251.3, 248.3, 267.5};
    		Matrix m  = new Matrix(BETHAS_COUNT, BETHAS_COUNT).setMatrixArray(new double[][] {
    			    {1, 43.4, 29.3},
    			    {1, 43.9, 29.5},
    			    {1, 44.5, 29.7}
    		});		
		MultiLinearRegressionCramer mlrCramer = new MultiLinearRegressionCramer(m, y);
		System.out.println(mlrCramer.calc().getResult(2.0, 1.0));
	}

	public MultiLinearRegressionCramer(Matrix m, double y[]) {
		bethas = new double[BETHAS_COUNT];
		this.x = m.clone(); 
		this.y = y;
	}

	public MultiLinearRegressionCramer calc() {
		double ds  = x.calcDeterminant();
		bethas[0] = x.createAndReplacedCol(y, 0).calcDeterminant() / ds;
		bethas[1] = x.createAndReplacedCol(y, 1).calcDeterminant() / ds; 
		bethas[2] = x.createAndReplacedCol(y, 2).calcDeterminant() / ds;
		return this;
	}

	public double getResult(double x1, double x2) {
		return bethas[0] + bethas[1] * x1 + bethas[2] * x2;
	}

}
