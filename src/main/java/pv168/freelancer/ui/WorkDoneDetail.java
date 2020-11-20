package pv168.freelancer.ui;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.buttons.RoundedButtonSmall;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.DateLabelFormatter;
import pv168.freelancer.ui.utils.Icons;


import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

public class WorkDoneDetail extends JDialog {

    private JPanel quitPanel;
    private JPanel contentPanel;

    private JSpinner timePickerStart;
    private JSpinner timePickerEnd;
    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;

    private JComboBox<WorkType> workComboBox;
    private JTextArea note;

    private final ComponentMover cm = new ComponentMover();

    public WorkDoneDetail(JFrame owner, Boolean modality){
        super(owner, modality);
        setUpDialog();

        setUpQuitPanel(owner);

        setUpContentPanel(owner);

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

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

        contentPanel.add(createTimeSelectPanel());
        contentPanel.add(createWorkSelectPanel(owner));
        contentPanel.add(createNotePanel());

        JButton btnOK = new JButton("Confirm");
        btnOK.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        btnOK.setAlignmentX(CENTER_ALIGNMENT);
        btnOK.addActionListener(e -> dispose());
        contentPanel.add(btnOK, BorderLayout.CENTER);
    }

    private JPanel createTimeSelectPanel() {
        timePickerStart = createTimePicker();
        timePickerEnd = createTimePicker();
        datePickerStart = createDatePicker();
        datePickerEnd = createDatePicker();

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 100));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(450, 50));
        startPanel.setLayout(new FlowLayout());
        startPanel.add(new JLabel("Start:"));
        startPanel.add(timePickerStart);
        startPanel.add(datePickerStart);

        JPanel endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(450, 50));
        endPanel.setLayout(new FlowLayout());
        endPanel.add(new JLabel("End:"));
        endPanel.add(timePickerEnd);
        endPanel.add(datePickerEnd);

        panel.add(startPanel);
        panel.add(endPanel);
        return panel;
    }

    private JSpinner createTimePicker() {
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(new Date());
        JSpinner timePicker = new JSpinner(timeModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(timePicker, "HH:mm");
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

        JFormattedTextField textField = dateImpl.getJFormattedTextField();
        textField.setBackground(Color.WHITE);

        return dateImpl;
    }

    private JPanel createWorkSelectPanel(JFrame owner) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 80));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel workPanel = new JPanel();
        workPanel.setPreferredSize(new Dimension(450, 50));
        workPanel.setLayout(new FlowLayout());
        workPanel.add(new JLabel("Work:"));

        WorkType[] testData = new TestDataGenerator().createWorkTypeTestData(5);
        workComboBox = new JComboBox<>(testData);
        workPanel.add(workComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(450, 50));
        buttonPanel.setLayout(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        btnAdd.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.ADD_ICON_S));
        btnAdd.addActionListener(e -> new WorkTypeDetail(owner, true));
        buttonPanel.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setUI(new RoundedButtonSmall(new Color(76, 175, 80), Icons.EDIT_ICON_S));
        btnEdit.addActionListener(e -> new WorkTypeDetail(owner, true));
        buttonPanel.add(btnEdit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setUI(new RoundedButtonSmall(new Color(246, 105, 94), Icons.DELETE_ICON_S));
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

        note = new JTextArea();
        note.setLineWrap(true);
        note.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(note);
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
}
