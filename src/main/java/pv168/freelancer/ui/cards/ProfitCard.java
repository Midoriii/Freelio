package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

public class ProfitCard extends Card {

    private JLabel testText;

    public ProfitCard(String name){
        super(name);
        testText = new JLabel("Profit Calculator");
        add(testText, BorderLayout.CENTER);
    }
}
