package com.challenge.five;

import jade.core.Agent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class SingleInputGui extends JFrame {
    private JTextField                  inputField;
    private SingleInputInterface        agent_interface;

    public SingleInputGui(SingleInputInterface agent) {
	super(agent.getAgentLocalName());

    	agent_interface = agent;
	JPanel p = new JPanel();
	p.setLayout(new GridLayout(1, 2));
	p.add(new JLabel("Input:"));
	inputField = new JTextField(15);
	p.add(inputField);

	getContentPane().add(p, BorderLayout.CENTER);
	JButton addButton = new JButton("Accept");
	addButton.addActionListener( new ActionListener() {
	    public void actionPerformed(ActionEvent ev) {
		String error = agent_interface.onInput(inputField.getText().trim());
		if (error != null) {
		    JOptionPane.showMessageDialog(SingleInputGui.this, error,
						  "Error",
						  JOptionPane.ERROR_MESSAGE);
		}
	    }
	} );
	p = new JPanel();
	p.add(addButton);
	getContentPane().add(p, BorderLayout.SOUTH);

	addWindowListener(new	WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		agent_interface.onClose();
	    }
	});

	setResizable(false);
    }

    public void showGui() {
	pack();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int cy = (int)screenSize.getWidth() / 2;
	int cx = (int)screenSize.getHeight() / 2;

	setLocation (cx - getWidth() / 2, cy - getHeight() / 2);
	super.setVisible(true);
    }
}
