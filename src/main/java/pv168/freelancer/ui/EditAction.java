package pv168.freelancer.ui;

import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class EditAction extends AbstractAction {

    private final JTable itemTable;

    public EditAction(JTable itemTable) {
        super("Edit", Icons.EDIT_ICON);
        this.itemTable = itemTable;
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = itemTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        JOptionPane.showMessageDialog(itemTable,
                "This operation is not implemented yet",
                "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
