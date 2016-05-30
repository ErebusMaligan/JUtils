package icon;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 8:41:12 PM
 */
public class DefaultIconLoader {
	
	/**
	 * List of icons to load at creation
	 */
	protected String[] names;
	
	/**
	 * file extension - defaults to ".gif"
	 */
	protected String extension;
	
	/**
	 * Storage map of all icons available
	 */
	protected Map<String, Icon> icons;
	
	/**
	 * Creates this class and inits all icons into the map
	 */
	public DefaultIconLoader() {
		extension = ".gif";
		//In subclass constructor:
		//*************set names here (see DispatchIconLoader for how to)************
		//then call init();
	}
	
	/**
	 * initializes all icons, loads them into the map
	 */
	protected void init() {
		icons = new HashMap<String, Icon>();
		for ( String s : names ) {
			icons.put( s, new ImageIcon( getClass().getResource( s + extension ) ) );
		}
	}
	
	/**
	 * Get an icon by name
	 * 
	 * @param name name of the icon to load
	 * @return Icon with the name if it exists, otherwise null
	 */
	public Icon getIcon( String name ) {
		return icons.get( name );
	}
}