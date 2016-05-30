package gui.button;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 9:37:29 PM 
 */
public class ToggleButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Icon on;
	
	private Icon off;
	
	public ToggleButton( Icon off, Icon on, boolean s ) {
		this.on = on;
		this.off = off;
		this.setIcon( s ? on : off );
		this.setContentAreaFilled( false );
		this.setOpaque( false );
		setSelected( s );
	}
	
	@Override
	protected void fireActionPerformed( ActionEvent event ) {
		if ( this.isSelected() ) {
			this.setSelected( false );
			this.setIcon( off );
		} else {
			this.setSelected( true );
			this.setIcon( on );
		}
		super.fireActionPerformed( event );
	}
	
	public void setState( boolean state ) {
		this.setSelected( state );
		this.setIcon( state ? on : off );
	}
}
