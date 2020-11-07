package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

public class InvoiceCard extends Card {

    private JLabel testText;

    public InvoiceCard(String name){
        super(name);
        testText = new JLabel("Invoices");
        add(testText, BorderLayout.CENTER);
    }
}
