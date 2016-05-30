package gui.windowmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDesktopPane;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 8:16:05 PM 
 */
public class WindowManager implements XMLValues {
	
	private Object state;
	
	private String name;
	
	private JDesktopPane desktop;
	
	private static Map<String, WindowParameters> defaults = new HashMap<>();
	
	private static Map<String, WindowDefinition> definitions = new HashMap<>();
	
	private List<ComponentWindow> windows = new ArrayList<>();
	
	private ComponentWindowFactory factory;
	
	public WindowManager( String name, Object state, ComponentWindowFactory factory ) {
		this.state = state;
		this.name = name;
		this.factory = factory;
		desktop = createDesktopPane();
	}
	
	public JDesktopPane createDesktopPane() {
		return new JDesktopPane();
	}

	public static void addWindowDefaults( String title, WindowParameters p ) {
		defaults.put( title, p );
	}
	
	public static void addWindowDefinition( WindowDefinition d ) {
		definitions.put( d.getTitle(), d );
	}
	
	public static void addWindowDefinitionAndDefaults( WindowDefinition d, WindowParameters p ) {
		addWindowDefinition( d );
		addWindowDefaults( d.getTitle(), p );
	}
	
	public void instantiateWindow( String title, WindowParameters p ) {
		WindowDefinition d = definitions.get( title );
		if ( d != null ) {
			ComponentWindow c = factory.createNewComponentWindow( state, this, d );
			windows.add( c );
			desktop.add( c );
			c.setVisible( true );
			if ( p != null ) {
				c.setParameters( p );
			} else {
				p = defaults.get( title );
				if ( p != null ) {
					c.setParameters( p );
				}
			}
		}
	}
	
	public void removeWindow( ComponentWindow w ) {
		windows.remove( w );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLValues> getChildNodes() {
		return (List<XMLValues>)(List<?>)windows;
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		if ( e.getChild( WindowManagerConstants.XWINDOW ) != null ) {
			e.getChildren( WindowManagerConstants.XWINDOW ).forEach( c -> {
				WindowParameters p = new WindowParameters();
				p.loadParamsFromXMLValues( c );
				instantiateWindow( c.get( WindowManagerConstants.XWINTITLE ), p );
			} );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<>();
		Map<String, String[]> values = new HashMap<>();
		values.put( WindowManagerConstants.XWINTITLE, new String[] { name } );
		ret.put( WindowManagerConstants.XWINDOWS, values );
		return ret;
	}
	
	public JDesktopPane getDesktop() {
		return desktop;
	}
	
	public List<ComponentWindow> getWindows() {
		return windows;
	}
}