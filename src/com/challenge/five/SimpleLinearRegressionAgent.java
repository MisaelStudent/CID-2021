package com.challenge.five;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import com.util.math.SimpleLinearRegression;

public class SimpleLinearRegressionAgent extends Agent
    implements SingleInputInterface
{
    private SingleInputGui gui;

    protected void setup() {
	gui = new SingleInputGui(this);
	gui.showGui();

	DFAgentDescription dfd = new DFAgentDescription();
	ServiceDescription service = new ServiceDescription();
	dfd.setName(getAID());
	service.setType("simple-linear-regression");
	service.setName("JADE-simple-linear-regression");

	dfd.addServices(service);
	try {
	    DFService.register(this, dfd);
	} catch (FIPAException e) {
	    e.printStackTrace();
	}

    }

    protected void takeDown() {
	try {
	    DFService.deregister(this);
	} catch (FIPAException e) {
	    e.printStackTrace();
	}
	gui.dispose();
    }

    @Override
    public void onClose() {
	this.doDelete();
    }

    @Override
    public String getAgentLocalName() {
	return getLocalName();
    }

    @Override
    public String onInput(Object object) {
	if (object instanceof String) {
	    String input_raw = (String)object;
	    try {
		double input = Double.parseDouble(input_raw);
		addBehaviour(new OneShotBehaviour() {
		    public void action() {
			// independent, advertising
			double[] xs = {23.0, 26.0, 30.0, 34.0,
				       43.0, 48.0, 52.0, 57.0, 58.0};
			// dependent, sales
			double[] ys = {651.0, 762.0, 856.0, 1063.0, 1190.0, 1298.0,
				       1421.0, 1440.0, 1518.0};

			SimpleLinearRegression slr = new SimpleLinearRegression(xs, ys);
			slr.calculate();
			slr.printData(input);
		    }
		});
	    } catch (Exception e) {
		return String.format("Could not parse to double. %s ",
				     e.getMessage());

	    }
	    return null;
	}
	return "Object is not a String class";
    }
}
