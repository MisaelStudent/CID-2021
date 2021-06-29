package com.challenge.six;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class CramerAgent extends Agent implements SingleInputInterface
{
    private CramerGui gui;

    protected void setup() {
        gui = new CramerGui(this);
        gui.showGui();

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        dfd.setName(getAID());
        service.setType("cramer");
        service.setName("JADE-cramer");

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
                            double y[] = {251.3, 248.3, 267.5};
                            Matrix m  = new Matrix(BETHAS_COUNT, BETHAS_COUNT).setMatrixArray(new double[][] {
                                    {1, 43.4, 29.3},
                                    {1, 43.9, 29.5},
                                    {1, 44.5, 29.7}
                                });
                            MultiLinearRegressionCramer mlrCramer = new MultiLinearRegressionCramer(m, y);
                            System.out.println(mlrCramer.calc().getResult(2.0, 1.0));
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
