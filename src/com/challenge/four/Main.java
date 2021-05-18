package com.challenge.four;


import com.util.math.SimpleLinearRegression;

public class Main {

    public void run(String[] args) {
        // independent, advertising
	double[] xs = {23.0, 26.0, 30.0, 34.0,
		      43.0, 48.0, 52.0, 57.0, 58.0};
	// dependent, sales
	double[] ys = {651.0, 762.0, 856.0, 1063.0, 1190.0, 1298.0,
		       1421.0, 1440.0, 1518.0};

	double x_input = 0.0;
	if (args.length > 0) {
	    try {
		x_input = Double.parseDouble(args[0]);
	    } catch (Exception e) {
		System.out.println(e);
		return;
	    }
	}

	SimpleLinearRegression slr = new SimpleLinearRegression(xs, ys);
	slr.calculate();
	slr.printData(x_input);
    }

    public static void main(String[] args) {
	try {
	    new Main().run(args);
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
