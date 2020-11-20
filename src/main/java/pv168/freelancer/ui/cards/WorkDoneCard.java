package pv168.freelancer.ui.cards;

import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.ui.WorkDoneDetail;
import pv168.freelancer.ui.WorkDoneTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
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
        workDoneTable = createWorkDoneTable(testDataGenerator.createTestData(100));
    }

    private void updateActions(int selectedRowsCount) {
        btnEdit.setEnabled(selectedRowsCount == 1);
        btnDelete.setEnabled(selectedRowsCount >= 1);
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        updateActions(selectionModel.getSelectedItemsCount());
    }

    private JTable createWorkDoneTable(List<WorkDone> worksDone) {
        var model = new WorkDoneTableModel(worksDone);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowHeight(20);
        return table;
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
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(Box.createVerticalGlue());

        add(btnPanel);
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
