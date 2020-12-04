package pv168.freelancer.ui.cards;


import javax.swing.*;
import java.awt.*;

/**
 * A card for cardLayout in MainWindow class, contains profit calculation functionality.
 *
 * @author xbenes2
 */
public class ProfitCard extends JPanel {

    private JLabel testText;

    public final String name;

    public ProfitCard(String name){
        this.name = name;
        setPreferredSize(new Dimension(890, 635));
        testText = new JLabel("Profit Calculator");
        add(testText, BorderLayout.CENTER);
    }
}
