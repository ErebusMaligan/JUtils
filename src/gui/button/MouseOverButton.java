package gui.button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Dec 1, 2013, 3:19:06 AM 
 */
public class MouseOverButton extends JButton {

	private static final long serialVersionUID = 1L;

	public MouseOverButton( final ImageIcon off, final ImageIcon on ) {
		super( off );
		this.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseEntered( MouseEvent e ) {
				MouseOverButton.this.setIcon( on );
			}
			@Override
			public void mouseExited( MouseEvent e ) {
				MouseOverButton.this.setIcon( off );
			}
		} );
	}
}