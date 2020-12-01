package pv168.freelancer.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class CustomDocumentFilter extends DocumentFilter {

    private int len;

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

//    javax.swing.text.DocumentFilter docFilter(int len) {
//        return new javax.swing.text.DocumentFilter() {
//            final int maxCharLength = len;
//
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                if ((fb.getDocument().getLength() + text.length()) <= maxCharLength) {
//                    super.replace(fb, offset, length, text, attrs);
//                } else {
//                    Toolkit.getDefaultToolkit().beep();
//                }
//            }
//        };
//    }
}
