package com.challenge.five;

import jade.core.Agent;

public interface SingleInputInterface  {

    public String onInput(Object object);

    public void onClose();

    public String getAgentLocalName();
}
