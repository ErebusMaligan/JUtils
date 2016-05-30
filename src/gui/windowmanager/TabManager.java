package gui.windowmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:54:31 AM 
 */
public class TabManager implements XMLValues {
	
	protected JTabbedPane tabPane = new JTabbedPane();
	
	protected List<WindowManager> tabs = new ArrayList<>();
	
	protected Object state;
	
	protected List<XMLExpansion> prev = new ArrayList<>();

	protected Map<JDesktopPane, WindowManager> index = new HashMap<>();
	
	protected ComponentWindowFactory factory;
	
	public TabManager( Object state, ComponentWindowFactory factory ) {
		this.state = state;
		this.factory = factory;
		additionalConfiguration( state );
	}
	
	public void additionalConfiguration( Object state ) {}
	
	public WindowManager createWindowManager( String name ) {
		return new WindowManager( name, state, factory );
	}
	
	public WindowManager addTab( String name ) {
		WindowManager w = createWindowManager( name );
		tabs.add( w );
		tabPane.add( name, w.getDesktop() );
		index.put( w.getDesktop(), w );
		return w;
	}
	
	public void removeSelectedTab() {
		WindowManager wm = getSelectedWindowManager();
		List<ComponentWindow> win = new ArrayList<>();
		getSelectedWindowManager().getWindows().forEach( w -> {
			win.add( w );
		} );
		win.forEach( w -> w.doDefaultCloseAction() );
		index.remove( wm.getDesktop() );
		tabs.remove( wm );
		tabPane.remove( tabPane.getSelectedComponent() );
	}

	public void loadPreviousConfig() {
		prev.forEach( c -> {
			WindowManager w = addTab( c.get( WindowManagerConstants.XWINTITLE ) );
			w.loadParamsFromXMLValues( c );
		} );
	}
	
	public WindowManager getSelectedWindowManager() {
		return index.get( tabPane.getSelectedComponent() );
	}
	
	public void instantiateWindow( String title, WindowParameters p ) {
		getSelectedWindowManager().instantiateWindow( title, p );
	}
	
	public void removeWindow( ComponentWindow w ) {
		getSelectedWindowManager().removeWindow( w );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XMLValues> getChildNodes() {
		return (List<XMLValues>)(List<?>)tabs;
	}
	
	public List<ComponentWindow> getAllWindows() {
		List<ComponentWindow> ret = new ArrayList<>();
		tabs.forEach( t -> ret.addAll( t.getWindows() ) );
		return ret;
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		if ( e.getChild( WindowManagerConstants.XWINDOWS ) != null ) {
			e.getChildren( WindowManagerConstants.XWINDOWS ).forEach( c -> prev.add( c ) );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<>();
		ret.put( WindowManagerConstants.XTABS, null );
		return ret;
	}
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
}