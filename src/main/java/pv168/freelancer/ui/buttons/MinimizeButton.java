package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.*;

public class MinimizeButton extends QuitPanelButton {

    public MinimizeButton(JFrame frame){
        super();

        setIcon(Icons.MINIMIZE_ICON);
        setRolloverIcon(Icons.MINIMIZE_ICON_HOVER);
        setPressedIcon(Icons.MINIMIZE_ICON_CLICK);

        super.addActionListener(e -> frame.setState(Frame.ICONIFIED));
    }
}
