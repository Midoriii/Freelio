package pv168.freelancer.ui.details;

import org.jdatepicker.impl.JDatePickerImpl;
import pv168.freelancer.data.WorkTypeDao;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.CustomDocumentFilter;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.buttons.RoundedButtonSmall;
import pv168.freelancer.ui.tablemodels.WorkDoneTableModel;
import pv168.freelancer.ui.tablemodels.WorkTypeTableModel;
import pv168.freelancer.ui.utils.*;


import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * An editable dialog containing information about a single work done.
 *
 * @author xbenes2
 */
public class WorkDoneDetail extends JDialog {
    private boolean editing;

    private JPanel quitPanel;
    private JPanel contentPanel;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    private JTable workDoneTable;

    private JSpinner timePickerStart;
    private JSpinner timePickerEnd;
    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;

    private JComboBox<WorkType> workComboBox;
    private JTextArea description;

    private WorkTypeTableModel workTypeTable;
    private final WorkTypeDao workTypeDao;


    private final ComponentMover cm = new ComponentMover();

    private static final I18N I18N = new I18N(WorkDoneDetail.class);

    public WorkDoneDetail(JFrame owner, Boolean modality, JTable workDoneTable, WorkTypeDao workTypeDao, boolean editing) {
        super(owner, modality);
        setUpDialog();

        this.workTypeDao = workTypeDao;
        this.workComboBox = new JComboBox<>(workTypeDao.findAllWorkTypes().toArray(new WorkType[0]));

        this.timePickerStart = createTimePicker();
        this.timePickerEnd = createTimePicker();
        this.datePickerStart = ComponentFactory.createDatePicker();
        this.datePickerEnd = ComponentFactory.createDatePicker();
        this.editing = editing;
        this.workDoneTable = workDoneTable;
        this.workTypeTable = new WorkTypeTableModel(workTypeDao.findAllWorkTypes(), workTypeDao);


        quitPanel = ComponentFactory.createQuitPanel(owner, this, 450, 40);

        setUpContentPanel(owner);

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        if (editing) loadWorkDone(false);

        setVisible(true);
    }

    private void setUpContentPanel(JFrame owner) {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(450, 540));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(createTimeSelectPanel());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(createWorkSelectPanel(owner));
        contentPanel.add(createNotePanel());
        contentPanel.add(Box.createVerticalGlue());

        JButton btnOK = new JButton(I18N.getButtonString("confirm"));
        btnOK.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        btnOK.setAlignmentX(CENTER_ALIGNMENT);
        btnOK.addActionListener(new CreateWorkDoneAction());

