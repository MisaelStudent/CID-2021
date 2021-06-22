package com.challenge.three;

// import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class BookSellerGui extends JFrame {
    private BookSellerAgent seller;
    private JTextField titleField;
    private JTextField priceField;

    BookSellerGui(BookSellerAgent agent) {
	super(agent.getLocalName());

	seller = agent;
	JPanel p = new JPanel();
	p.setLayout(new GridLayout(2, 2));
	p.add(new JLabel("Book title:"));
	titleField = new JTextField(15);
	p.add(titleField);
	p.add(new JLabel("Price:"));
	priceField = new JTextField(15);
	p.add(priceField);
	getContentPane().add(p, BorderLayout.CENTER);

	JButton addButton = new JButton("Add");
	addButton.addActionListener( new ActionListener() {
	    public void actionPerformed(ActionEvent ev) {
		try {
		    String title = titleField.getText().trim();
		    String price = priceField.getText().trim();
		    seller.updateCatalogue(title, Integer.parseInt(price));
		    titleField.setText("");
		    priceField.setText("");
		}
		catch (Exception e) {
		    JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	} );
	p = new JPanel();
	p.add(addButton);
	getContentPane().add(p, BorderLayout.SOUTH);

	addWindowListener(new	WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		seller.doDelete();
	    }
	} );

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
