package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.*;

public class MinimizeButton extends JButton {

    public MinimizeButton(JFrame frame){
        super();
        setText("Minimize");
        addActionListener(e -> frame.setState(Frame.ICONIFIED));
    }
}
