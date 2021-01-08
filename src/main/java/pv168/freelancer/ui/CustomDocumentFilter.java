package pv168.freelancer.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * Class for checking document length
 *
 * @author xkopeck5
 */
public class CustomDocumentFilter extends DocumentFilter {

    private final int len;

    public CustomDocumentFilter(int len) {
        this.len = len;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if ((fb.getDocument().getLength() + text.length()) <= len) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

}
