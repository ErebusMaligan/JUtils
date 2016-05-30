package gui.windowmanager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 2:50:44 AM 
 */
@FunctionalInterface
public interface ComponentWindowFactory {
	public ComponentWindow createNewComponentWindow( Object state, WindowManager wm, WindowDefinition d );
}