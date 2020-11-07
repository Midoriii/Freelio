package pv168.freelancer.ui.cards;


import pv168.freelancer.ui.WorkDoneDetail;

import javax.swing.*;
import java.awt.*;

public class WorkDoneCard extends Card {

    private final Frame owner;

    private JLabel testText;
    private JPanel btnPanel;

    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnDelete;

    public WorkDoneCard(String name, Frame owner){
        super(name);
        setLayout(new BorderLayout());
        this.owner = owner;

        // This will be replaced with setting up the actual Table
        setUpTestText();
        createButtonPanel();
    }

    private void createButtonPanel() {
        btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(180, 635));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));

        createButtons();

        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(btnCreate);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(Box.createVerticalGlue());

        add(btnPanel, BorderLayout.LINE_END);
    }

    private void createButtons() {
        btnCreate = new JButton("Create");
        btnCreate.setAlignmentX(CENTER_ALIGNMENT);
        btnCreate.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnEdit = new JButton("Edit");
        btnEdit.setAlignmentX(CENTER_ALIGNMENT);
        btnEdit.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnDelete = new JButton("Delete");
        btnDelete.setAlignmentX(CENTER_ALIGNMENT);
    }

    private void setUpTestText() {
        testText = new JLabel("Here lies a Table");
        testText.setVerticalAlignment(SwingConstants.CENTER);
        testText.setHorizontalAlignment(SwingConstants.CENTER);
        add(testText, BorderLayout.CENTER);
    }
}
