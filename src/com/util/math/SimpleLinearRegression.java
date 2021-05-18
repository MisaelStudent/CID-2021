package com.util.math;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleLinearRegression {
    private ArrayList<Double> xs;
    private ArrayList<Double> ys;
    private double x_avarage;
    private double y_avarage;
    private double betha;
    private double betha_zero;
    private double y;

    public SimpleLinearRegression(ArrayList<Double> xs, ArrayList<Double> ys) {
	this.xs = xs;
	this.ys = ys;
    }

    public SimpleLinearRegression (double[] xs, double[] ys) {
	this.xs = new ArrayList<Double>();
	for (double x : xs) {
	    this.xs.add(x);
	}
	this.ys = new ArrayList<Double>();
	for (double y : ys) {
	    this.ys.add(y);
	}
    }

    public void calculate() {
	x_avarage  = calculateAvarage(xs);
	y_avarage  = calculateAvarage(ys);
	betha      = calculateBeta();
	betha_zero = y_avarage - betha * x_avarage;
    }

    public double calculateYOnX(double x_input) {
	return betha_zero + betha * x_input;
    }

    // TODO(Misael): Don't like to print anything to the console from
    // a class...
    public void printData(double x_input) {
	System.out.println(String.format("X avarage is %f", x_avarage));
	System.out.println(String.format("Y avarage is %f", y_avarage));
	System.out.println(String.format("Betha 1 is %f", betha));
	System.out.println(String.format("Betha 0 is %f", betha_zero));

	System.out.println(String.format("Y = %f + %fx",
					 betha_zero, betha));

	System.out.println(String.format("Y = %f + %f(%f) = %f",
					 betha_zero, betha, x_input,
					 calculateYOnX(x_input)));
    }

    private  double calculateAvarage(ArrayList<Double> xs) {
	double r = 0.0;
	for (double x : xs) {
	    r += x;
	}
	return r / xs.size();
    }

    private double calculateBeta()
    {
	double betha_num = 0.0;
	double betha_div = 0.0;
	for (int i = 0; i < xs.size() && i < ys.size(); i++) {
	    double x = xs.get(i);
	    double y = ys.get(i);

	    double x_line = x - x_avarage;
	    double y_line = y - y_avarage;
	    betha_num += x_line * y_line;
	    betha_div += SimpleMath.square(x_line);
	}
	return betha_num / betha_div;
    }
}
