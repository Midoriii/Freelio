package pv168.freelancer.ui.utils;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;

import javax.swing.*;
import java.awt.*;

/**
 * A helper class that creates some often used UI components.
 *
 * @author xbenes2
 */
public final class ComponentFactory {

    /**
     * Used only for quit panels in Dialogs, as they are fixed size
     */
    public static JPanel createQuitPanel(JFrame owner, JDialog dialog, int width, int height){
        JPanel quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(width, height));

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> dialog.dispose()));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        return quitPanel;
    }
}
