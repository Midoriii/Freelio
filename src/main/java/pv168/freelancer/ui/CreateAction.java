package pv168.freelancer.ui;

import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CreateAction extends AbstractAction {

    private TestDataGenerator testDataGenerator;
    private JTable itemTable;

    public CreateAction(JTable itemTable, TestDataGenerator testDataGenerator) {
        super("Add", Icons.ADD_ICON);
        this.itemTable = itemTable;
        this.testDataGenerator = testDataGenerator;
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var wdtm = (WorkDoneTableModel) itemTable.getModel();
        wdtm.addRow(testDataGenerator.createTestWorkDone());
    }
}
