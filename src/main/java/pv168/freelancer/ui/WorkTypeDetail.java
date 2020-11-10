package pv168.freelancer.ui;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.utils.ComponentMover;

import javax.swing.*;
import java.awt.*;

public class WorkTypeDetail extends JDialog {
    private JPanel quitPanel;
    private JPanel contentPanel;

    private final ComponentMover cm = new ComponentMover();

    public WorkTypeDetail(JFrame owner, Boolean modality){
        super(owner, modality);
        setUpDialog();

        setUpQuitPanel(owner);

        setUpContentPanel();

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        setVisible(true);
    }

    private void setUpContentPanel() {
        JLabel testText = new JLabel("Ha!");
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(450, 540));
        contentPanel.add(testText);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(450, 60));

        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(new QuitButton(e -> dispose()));
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(450,600));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }
}
