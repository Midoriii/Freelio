package pv168.freelancer.ui.cards;

import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.ui.DeleteAction;
import pv168.freelancer.ui.EditAction;
import pv168.freelancer.ui.WorkDoneDetail;
import pv168.freelancer.ui.WorkDoneTableModel;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.List;

public class WorkDoneCard extends Card {

    private final JFrame owner;

    private JPanel contentPanel;
    private JPanel btnPanel;

    private JTable workDoneTable;
    private Action editAction;
    private Action deleteAction;

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

        updateActions(workDoneTable.getSelectedRowCount());
    }

    private void setUpTable() {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        workDoneTable = createWorkDoneTable(testDataGenerator.createTestData(100));
        workDoneTable.setComponentPopupMenu(createWorkDoneTablePopupMenu());
    }

    private JTable createWorkDoneTable(List<WorkDone> worksDone) {
        var model = new WorkDoneTableModel(worksDone);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowHeight(20);
        return table;
    }

    private JPopupMenu createWorkDoneTablePopupMenu() {
        editAction = new EditAction(workDoneTable);
        deleteAction = new DeleteAction(workDoneTable);
        var menu = new JPopupMenu();
        menu.add(editAction);
        menu.add(deleteAction);
        return menu;
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

        // Fillers are empty resizable 'containers' that allow some sort of positioning.
        // Parameters are: MinSize, PrefSize, MaxSize
        btnPanel.add(new Box.Filler(new Dimension(180, 100), new Dimension(240, 180),
                     new Dimension(240, 250)));
        btnPanel.add(btnCreate);
        btnPanel.add(new Box.Filler(new Dimension(0, 8), new Dimension(0, 20),
                new Dimension(0, 35)));
        btnPanel.add(btnEdit);
        btnPanel.add(new Box.Filler(new Dimension(0, 8), new Dimension(0, 20),
                new Dimension(0, 35)));
        btnPanel.add(btnDelete);
        btnPanel.add(new Box.Filler(new Dimension(180, 130), new Dimension(240, 235),
                     new Dimension(240, 350)));

        add(btnPanel);
    }

    private void createButtons() {
        btnCreate = new JButton("Create");
        btnCreate.setUI(new RoundedButton(new Color(76, 175, 80), Icons.ADD_ICON));
        btnCreate.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnEdit = new JButton("Edit");
        btnEdit.setUI(new RoundedButton(new Color(76, 175, 80), Icons.EDIT_ICON));
        btnEdit.addActionListener(e -> new WorkDoneDetail(owner, true));

        btnDelete = new JButton("Delete");
        btnDelete.setUI(new RoundedButton(new Color(246, 105, 94), Icons.DELETE_ICON));
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

    private void updateActions(int selectedRowsCount) {
        btnEdit.setEnabled(selectedRowsCount == 1);
        btnDelete.setEnabled(selectedRowsCount >= 1);
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        updateActions(selectionModel.getSelectedItemsCount());
    }
}
