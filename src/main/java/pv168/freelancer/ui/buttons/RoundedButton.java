package pv168.freelancer.ui.buttons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom rounded-corners buttonUI.
 *
 * @author xbenes2
 */
public class RoundedButton extends BasicButtonUI {
    private static final float arcwidth  = 15.0f;
    private static final float archeight = 15.0f;
    protected static final int focusstroke = 0;
    protected final Color color;
    protected final Color hovercolor;
    protected final Color disabled = Color.gray;
    protected Shape shape;
    protected Shape border;
    protected Shape base;
    protected Icon icon;

    public RoundedButton(Color color, Icon icon){
        this.color = color;
        hovercolor = color.darker();
        this.icon = icon;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(new EmptyBorder(0,0,0,0));

        b.setMinimumSize(new Dimension(110,60));
        b.setPreferredSize(new Dimension(130,60));
        b.setMaximumSize(new Dimension(130,60));

        b.setBackground(color);
        b.setForeground(new Color(239, 245, 239));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setHorizontalAlignment(SwingConstants.LEFT);

        b.setIconTextGap(35);
        b.setIcon(icon);

        initShape(b);
    }

    @Override
    protected void installListeners(AbstractButton b) {
        BasicButtonListener listener = new BasicButtonListener(b) {
            @Override
            public void mousePressed(MouseEvent e) {
                AbstractButton b = (AbstractButton) e.getSource();
                initShape(b);
                if(shape.contains(e.getX(), e.getY())) {
                    super.mousePressed(e);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if(shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                }else{
                    super.mouseExited(e);
                }
            }
        };
        if(listener != null) {
            b.addMouseListener(listener);
            b.addMouseMotionListener(listener);
            b.addFocusListener(listener);
            b.addPropertyChangeListener(listener);
            b.addChangeListener(listener);
        }
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D)g;
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        initShape(b);
        //ContentArea
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if(model.isArmed()) {
            g2.setColor(hovercolor);
            g2.fill(shape);
        }else if(b.isRolloverEnabled() && model.isRollover()) {
            paintFocusAndRollover(g2, c, hovercolor);
        }else if(b.hasFocus()) {
            paintFocusAndRollover(g2, c, hovercolor);
        }else if(!b.isEnabled()){
            g2.setColor(disabled);
            g2.fill(shape);
        } else{
            g2.setColor(c.getBackground());
            g2.fill(shape);
        }

        g2.setColor(c.getBackground());

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

        super.paint(g2, c);
    }

    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text)
    {
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();

        g.setColor(b.getForeground());
        BasicGraphicsUtils.drawString(g,text, model.getMnemonic(), textRect.x,textRect.y + fm.getAscent());
    }

    protected void initShape(JComponent c) {
        if(!c.getBounds().equals(base)) {
            base = c.getBounds();
            shape = new RoundRectangle2D.Float(0, 0, c.getWidth()-1, c.getHeight()-1,
                    arcwidth, archeight);
            border = new RoundRectangle2D.Float(focusstroke, focusstroke,
                    c.getWidth()-1-focusstroke*2,
                    c.getHeight()-1-focusstroke*2,
                    arcwidth, archeight);
        }
    }

    private void paintFocusAndRollover(Graphics2D g2, JComponent c, Color color) {
        g2.setPaint(new GradientPaint(0, 0, color, c.getWidth()-1, c.getHeight()-1,
                color.brighter(), true));
        g2.fill(shape);
        g2.setColor(c.getBackground());
        g2.fill(border);
    }
}
