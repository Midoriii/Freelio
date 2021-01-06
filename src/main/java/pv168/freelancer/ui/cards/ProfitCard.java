package pv168.freelancer.ui.cards;


import org.jdatepicker.impl.JDatePickerImpl;
import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.services.ProfitCalculator;
import pv168.freelancer.ui.buttons.RoundedButtonLong;
import pv168.freelancer.ui.utils.ComponentFactory;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * A card for cardLayout in MainWindow class, contains profit calculation functionality.
 *
 * @author xbenes2
 */
public class ProfitCard extends JPanel {

    public final String name;

    private final JDatePickerImpl fromDate = createDatePicker();
    private final JDatePickerImpl toDate = createDatePicker();

    private JLabel profitLabel;
    private final WorkDoneDao workDoneDao;

    private static final pv168.freelancer.ui.utils.I18N I18N = new I18N(ProfitCard.class);

    public ProfitCard(String name, WorkDoneDao workDoneDao){
        this.name = name;
        this.workDoneDao = workDoneDao;
        setPreferredSize(new Dimension(890, 635));

        setUpGroupLayout(createUpperPanel(), createLowerPanel());
    }

    private JPanel createUpperPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(470, 200));
        panel.setPreferredSize(new Dimension(690, 200));
        panel.setMaximumSize(new Dimension(890, 200));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(20,100,0,0));

        panel.add(createDatePanel());
        panel.add(createProfitButton());

        return panel;
    }

    private JPanel createDatePanel(){
        JPanel panel = ComponentFactory.createFixedSizePanel(190, 120);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(40));
        panel.add(createDateRow(I18N.getString("from"), fromDate));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createDateRow(I18N.getString("to"), toDate));

        return panel;
    }

    private JPanel createDateRow(String labelName, JDatePickerImpl datePicker){
        JPanel panel = ComponentFactory.createFixedSizePanel(190, 30);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelName);

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(datePicker);

        return panel;
    }

    private JPanel createProfitButton(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(400, 120));
        panel.setPreferredSize(new Dimension(400, 120));
        panel.setMaximumSize(new Dimension(400, 120));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton btnCalculate = new JButton(I18N.getButtonString("profit"));
        btnCalculate.setUI(new RoundedButtonLong(new Color(76, 175, 80), Icons.DOLLAR_ICON));
        btnCalculate.addActionListener(e -> calculateProfit());

        panel.add(Box.createVerticalGlue());
        panel.add(btnCalculate);

        return panel;
    }

    private JPanel createLowerPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(470, 250));
        panel.setPreferredSize(new Dimension(690, 400));
        panel.setMaximumSize(new Dimension(890, 620));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(170,160,0,0));

        profitLabel = new JLabel(I18N.getString("profit"));
        profitLabel.setVisible(false);

        panel.add(profitLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JDatePickerImpl createDatePicker() {
        JDatePickerImpl dateImpl = ComponentFactory.createDatePicker();

        dateImpl.setMinimumSize(new Dimension(120,26));
        dateImpl.setPreferredSize(new Dimension(120,26));
        dateImpl.setMaximumSize(new Dimension(120,26));

        return dateImpl;
    }

    private void setUpGroupLayout(JPanel upper, JPanel lower) {
        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(upper)
                        .addComponent(lower)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(upper)
                        .addComponent(lower)
        );
    }

    public void calculateProfit(){
        Date sDate = (Date) fromDate.getModel().getValue();
        LocalDate startDate = sDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date eDate = (Date) toDate.getModel().getValue();
        LocalDate endDate = eDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        ProfitCalculator calculator = new ProfitCalculator(startDate, endDate, workDoneDao.findAllWorksDone());

        profitLabel.setText(String.format("<html>%s <font color=#4CAF50>%s $</font></html>",
                I18N.getString("profit"), calculator.calculateProfit().setScale(2, RoundingMode.HALF_EVEN)));
        profitLabel.setVisible(true);
    }
}
