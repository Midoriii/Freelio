package pv168.freelancer.ui;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.data.TestDataGenerator;
import pv168.freelancer.model.WorkType;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.DateLabelFormatter;

import java.util.List;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Calendar;
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

    private final ComponentMover cm = new ComponentMover();

    public WorkDoneDetail(JFrame owner, Boolean modality){
        super(owner, modality);
        setUpDialog();

        // This will be refactored later on


        setUpQuitPanel(owner);

        setUpContentPanel(owner);

        add(quitPanel, BorderLayout.NORTH);
        add(contentPanel);

        setUpMover();

        setVisible(true);
    }

    private void setUpContentPanel(JFrame owner) {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(350, 540));

        contentPanel.add(createTimeSelectPanel());
        contentPanel.add(createWorkSelectPanel(owner));





//        JButton btnAdd = new JButton("Add");
//        btnAdd.setAlignmentX(CENTER_ALIGNMENT);
//        btnAdd.addActionListener(e -> new WorkTypeDetail(owner, true));
//        contentPanel.add(btnAdd, BorderLayout.CENTER);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(350, 60));

        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(new QuitButton(e -> dispose()));
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
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private JPanel createTimeSelectPanel() {
        timePickerStart = createTimePicker();
        timePickerEnd = createTimePicker();
        datePickerStart = createDatePicker();
        datePickerEnd = createDatePicker();

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 100));

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(350, 50));
        startPanel.setLayout(new FlowLayout());
        startPanel.add(new JLabel("Start:"));
        startPanel.add(timePickerStart);
        startPanel.add(datePickerStart);

        JPanel endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(350, 50));
        endPanel.setLayout(new FlowLayout());
        endPanel.add(new JLabel("End:"));
        endPanel.add(timePickerEnd);
        endPanel.add(datePickerEnd);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        contentPanel.add(startPanel);
        contentPanel.add(endPanel);
        return panel;
    }

    private JPanel createWorkSelectPanel(JFrame owner) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 100));
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel("Work:"));

        WorkType[] testData = new TestDataGenerator().createWorkTypeTestData(5);
        workComboBox = new JComboBox<>(testData);
        panel.add(workComboBox);

        JButton btnAdd = new JButton("Add");
        btnAdd.setAlignmentX(CENTER_ALIGNMENT);
        btnAdd.addActionListener(e -> new WorkTypeDetail(owner, true));
        panel.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        panel.add(btnEdit);

        JButton btnDelete = new JButton("Delete");
        panel.add(btnDelete);

        return panel;
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(350,600));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }
}
