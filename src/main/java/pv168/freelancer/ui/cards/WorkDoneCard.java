package pv168.freelancer.ui.cards;

import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.ui.WorkDoneDetail;
import pv168.freelancer.ui.buttons.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkDoneCard extends Card {

    private final JFrame owner;

    private JPanel contentPanel;
    private JPanel btnPanel;

    private JTable workDoneTable;

    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnDelete;

    public WorkDoneCard(String name, JFrame owner){
        super(name);
        this.owner = owner;

        // This will be replaced with setting up the actual Table
        setUpTable();

        createContentPanel();

        createButtonPanel();

        setUpGroupLayout();
    }

    private void setUpTable() {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        workDoneTable = new JTable(new DefaultTableModel(
                createWorkTable(testDataGenerator.createTestData(100), 100),
                new String[]{"Work Type", "From", "To", "Expected Pay"}
        ));
    }

    private Object[][] createWorkTable(List<WorkDone> worksDone, int count) {
        Object[][] result = new Object[count][];

        for (int i = 0; i < count; i++) {
            ArrayList<String>tuple = new ArrayList<>();
                tuple.add(worksDone.get(i).getWorkType().getName());
                tuple.add(worksDone.get(i).getWorkStart().toString());
                tuple.add(worksDone.get(i).getWorkEnd().toString());
                tuple.add(Double.toString(Math.ceil(Math.random() * 1000 + 100)));
            result[i] = (tuple.toArray());
        }
        return result;
    }

    private void createContentPanel() {
        JScrollPane tablePane = new JScrollPane(workDoneTable);
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(470, 450));
        contentPanel.setPreferredSize(new Dimension(660, 600));
        contentPanel.setMaximumSize(new Dimension(920, 820));

        GroupLayout groupLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(tablePane)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(tablePane)
        );

        contentPanel.setBorder(new EmptyBorder(100,40,50,0));
    }

    private void createButtonPanel() {
        btnPanel = new JPanel();
        btnPanel.setMinimumSize(new Dimension(180, 450));
        btnPanel.setPreferredSize(new Dimension(240, 635));
        btnPanel.setMaximumSize(new Dimension(240, 820));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));

        createButtons();

        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(btnCreate);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnPanel.add(btnEdit);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnPanel.add(btnDelete);
        btnPanel.add(Box.createVerticalGlue());

        add(btnPanel);
    }

    private void createButtons() {
        btnCreate = new JButton("Create");
        btnCreate.setUI(new RoundedButton(new Color(76, 175, 80)));
        btnCreate.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnEdit = new JButton("Edit");
        btnEdit.setUI(new RoundedButton(new Color(76, 175, 80)));
        btnEdit.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnDelete = new JButton("Delete");
        btnDelete.setUI(new RoundedButton(new Color(246, 105, 94)));
    }

    private void setUpGroupLayout() {
        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(contentPanel)
                        .addComponent(btnPanel)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(contentPanel)
                        .addComponent(btnPanel)
        );
    }
}
