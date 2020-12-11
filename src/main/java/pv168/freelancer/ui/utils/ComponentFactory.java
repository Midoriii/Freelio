package pv168.freelancer.ui.utils;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

/**
 * A helper class that creates some often used UI components.
 *
 * @author xbenes2
 */
public final class ComponentFactory {

    private static final I18N I18N = new I18N(ComponentFactory.class);

    /**
     * Used only for quit panels in Dialogs, as they are fixed size
     */
    public static JPanel createQuitPanel(JFrame owner, JDialog dialog, int width, int height){
        JPanel quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(width, height));

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> dialog.dispose()));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        return quitPanel;
    }

    /**
     * Creates a custom styled date picker
     */
    public static JDatePickerImpl createDatePicker(){
        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setValue(new Date());

        Properties p = new Properties();
        p.put("text.today", I18N.getString("today"));

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
}
