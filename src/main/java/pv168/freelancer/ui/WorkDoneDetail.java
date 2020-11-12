package pv168.freelancer.ui;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.DateLabelFormatter;

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
    private JSpinner timePicker;
    private JDatePickerImpl datePicker;

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





        JButton btnAdd = new JButton("Add");
        btnAdd.setAlignmentX(CENTER_ALIGNMENT);
        btnAdd.addActionListener(e -> new WorkTypeDetail(owner, true));
        contentPanel.add(btnAdd, BorderLayout.CENTER);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(350, 60));

        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(new QuitButton(e -> dispose()));
    }

    private void createTimePicker() {
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(new Date());
        timePicker = new JSpinner(timeModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(timePicker, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        timePicker.setEditor(editor);
    }

    private void createDatePicker() {
        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setValue(new Date());
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
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
