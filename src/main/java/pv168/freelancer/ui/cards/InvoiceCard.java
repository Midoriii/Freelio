package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

public class InvoiceCard extends Card {

    private Frame owner;

    private JLabel testText;

    public InvoiceCard(String name, Frame owner){
        super(name);
        this.owner = owner;
        testText = new JLabel("Invoices");
        add(testText, BorderLayout.CENTER);
    }
}
