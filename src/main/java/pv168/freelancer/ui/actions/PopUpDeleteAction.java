package pv168.freelancer.ui.actions;

import pv168.freelancer.ui.tablemodels.WorkDoneTableModel;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public final class PopUpDeleteAction extends AbstractAction {

    private final JTable itemTable;

    public PopUpDeleteAction(JTable itemTable) {
        super("Delete", Icons.TOOLBAR_DELETE_ICON);
        this.itemTable = itemTable;
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var workDoneTableModel = (WorkDoneTableModel) itemTable.getModel();
        Arrays.stream(itemTable.getSelectedRows())
                .map(itemTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(workDoneTableModel::deleteRow);
    }
}
