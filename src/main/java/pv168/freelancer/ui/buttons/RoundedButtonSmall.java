package pv168.freelancer.ui.buttons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButtonSmall extends RoundedButton{
    public RoundedButtonSmall(Color color) {
        super(color);
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        b.setMinimumSize(new Dimension(60,32));
        b.setPreferredSize(new Dimension(70,32));
        b.setMaximumSize(new Dimension(70,32));

        initShape(b);
    }
}
