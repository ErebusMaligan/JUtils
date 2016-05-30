package gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Aug 28, 2013, 12:47:06 AM 
 */
public class GenericActionListener implements ActionListener {

	private GenericMenuBarAction action;
	
	private Object executor;
	
	public GenericActionListener( GenericMenuBarAction action, Object executor ) {
		this.action = action;
		this.executor = executor;
	}
	
	@Override
	public void actionPerformed( ActionEvent arg0 ) {
		action.execute( executor );
	}

}
