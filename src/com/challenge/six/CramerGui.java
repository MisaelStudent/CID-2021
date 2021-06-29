package com.challenge.six;

public class CramerGui extends SingleInputInterface
{
    private JTextField                  inputXField;
    private JTextField                  inputYField;
    private SingleInputInterface        agent_interface;

    public CramerGui(SingleInputInterface agent) {
        super(agent.getAgentLocalName());

    	agent_interface = agent;
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 3));
        p.add(new JLabel("Input X:"));
        inputXField = new JTextField(15);
        p.add(inputXField);
        p.add(new JLabel("Input Y:"));
        inputYField = new JTextField(15);
        p.add(inputYField);

        getContentPane().add(p, BorderLayout.CENTER);
        JButton addButton = new JButton("Accept");
        addButton.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    TwoDoubleInput input = new TwoDoubleInput();
                    input.setInputStrings(inputXField.getText().trim(),
                                          inputYField.getText().trim());
                    String error = agent_interface.onInput(input);
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
