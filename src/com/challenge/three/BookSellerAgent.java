package com.challenge.three;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Hashtable;
import java.lang.Integer;

public class BookSellerAgent extends Agent {
    private Hashtable<String, Integer> catalogue;
    private BookSellerGui gui;

    protected void setup() {
	catalogue = new Hashtable<String, Integer>();
	gui = new BookSellerGui(this);
        gui.showGui();

	DFAgentDescription dfd = new DFAgentDescription();
	ServiceDescription service = new ServiceDescription();
	dfd.setName(getAID());
	service.setType("book-selling");
	service.setName("JADE-book-trading");

	dfd.addServices(service);
	try {
	    DFService.register(this, dfd);
	} catch (FIPAException e) {
	    e.printStackTrace();
	}

	addBehaviour(new OfferRequestsServer());
	addBehaviour(new PurchaseOrdersServer());
    }

    protected void takeDown() {
	try {
	    DFService.deregister(this);
	} catch (FIPAException e) {
	    e.printStackTrace();
	}
	gui.dispose();
    }

    public void updateCatalogue(String title, int price) {
	addBehaviour(new OneShotBehaviour() {
	    public void action() {
		catalogue.put(title, new Integer(price));
	    }
	});
    }

    private class OfferRequestsServer extends CyclicBehaviour {
	public void action() {
	    MessageTemplate template =
		MessageTemplate.MatchPerformative(ACLMessage.CFP);
	    ACLMessage msg = myAgent.receive(template);
	    if (msg == null) {
		block();
		return;
	    }
	    String title     = msg.getContent();
	    ACLMessage reply = msg.createReply();
	    Integer price    = catalogue.get(title);
	    if (price != null) {
		reply.setPerformative(ACLMessage.PROPOSE);
		reply.setContent(String.valueOf(price.intValue()));
	    } else {
		reply.setPerformative(ACLMessage.REFUSE);
		reply.setContent("not-available");
	    }
	    myAgent.send(reply);
	}
    }

    private class PurchaseOrdersServer extends CyclicBehaviour {
	public void action() {
	    MessageTemplate template =
		MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
	    ACLMessage msg = myAgent.receive(template);
	    if (msg == null) {
		block();
		return;
	    }
	    String title     = msg.getContent();
	    ACLMessage reply = msg.createReply();
	    Integer price    = catalogue.remove(title);
	    if (price != null) {
		reply.setPerformative(ACLMessage.INFORM);
		reply.setContent(String.format("%s sold to agent %s",
					       title,
					       msg.getSender().getName()));
	    } else {
		reply.setPerformative(ACLMessage.FAILURE);
		reply.setContent("not-available");
	    }
	    myAgent.send(reply);
	}
    }
}
