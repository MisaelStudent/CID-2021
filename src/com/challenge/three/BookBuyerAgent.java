package com.challenge.three;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {
    private String bookTitle;
    private AID[]  sellerAgents;

    protected void setup() {
	Object[] args = getArguments();
	if (args != null && args.length > 0) {
	    bookTitle = (String) args[0];
	    System.out.println(String.format("Buyer wants %s", bookTitle));

	    addBehaviour(new TickerBehaviour(this, 1000) {
		protected void onTick() {
		    DFAgentDescription template = new DFAgentDescription();
		    ServiceDescription service  = new ServiceDescription();
		    service.setType("book-selling");
		    template.addServices(service);

		    try {
			DFAgentDescription[] result =
			    DFService.search(myAgent, template);

			sellerAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
			    sellerAgents[i] = result[i].getName();
			    System.out.println(String.format("Seller found %s",
						   sellerAgents[i]));
			}
		    }
		    catch (FIPAException e) {
			e.printStackTrace();
		    }

		    myAgent.addBehaviour(new RequestPerformer());
		}
	    });
	} else {
	    System.out.println("Buyer has no args");
	    doDelete();
	}
    }

    protected void takeDown() {
	// ....
    }

    private class RequestPerformer extends Behaviour {
	private AID bestSeller;
	private int bestPrice;
	private int repliesCnt = 0;
	private MessageTemplate template;
	private int step = 0;

	public void action() {
	    switch (step) {
	    case 0:
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		for (int i = 0; i < sellerAgents.length; ++i) {
		    cfp.addReceiver(sellerAgents[i]);
		}
		cfp.setContent(bookTitle);
		cfp.setConversationId("book-trade");
		cfp.setReplyWith("cfp"+System.currentTimeMillis());
		myAgent.send(cfp);
		template = MessageTemplate.and(
			MessageTemplate.MatchConversationId("book-trade"),
			MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
		step = 1;
		break;
	    case 1:
		ACLMessage reply = myAgent.receive(template);
		if (reply != null) {
		    if (reply.getPerformative() == ACLMessage.PROPOSE) {
			int price = Integer.parseInt(reply.getContent());
			if (bestSeller == null || price < bestPrice) {
			    bestPrice = price;
			    bestSeller = reply.getSender();
			}
		    }
		    repliesCnt++;
		    if (repliesCnt >= sellerAgents.length) {
			step = 2;
		    }
		}
		else {
		    block();
		}
		break;
	    case 2:
		ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		order.addReceiver(bestSeller);
		order.setContent(bookTitle);
		order.setConversationId("book-trade");
		order.setReplyWith("order"+System.currentTimeMillis());
		myAgent.send(order);
		template = MessageTemplate.and(
		    MessageTemplate.MatchConversationId("book-trade"),
		    MessageTemplate.MatchInReplyTo(order.getReplyWith()));
		step = 3;
		break;
	    case 3:
		reply = myAgent.receive(template);
		if (reply != null) {
		    if (reply.getPerformative() == ACLMessage.INFORM) {
			System.out.println(bookTitle+
					   " successfully purchased from agent "+
					   reply.getSender().getName());
			System.out.println("Price = "+bestPrice);
			myAgent.doDelete();
		    }
		    else {
			System.out.println(
			    "Attempt failed: requested book already sold.");
		    }

		    step = 4;
		}
		else {
		    block();
		}
		break;
	    }
	}

	public boolean done() {
	    if (step == 2 && bestSeller == null) {
		System.out.println("Attempt failed: "+
				   bookTitle+
				   " not available for sale");
	    }
	    return ((step == 2 && bestSeller == null) || step == 4);
	}
    }
}
