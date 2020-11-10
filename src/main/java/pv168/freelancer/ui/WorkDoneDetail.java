package pv168.freelancer.ui;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.utils.ComponentMover;

import javax.swing.*;
import java.awt.*;

public class WorkDoneDetail extends JDialog {

    private JPanel quitPanel;
    private JPanel contentPanel;

    private final ComponentMover cm = new ComponentMover();

    public WorkDoneDetail(JFrame owner, Boolean modality){
        super(owner, modality);
        setUpDialog();

        // This will be refactored later on
        setUpQuitPanel(owner);

        setUpContentPanel(owner);

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        setVisible(true);
    }

    private void setUpContentPanel(JFrame owner) {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(350, 540));

        JButton btnAdd = new JButton("Add");
        btnAdd.setAlignmentX(CENTER_ALIGNMENT);
        btnAdd.addActionListener(e -> new WorkTypeDetail(owner, true));
        contentPanel.add(btnAdd, BorderLayout.CENTER);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(350, 60));

        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(new QuitButton(e -> dispose()));
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(350,600));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }
}
