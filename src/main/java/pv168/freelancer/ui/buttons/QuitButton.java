package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.event.ActionListener;

public class QuitButton extends JButton {

    public QuitButton(ActionListener listener){
        super();
        setText("Quit");
        addActionListener(listener);
    }
}
