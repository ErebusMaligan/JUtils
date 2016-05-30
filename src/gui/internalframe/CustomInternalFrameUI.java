package gui.internalframe;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 10, 2015, 3:32:37 AM 
 */
public class CustomInternalFrameUI extends BasicInternalFrameUI {

	public CustomInternalFrameUI( JInternalFrame b ) {
		super( b );
	}
	
	public static ComponentUI createUI( JComponent b ) {
		return new CustomInternalFrameUI( (JInternalFrame)b ); //THIS is the class entry point, via reflection from UIManager properties. MUST have this method overridden
	}
	
	protected JComponent createNorthPane( JInternalFrame w ) {
		titlePane = new CustomInternalFrameTitlePane( w );
		return titlePane;
	}
}