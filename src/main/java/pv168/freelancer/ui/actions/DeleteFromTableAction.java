package pv168.freelancer.ui.actions;

import pv168.freelancer.ui.tablemodels.WorkDoneTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteFromTableAction extends AbstractAction {

    private final JTable table;

    public DeleteFromTableAction(JTable table) {
        super("Delete");
        this.table = table;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var workDoneTableModel = (WorkDoneTableModel) table.getModel();
        Arrays.stream(table.getSelectedRows())
                .map(table::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(workDoneTableModel::deleteRow);
    }
}
