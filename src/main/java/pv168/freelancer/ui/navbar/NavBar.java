package pv168.freelancer.ui.navbar;

import javax.swing.*;
import java.awt.*;

public class NavBar extends JPanel {

    private Label testText;

    public NavBar(){
        super();
        testText = new Label("This is a Navbar");
        add(testText);
        setPreferredSize(new Dimension(250, 740));
        setBackground(new Color(76, 175, 80));
    }
}
