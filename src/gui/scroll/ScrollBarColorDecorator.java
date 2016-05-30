package gui.scroll;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 7, 2015, 8:07:57 AM 
 */
public class ScrollBarColorDecorator {
	public static void decorate( JScrollPane scroll, Color bg, Color fg ) {
		scroll.getHorizontalScrollBar().setUI( new CustomBasicScrollBarUI( bg, fg ) );
		scroll.getVerticalScrollBar().setUI( new CustomBasicScrollBarUI( bg, fg ) );
		Arrays.asList( new String[] { JScrollPane.LOWER_RIGHT_CORNER, JScrollPane.LOWER_LEFT_CORNER, JScrollPane.UPPER_LEFT_CORNER, JScrollPane.UPPER_RIGHT_CORNER } ).forEach( s -> {
			JPanel p = new JPanel();
			p.setBackground( bg );
			p.setForeground( fg );
			scroll.setCorner( s, p );
		} );
	}
}