        contentPanel.add(btnOK);
        contentPanel.add(Box.createVerticalStrut(50));
    }

    private void loadWorkDone(boolean updated) {
        WorkDone workDone = ((WorkDoneTableModel) workDoneTable.getModel()).getEntity(workDoneTable.getSelectedRow());
        description.setText(workDone.getDescription());

        timePickerStart.getModel().setValue(Date.from(
                workDone.getWorkStart().atZone(ZoneId.systemDefault()).toInstant()));
        timePickerEnd.getModel().setValue(Date.from(
                workDone.getWorkEnd().atZone(ZoneId.systemDefault()).toInstant()));
        datePickerStart.getModel().setDate(workDone.getWorkStart().getYear(), workDone.getWorkStart().getMonthValue() - 1, workDone.getWorkStart().getDayOfMonth());
        datePickerEnd.getModel().setDate(workDone.getWorkEnd().getYear(), workDone.getWorkEnd().getMonthValue() - 1, workDone.getWorkEnd().getDayOfMonth());
        if (!updated) {
            workComboBox.setSelectedItem(workDone.getWorkType());}
    }

    private JPanel createTimeSelectPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 50));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(450, 50));
        startPanel.setLayout(new FlowLayout());
        startPanel.add(Box.createHorizontalGlue());
        // The white spaces in label have to be there, otherwise Swing causes misalignment bug
        startPanel.add(new JLabel(I18N.getString("start") + "   "));
        startPanel.add(timePickerStart);
        startPanel.add(datePickerStart);
        startPanel.add(Box.createHorizontalGlue());

        JPanel endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(450, 50));
        endPanel.setLayout(new FlowLayout());
        startPanel.add(Box.createHorizontalGlue());
        endPanel.add(new JLabel(I18N.getString("end") + "   "));
        endPanel.add(timePickerEnd);
        endPanel.add(datePickerEnd);
        startPanel.add(Box.createHorizontalGlue());

        panel.add(startPanel);
        panel.add(endPanel);
        return panel;
    }

    private JSpinner createTimePicker() {
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(new Date());
        JSpinner timePicker = new JSpinner(timeModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(timePicker, "H:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        timePicker.setEditor(editor);

        return timePicker;
    }

    private JPanel createWorkSelectPanel(JFrame owner) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 80));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel workPanel = new JPanel();
        workPanel.setPreferredSize(new Dimension(450, 20));
        workPanel.setLayout(new FlowLayout());
        workPanel.add(new JLabel(I18N.getString("work")));

        workPanel.add(workComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(450, 40));
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton(I18N.getButtonString("add"));
        btnAdd.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.ADD_ICON_S));
        btnAdd.addActionListener(e -> addWorkType(e, owner));
        buttonPanel.add(btnAdd);

        btnEdit = new JButton(I18N.getButtonString("edit"));
        btnEdit.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.EDIT_ICON_S));
        btnEdit.addActionListener(e -> editWorkType(e, owner));
        buttonPanel.add(btnEdit);

        btnDelete = new JButton(I18N.getButtonString("delete"));
        btnDelete.setUI(new RoundedButtonSmall(new Color(246, 105, 94), Icons.DELETE_ICON_S));
        btnDelete.addActionListener(e -> deleteWorkType(e, owner));
        buttonPanel.add(btnDelete);

        panel.add(workPanel);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createNotePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 150));
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel(I18N.getString("note")));

        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        AbstractDocument absDoc = (AbstractDocument) description.getDocument();
        absDoc.setDocumentFilter(new CustomDocumentFilter(200));

        JScrollPane scroll = new JScrollPane(description);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(300, 100));
        panel.add(scroll);

        return panel;
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(450,600));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }

    private class CreateWorkDoneAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!checkWorkDoneValidity()) {
                return;
            }

            var workDoneTableModel = (WorkDoneTableModel) workDoneTable.getModel();

            if (editing) {
                WorkDone workDone = ((WorkDoneTableModel) workDoneTable.getModel()).getEntity(workDoneTable.getSelectedRow());
                WorkDone currentWorkDone = getWorkDone();
                currentWorkDone.setId(workDone.getId());
                workDoneTableModel.editRow(workDoneTable.getSelectedRow(), currentWorkDone);
            } else {
                workDoneTableModel.addRow(getWorkDone());
            }

            dispose();
        }

        private boolean checkWorkDoneValidity() {
            WorkDone workDone = getWorkDone();

            if (workDone.getWorkStart().isAfter(workDone.getWorkEnd())) {
                JOptionPane.showMessageDialog(null,
                        I18N.getDialogString("startEnd"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (workDone.getWorkType() == null) {
                JOptionPane.showMessageDialog(null,
                        I18N.getDialogString("workTypeChosen"),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            return true;
        }

        private WorkDone getWorkDone() {
            Date sDate = (Date) datePickerStart.getModel().getValue();
            LocalDate startDate = sDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date eDate = (Date) datePickerEnd.getModel().getValue();
            LocalDate endDate = eDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date sTime = ((SpinnerDateModel)timePickerStart.getModel()).getDate();
            LocalTime startTime = sTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            Date eTime = ((SpinnerDateModel)timePickerEnd.getModel()).getDate();
            LocalTime endTime = eTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            LocalDateTime workStart = startDate.atTime(startTime);
            LocalDateTime workEnd = endDate.atTime(endTime);
            return new WorkDone(workStart, workEnd, (WorkType) workComboBox.getSelectedItem(), description.getText());
        }
    }

    void updatePanel(JFrame owner) {
        remove(contentPanel);
        this.workComboBox = new JComboBox<>(workTypeDao.findAllWorkTypes().toArray(new WorkType[0]));

        setUpContentPanel(owner);
        add(contentPanel);
        if (editing) loadWorkDone(true);
        revalidate();
        repaint();
    }

    public void deleteWorkType(ActionEvent actionEvent, JFrame owner) {
        var workDoneTableModel = (WorkDoneTableModel) workDoneTable.getModel();
        if (workDoneTableModel.workTypeCount(((WorkType) workComboBox.getSelectedItem()).getId()) == 0) {
            workTypeTable.deleteRow(workComboBox.getSelectedIndex());
            updatePanel(owner);
        } else {
            JOptionPane.showMessageDialog(null, I18N.getDialogString("inUse"));
        }
    }

    private void editWorkType(ActionEvent e, JFrame owner) {
        new WorkTypeDetail(owner, true, workComboBox, workTypeTable, true);
        WorkType edited = (WorkType) workComboBox.getSelectedItem();
        updatePanel(owner);
        workComboBox.setSelectedItem(edited);
    }

    private void addWorkType(ActionEvent e, JFrame owner) {
        new WorkTypeDetail(owner, true, workComboBox, workTypeTable, false);
        updatePanel(owner);
        workComboBox.setSelectedIndex(workComboBox.getItemCount() - 1);
    }
}
