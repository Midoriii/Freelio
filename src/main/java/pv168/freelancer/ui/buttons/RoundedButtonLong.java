package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * An elongated variant of custom rounded button
 *
 * @author xbenes2
 */
public class RoundedButtonLong extends RoundedButton{
    public RoundedButtonLong(Color color, Icon icon) {
        super(color, icon);
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        b.setMinimumSize(new Dimension(150,60));
        b.setPreferredSize(new Dimension(165,60));
        b.setMaximumSize(new Dimension(165,60));

        b.setHorizontalAlignment(SwingConstants.LEFT);

        b.setIconTextGap(20);

        initShape(b);
    }
}
