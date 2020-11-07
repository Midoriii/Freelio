package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

public class WorkDoneCard extends Card {

    private JLabel testText;

    public WorkDoneCard(String name){
        super(name);
        testText = new JLabel("Work Done");
        add(testText, BorderLayout.CENTER);
    }
}
