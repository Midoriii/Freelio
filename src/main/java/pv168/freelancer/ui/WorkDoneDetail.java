package pv168.freelancer.ui;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.data.WorkDao;
import pv168.freelancer.model.WorkDone;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.buttons.RoundedButtonSmall;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.DateLabelFormatter;
import pv168.freelancer.ui.utils.Icons;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

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
    private final WorkDao workDao;

    private final ComponentMover cm = new ComponentMover();

    public WorkDoneDetail(JFrame owner, Boolean modality, JTable workDoneTable, WorkDao workDao, boolean editing) {
        super(owner, modality);
        setUpDialog();

        this.workComboBox = new JComboBox<>(workDao.findAllWorkTypes().toArray(new WorkType[0]));
        this.timePickerStart = createTimePicker();
        this.timePickerEnd = createTimePicker();
        this.datePickerStart = createDatePicker();
        this.datePickerEnd = createDatePicker();
        this.editing = editing;
        this.workDao = workDao;
        this.workDoneTable = workDoneTable;
        this.workTypeTable = new WorkTypeTableModel(workDao.findAllWorkTypes(), workDao);

        setUpQuitPanel(owner);

        setUpContentPanel(owner);

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        if (editing) loadWorkDone(false);

        setVisible(true);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(450, 40));

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> dispose()));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
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

        JButton btnOK = new JButton("Confirm");
        btnOK.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        btnOK.setAlignmentX(CENTER_ALIGNMENT);
        btnOK.addActionListener(new CreateWorkDone());
        btnOK.addActionListener(e -> dispose());

        contentPanel.add(btnOK);
        contentPanel.add(Box.createVerticalStrut(50));
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

    private void loadWorkDone(boolean updated) {
        WorkDone workDone = ((WorkDoneTableModel) workDoneTable.getModel()).getEntity(workDoneTable.getSelectedRow());
        description.setText(workDone.getDescription());

        timePickerStart.getModel().setValue(Date.from(
                workDone.getWorkStart().atZone(ZoneId.systemDefault()).toInstant()));
        timePickerEnd.getModel().setValue(Date.from(
                workDone.getWorkEnd().atZone(ZoneId.systemDefault()).toInstant()));
        datePickerStart.getJFormattedTextField().setText(workDone.getWorkStart()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        datePickerEnd.getJFormattedTextField().setText(workDone.getWorkEnd()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
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
        startPanel.add(new JLabel("Start:   "));
        startPanel.add(timePickerStart);
        startPanel.add(datePickerStart);
        startPanel.add(Box.createHorizontalGlue());

        JPanel endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(450, 50));
        endPanel.setLayout(new FlowLayout());
        startPanel.add(Box.createHorizontalGlue());
        endPanel.add(new JLabel("End:   "));
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

    private JDatePickerImpl createDatePicker() {
        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setValue(new Date());

        Properties p = new Properties();
        p.put("text.today", "Dnes");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl dateImpl = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // What a fancy way to style the picker ...
        ((JFormattedTextField)dateImpl.getComponents()[0]).setBorder(new LineBorder(Color.BLACK));
        ((JButton)dateImpl.getComponents()[1]).setIcon(Icons.CALENDAR_ICON);
        ((JButton)dateImpl.getComponents()[1]).setText("");
        ((JButton)dateImpl.getComponents()[1]).setMargin(new Insets(0, 0, 0, 0));
        ((JButton)dateImpl.getComponents()[1]).setContentAreaFilled(false);
        ((JButton)dateImpl.getComponents()[1]).setFocusPainted(false);
        ((JButton)dateImpl.getComponents()[1]).setBorder(new LineBorder(Color.BLACK));

        JFormattedTextField textField = dateImpl.getJFormattedTextField();
        textField.setBackground(Color.WHITE);

        return dateImpl;
    }

    private JPanel createWorkSelectPanel(JFrame owner) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 80));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel workPanel = new JPanel();
        workPanel.setPreferredSize(new Dimension(450, 20));
        workPanel.setLayout(new FlowLayout());
        workPanel.add(new JLabel("Work:"));

        //workComboBox = new JComboBox<>(workDao.findAllWorkTypes().toArray(new WorkType[0]));
        workPanel.add(workComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(450, 40));
        buttonPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Add");
        btnAdd.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.ADD_ICON_S));
        btnAdd.addActionListener(e -> addWorkType(e, owner));
        buttonPanel.add(btnAdd);

        btnEdit = new JButton("Edit");
        btnEdit.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.EDIT_ICON_S));
        btnEdit.addActionListener(e -> editWorkType(e, owner));
        buttonPanel.add(btnEdit);

        btnDelete = new JButton("Delete");
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

        panel.add(new JLabel("Note:"));

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

    private class CreateWorkDone extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            var workDoneTableModel = (WorkDoneTableModel) workDoneTable.getModel();
            if (editing) {
                WorkDone workDone = ((WorkDoneTableModel) workDoneTable.getModel()).getEntity(workDoneTable.getSelectedRow());
                WorkDone currentWorkDone = getWorkDone();
                currentWorkDone.setId(workDone.getId());
                workDoneTableModel.editRow(workDoneTable.getSelectedRow(), currentWorkDone);
            } else {
                workDoneTableModel.addRow(getWorkDone());
            }
        }
    }
    void updatePanel(JFrame owner) {
        remove(contentPanel);
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
            JOptionPane.showMessageDialog(null, "This work type is in use.");
        }
    }

    private void editWorkType(ActionEvent e, JFrame owner) {
        new WorkTypeDetail(owner, true, workComboBox, workTypeTable, true);
        updatePanel(owner);
    }

    private void addWorkType(ActionEvent e, JFrame owner) {
        new WorkTypeDetail(owner, true, workComboBox, workTypeTable, false);
        updatePanel(owner);
    }
}
