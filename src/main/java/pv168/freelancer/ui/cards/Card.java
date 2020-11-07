package pv168.freelancer.ui.cards;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {
    // CardLayout uses this attribute to find and show Cards.
    public final String name;

    public Card(String name){
        this.name = name;
        setPreferredSize(new Dimension(950, 680));
        setBackground(Color.RED);
    }
}
