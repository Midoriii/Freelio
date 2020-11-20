package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.*;

public class MinimizeButton extends QuitPanelButton {

    public MinimizeButton(JFrame frame){
        super();

        setIcon(Icons.MINIMIZE_ICON);
        setRolloverIcon(Icons.QUIT_ICON);

        super.addActionListener(e -> frame.setState(Frame.ICONIFIED));
    }
}
