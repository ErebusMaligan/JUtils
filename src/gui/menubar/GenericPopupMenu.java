package gui.menubar;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: May 21, 2014, 3:37:50 AM 
 */
public class GenericPopupMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 1L;

	protected JMenuItem item;

	protected JComponent menu = this;
	
	public JComponent setMenu( JComponent m ) {
		this.menu = m;
		return menu;
	}
	
	public void createBaseItem( GenericMenuBarAction action ) {
		item.addActionListener( new GenericActionListener( action, this ) );
		menu.add( item );		
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
}