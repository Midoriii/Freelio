package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuitButton extends JButton {

    public QuitButton(ActionListener listener){
        super();
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setIcon(Icons.QUIT_ICON);

        addActionListener(listener);
    }
}
