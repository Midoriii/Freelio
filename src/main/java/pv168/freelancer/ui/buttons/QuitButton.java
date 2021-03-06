package pv168.freelancer.ui.buttons;

import pv168.freelancer.ui.utils.Icons;

import java.awt.event.ActionListener;

/**
 * Simple quit button consisting of 3 icons.
 *
 * @author xbenes2
 */
public class QuitButton extends QuitPanelButton {

    public QuitButton(ActionListener listener){
        super();

        setIcon(Icons.QUIT_ICON);
        setRolloverIcon(Icons.QUIT_ICON_HOVER);
        setPressedIcon(Icons.QUIT_ICON_CLICK);

        super.addActionListener(listener);
    }
}
