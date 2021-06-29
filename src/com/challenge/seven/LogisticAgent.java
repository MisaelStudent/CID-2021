package com.challenge.seven;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import com.util.math.SimpleLogisticRegression;
import com.util.math.matrix.*;

public class LogisticAgent extends Agent implements SingleInputInterface
{
    private LogisticGui gui;

    protected void setup() {
        gui = new LogisticGui(this);
        gui.showGui();

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        dfd.setName(getAID());
        service.setType("logistic");
        service.setName("JADE-logistic");

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
        if (object instanceof LogisticInput) {
            LogisticInput input = (LogisticInput)object;
            addBehaviour(new OneShotBehaviour() {
                    public void action() {
                        Matrix m  = new Matrix(3, 3).setMatrixArray(new double[][] {
                                {1, 1, 1},
                                {1, 4, 2},
                                {1, 2, 4}
                            });
                        Vector y = new Vector(new double[] {0, 1, 1});
                        SimpleLogisticRegression slr = new SimpleLogisticRegression(m, y);
                        String result = slr.calculate(input.inputs);
                        System.out.println(result);
                    }
                });
            return null;
        }
        return "Object is not a LogisticInput class";
    }

}
