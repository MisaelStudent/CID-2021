package com.challenge.seven;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.util.math.SimpleLogisticRegression;
import com.util.math.matrix.Vector;

public class LogisticGui extends JFrame
{
    private JTextField                  inputRepeatsField;
    private JTextField                  inputsField;
    private SingleInputInterface        agent_interface;

    public LogisticGui(SingleInputInterface agent) {
        super(agent.getAgentLocalName());

    	agent_interface = agent;
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 3));
        p.add(new JLabel("Inputs:"));
        inputsField = new JTextField(15);
        p.add(inputsField);
        // p.add(new JLabel("Input Y:"));
        // inputYField = new JTextField(15);
        // p.add(inputYField);

        getContentPane().add(p, BorderLayout.CENTER);
        JButton addButton = new JButton("Accept");
        addButton.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    String[] str_inputs = inputsField.getText().trim().split(",");
                    try {
                        double[] inputs = new double[str_inputs.length];
                        int index = 0;
                        for (String str : str_inputs) {
                            inputs[index++] = Double.parseDouble(str);
                        }
                        LogisticInput input = new LogisticInput();
                        input.inputs = new Vector(inputs);
                        String error = agent_interface.onInput(input);
                        if (error != null) {
                            JOptionPane.showMessageDialog(LogisticGui.this, error,
                                                          "Error",
                                                          JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(LogisticGui.this, "Could not parse input to double",
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
