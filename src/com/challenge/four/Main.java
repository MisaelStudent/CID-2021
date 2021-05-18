package com.challenge.four;

import java.util.ArrayList;

import com.util.math.SimpleMath;

public class Main {

    public double calculateBeta(double[] xs, double[] ys,
				double x_avarage, double y_avarage)
    {
	double betha_num = 0.0;
	double betha_div = 0.0;
	for (int i = 0; i < xs.length && i < ys.length; i++) {
	    double x = xs[i];
	    double y = ys[i];

	    double x_line = x - x_avarage;
	    double y_line = y - y_avarage;
	    betha_num += x_line * y_line;
	    betha_div += SimpleMath.square (x_line);
	}
	return betha_num / betha_div;
    }

    public double calculateAvarage(double[] xs) {
	double r = 0.0;
	for (double x : xs) {
	    r += x;
	}
	return r / xs.length;
    }

    public void run() {
        // independent, advertising
	double[] xs = {23.0, 26.0, 30.0, 34.0,
		      43.0, 48.0, 52.0, 57.0, 58.0};
	// dependent, sales
	double[] ys = {651.0, 762.0, 856.0, 1063.0, 1190.0, 1298.0,
		      1421.0, 1440.0, 1518.0};


	System.out.println(String.format("Abs of %f is %f",
					 10.0, SimpleMath.absolute(10.0)));
	System.out.println(String.format("Abs of %f is %f",
					 -10.0, SimpleMath.absolute(-10.0)));
	System.out.println(String.format("Abs of %f is %f",
					 -10.123123, SimpleMath.absolute(-10.123123)));

	double x_input   = 1.0;

	double x_avarage = calculateAvarage(xs);
	double y_avarage = calculateAvarage(ys);
	double betha     = calculateBeta(xs, ys, x_avarage, y_avarage);
	double betha_0   = y_avarage - betha*x_avarage;
	System.out.println(String.format("X avarage is %f", x_avarage));
	System.out.println(String.format("Y avarage is %f", y_avarage));
	System.out.println(String.format("Betha 1 is %f", betha));
	System.out.println(String.format("Betha 0 is %f", betha_0));

	System.out.println(String.format("Y = %f + %fx",
					 betha_0, betha));

	double y         = betha_0 + betha*x_input;
	System.out.println(String.format("Y = %f + %f(%f) = %f",
					 betha_0, betha, x_input, y));
    }

    public static void main(String[] args) {
	try {
	    new Main().run();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
