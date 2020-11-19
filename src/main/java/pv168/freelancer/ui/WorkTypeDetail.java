package pv168.freelancer.ui;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.utils.ComponentMover;

import javax.swing.*;
import java.awt.*;

public class WorkTypeDetail extends JDialog {
    private JPanel quitPanel;
    private JPanel contentPanel;

    private JTextField nameField;
    private JTextField hourlyRateField;
    private JTextArea descriptionArea;


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

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(450, 40));

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> dispose()));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
    }

    private void setUpContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.anchor = GridBagConstraints.PAGE_START;

        contentPanel.setPreferredSize(new Dimension(450, 540));
        addNameField(gbc);
        addHourlyRateField(gbc);
        addDescriptionArea(gbc);
        addConfirmButton(gbc);
    }

    private void addNameField(GridBagConstraints gbc) {
        JLabel name = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(name, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(nameField, gbc);
    }

    private void addHourlyRateField(GridBagConstraints gbc) {
        JLabel hourlyRate = new JLabel("Hourly rate:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(hourlyRate, gbc);

        hourlyRateField = new JTextField(8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(hourlyRateField, gbc);
    }

    private void addDescriptionArea(GridBagConstraints gbc) {
        JLabel description = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(description, gbc);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(descriptionArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 100));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(scroll, gbc);
    }

    private void addConfirmButton(GridBagConstraints gbc) {
        JButton okBtn = new JButton("Confirm");
        okBtn.setUI(new RoundedButton(new Color(76, 175, 80)));
        okBtn.addActionListener(e -> dispose());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(100, 150, 20, 150);
        gbc.gridwidth = 2;
        contentPanel.add(okBtn, gbc);
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
