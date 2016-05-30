package gui.table.renderer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageIconCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    public ImageIconCellRenderer() {
    }

    protected void setValue(Object value) {
        setIcon((ImageIcon)value);
        setHorizontalAlignment(JLabel.CENTER);
    }

}