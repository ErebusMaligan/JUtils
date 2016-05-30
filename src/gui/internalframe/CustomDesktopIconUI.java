package gui.internalframe;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 10, 2015, 4:05:56 AM 
 */
public class CustomDesktopIconUI extends BasicDesktopIconUI {

    public static ComponentUI createUI( JComponent c) {  //THIS is the class entry point, via reflection from UIManager properties. MUST have this method overridden
        return new CustomDesktopIconUI();
    }
    
	protected void installComponents() {
		iconPane = new CustomInternalFrameTitlePane( frame );
		desktopIcon.setLayout( new BorderLayout() );
		desktopIcon.add( iconPane, BorderLayout.CENTER );
	}
}