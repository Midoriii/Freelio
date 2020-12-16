package pv168.freelancer.ui.details;

import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.CustomDocumentFilter;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.tablemodels.WorkTypeTableModel;
import pv168.freelancer.ui.utils.ComponentFactory;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * An editable dialog containing information about a single work type.
 *
 * @author xbenes2
 */
public class WorkTypeDetail extends JDialog {

    private boolean editingType;
    private JPanel quitPanel;
    private JPanel contentPanel;

    private JTextField nameField;
    private JTextField hourlyRateField;
    private JTextArea descriptionArea;
    JComboBox<WorkType> workComboBox;

    private WorkTypeTableModel workTypeTable;
    private final ComponentMover cm = new ComponentMover();

    private static final I18N I18N = new I18N(WorkTypeDetail.class);

    public WorkTypeDetail(JFrame owner, Boolean modality, JComboBox<WorkType> workComboBox, WorkTypeTableModel workTypeTable, boolean editingType){
        super(owner, modality);

        this.workTypeTable = workTypeTable;
        this.editingType = editingType;
        this.workComboBox = workComboBox;

        setUpDialog();

        quitPanel = ComponentFactory.createQuitPanel(owner, this, 450,40);

        setUpContentPanel();

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        if (editingType) loadWorkType();

        setVisible(true);
    }

    private void setUpContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.anchor = GridBagConstraints.PAGE_START;

        contentPanel.setPreferredSize(new Dimension(450, 540));
        addNameField(gbc);
        addHourlyRateField(gbc);
        addDescriptionArea(gbc);
        addConfirmButton(gbc);
    }

    private void addNameField(GridBagConstraints gbc) {
        JLabel name = new JLabel(I18N.getString("name"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(name, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        AbstractDocument absDoc = (AbstractDocument) nameField.getDocument();
        absDoc.setDocumentFilter(new CustomDocumentFilter(100));
        contentPanel.add(nameField, gbc);
    }

    private void addHourlyRateField(GridBagConstraints gbc) {
        JLabel hourlyRate = new JLabel(I18N.getString("rate"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(hourlyRate, gbc);

        hourlyRateField = new JTextField(8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(hourlyRateField, gbc);
    }

    private void addDescriptionArea(GridBagConstraints gbc) {
        JLabel description = new JLabel(I18N.getString("description"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(description, gbc);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(descriptionArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 100));
        AbstractDocument absDoc = (AbstractDocument) descriptionArea.getDocument();
        absDoc.setDocumentFilter(new CustomDocumentFilter(200));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(scroll, gbc);
    }

    private void addConfirmButton(GridBagConstraints gbc) {
        JButton okBtn = new JButton(I18N.getButtonString("confirm"));
        okBtn.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        okBtn.addActionListener(new CreateWorkTypeAction());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(100, 150, 20, 150);
        gbc.gridwidth = 2;
        contentPanel.add(okBtn, gbc);
    }

    private void loadWorkType() {
        WorkType workType = (WorkType) workComboBox.getSelectedItem();
        nameField.setText(workType.getName());
        hourlyRateField.setText(Double.toString(workType.getHourlyRate()));
        descriptionArea.setText(workType.getDescription());
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(450, 600));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }

    private class CreateWorkTypeAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkWorkTypeValidity()) {
                return;
            }

            if (editingType) {
                WorkType workType = workTypeTable.getEntity(workComboBox.getSelectedIndex());
                WorkType currentWorkType = getWorkType();
                currentWorkType.setId(workType.getId());
                workTypeTable.editRow(workComboBox.getSelectedIndex(), currentWorkType);
            } else {
                workTypeTable.addRow(getWorkType());
            }

            dispose();
        }

        private boolean checkWorkTypeValidity() {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        I18N.getDialogString("nameEmpty"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try
            {
                var hourlyRate = Double.parseDouble(hourlyRateField.getText());
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null,
                        I18N.getDialogString("validHourlyRate"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            return true;
        }

        private WorkType getWorkType() {
            return new WorkType(nameField.getText(), Double.parseDouble(hourlyRateField.getText()), descriptionArea.getText());
        }
    }
}