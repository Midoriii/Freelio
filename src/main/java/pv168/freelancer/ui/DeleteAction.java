package pv168.freelancer.ui;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public final class DeleteAction extends AbstractAction {

    private final JTable itemTable;

    public DeleteAction(JTable itemTable) {
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
