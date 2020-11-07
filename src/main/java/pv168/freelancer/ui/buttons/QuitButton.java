package pv168.freelancer.ui.buttons;

import javax.swing.*;

public class QuitButton extends JButton {

    public QuitButton(){
        super();
        setText("Quit");
        addActionListener(e -> System.exit(0));
    }
}
