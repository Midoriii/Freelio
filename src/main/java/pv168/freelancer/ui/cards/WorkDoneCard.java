package pv168.freelancer.ui.cards;

import pv168.freelancer.data.WorkDao;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.details.WorkDoneDetail;
import pv168.freelancer.ui.tablemodels.WorkDoneTableModel;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A card for cardLayout in MainWindow class, contains a basic overview of works done.
 *
 * @author xbenes2
 */
public class WorkDoneCard extends JPanel{

    private final JFrame owner;

    private JPanel contentPanel;
    private JPanel btnPanel;

    private JTable workDoneTable;
    private Action editAction;
    private Action deleteAction;

    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnDelete;

    private final WorkDao workDao;

    public final String name;

    public WorkDoneCard(String name, JFrame owner, WorkDao workDao){
        this.name = name;
        this.owner = owner;
        this.workDao = workDao;

        setPreferredSize(new Dimension(890, 635));

        createWorkDoneTable();

        createContentPanel();

        createButtonPanel();

        setUpGroupLayout();

        updateActions(workDoneTable.getSelectedRowCount());
    }

    private void createWorkDoneTable() {
        workDoneTable = setUpTable();
        workDoneTable.setComponentPopupMenu(createWorkDoneTablePopupMenu());
    }

    private JTable setUpTable() {
        var model = new WorkDoneTableModel(workDao);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowHeight(30);
        table.setShowGrid(false);

        setUpTableRenderers(table);

        return table;
    }

    private void setUpTableRenderers(JTable table) {
        // This will be built upon to style the table, so far only tests text centering
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(680, 40));
    }

    private JPopupMenu createWorkDoneTablePopupMenu() {
        editAction = new PopUpEditAction();
        deleteAction = new PopUpDeleteAction();
        var menu = new JPopupMenu();
        menu.add(editAction);
        menu.add(deleteAction);
        return menu;
    }

    private void createContentPanel() {
        JScrollPane tablePane = new JScrollPane(workDoneTable);
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tablePane.setBackground(new Color(76, 175, 80));
        tablePane.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));

        contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(470, 450));
        contentPanel.setPreferredSize(new Dimension(680, 600));
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
        btnPanel.setPreferredSize(new Dimension(220, 635));
        btnPanel.setMaximumSize(new Dimension(220, 820));
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
        btnCreate.addActionListener(this::addWorkDone);

        btnEdit = new JButton("Edit");
        btnEdit.setUI(new RoundedButton(new Color(76, 175, 80), Icons.EDIT_ICON));
        btnEdit.addActionListener(this::editWorkDone);

        btnDelete = new JButton("Delete");
        btnDelete.setUI(new RoundedButton(new Color(246, 105, 94), Icons.DELETE_ICON));
        btnDelete.addActionListener(this::deleteWorkDone);
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
        editAction.setEnabled(selectedRowsCount == 1);
        btnDelete.setEnabled(selectedRowsCount >= 1);
        deleteAction.setEnabled(selectedRowsCount >= 1);
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        updateActions(selectionModel.getSelectedItemsCount());
    }

    private void editWorkDone(ActionEvent e) {
        new WorkDoneDetail(owner, true, workDoneTable, workDao, true);
        workDoneTable.setModel(new WorkDoneTableModel(workDao));
        setUpTableRenderers(workDoneTable);
    }

    private void addWorkDone(ActionEvent e) {
        new WorkDoneDetail(owner, true, workDoneTable, workDao, false);
    }

    private void deleteWorkDone(ActionEvent e) {
        var workDoneTableModel = (WorkDoneTableModel) workDoneTable.getModel();
        Arrays.stream(workDoneTable.getSelectedRows())
                .map(workDoneTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(workDoneTableModel::deleteRow);
    }


    private class PopUpDeleteAction extends AbstractAction {

        public PopUpDeleteAction() {
            super("Delete", Icons.TOOLBAR_DELETE_ICON);
            putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteWorkDone(e);
        }
    }

    private class PopUpEditAction extends AbstractAction {
        public PopUpEditAction() {
            super("Edit", Icons.TOOLBAR_EDIT_ICON);
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editWorkDone(e);
        }
    }
}

