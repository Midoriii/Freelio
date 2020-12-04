package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

public class InvoiceCard extends JPanel {

    private Frame owner;

    private JLabel testText;

    public final String name;

    public InvoiceCard(String name, Frame owner){
        this.name = name;
        this.owner = owner;
        setPreferredSize(new Dimension(890, 635));
        testText = new JLabel("Invoices");
        add(testText, BorderLayout.CENTER);
    }
}
