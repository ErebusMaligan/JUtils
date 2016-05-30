package gui.windowmanager;

import javax.swing.JComponent;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 9:30:40 PM 
 */
public interface WindowDefinition {
	
	public JComponent getCenterComponent( Object state );
	
	public String getTitle();

}