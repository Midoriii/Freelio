package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import java.awt.event.ActionListener;

public class QuitButton extends QuitPanelButton {

    public QuitButton(ActionListener listener){
        super();

        setIcon(Icons.QUIT_ICON);
        setPressedIcon(Icons.MINIMIZE_ICON);

        super.addActionListener(listener);
    }
}
