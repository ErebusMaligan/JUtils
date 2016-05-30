package gui.combobox;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComboBox;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 2, 2015, 3:23:23 PM 
 */
public class ScrollableComboBoxListener implements MouseWheelListener {
	
	private JComboBox<?> combo;
	
	public ScrollableComboBoxListener( JComboBox<?> combo ) {
		this.combo = combo;
	}
	
	@Override
	public void mouseWheelMoved( MouseWheelEvent e ) {
		int c = e.getWheelRotation();
		int i = combo.getSelectedIndex();
		int s = combo.getModel().getSize();
		combo.setSelectedIndex( i + c < 0 ? 0 : i + c >= s ? s - 1 : i + c );
	}
}