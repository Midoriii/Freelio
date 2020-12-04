package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * A smaller variant of custom rounded button UI.
 *
 * @author xbenes2
 */
public class RoundedButtonSmall extends RoundedButton{
    public RoundedButtonSmall(Color color, Icon icon) {
        super(color, icon);
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        b.setMinimumSize(new Dimension(70,32));
        b.setPreferredSize(new Dimension(75,32));
        b.setMaximumSize(new Dimension(75,32));

        b.setHorizontalAlignment(SwingConstants.LEFT);

        b.setIconTextGap(15);

        initShape(b);
    }
}
