package gui.label;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Oct 20, 2013, 2:46:11 AM 
 */
public class ToggleLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private Icon on, off;
	
	private boolean tOn = false;
	
	public ToggleLabel() {
		super();
	}
	
	public ToggleLabel( Icon image ) {
		super( image );
	}
	
	public ToggleLabel( Icon image, int horizontalAlignment ) {
		super( image, horizontalAlignment );
	}
	
	public ToggleLabel( String text ) {
		super( text );
	}
	
	public ToggleLabel( String text, Icon icon, int horizontalAlignment ) {
		super( text, icon, horizontalAlignment );
	}
	
	public ToggleLabel( String text, int horizontalAlignment ) {
		super( text, horizontalAlignment );
	}
	
	public void setToggles( Icon off, Icon on ) {
		this.off = off;
		this.on = on;
	}
	
	public void setOn() {
		tOn = true;
		setIcon( on );
	}
	
	public void setOff() {
		tOn = false;
		setIcon( off );
	}
	
	public boolean isOn() {
		return tOn;
	}
}