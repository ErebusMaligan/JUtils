package gui.menubar;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 17, 2013, 10:25:58 PM 
 */
public class GenericMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	protected JMenu menu;
	
	protected JMenuItem item;
	
	protected Map<String, JMenuItem> buttonMap;

	public GenericMenuBar() {
		buttonMap = new HashMap<String, JMenuItem>();
	}
	
	public JMenu setMenu( JMenu m ) {
		this.menu = m;
		return menu;
	}
	
	public void createBaseItem( GenericMenuBarAction action ) {
		item.addActionListener( new GenericActionListener( action, this ) );
		menu.add( item );
		buttonMap.put( item.getText(), item );
	}
	
	public void createItem( String name, GenericMenuBarAction action ) {
		item = new JMenuItem( name );
		createBaseItem( action );
	}
	
	public void createCheckItem( String name, GenericMenuBarAction action, boolean selected ) {
		item = new JCheckBoxMenuItem( name );
		item.setSelected( selected );
		createBaseItem( action );
	}
	
	public void pressButton( String text ) {
		JMenuItem item = buttonMap.get( text );
		if ( item != null ) {
			item.doClick();
		}
	}
}