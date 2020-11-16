package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.*;

public class MinimizeButton extends JButton {

    public MinimizeButton(JFrame frame){
        super();
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setIcon(Icons.MINIMIZE_ICON);

        addActionListener(e -> frame.setState(Frame.ICONIFIED));
    }
}
