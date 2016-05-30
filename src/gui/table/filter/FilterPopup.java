package gui.table.filter;

import gui.dialog.OKCancelDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import statics.GU;

/**
 * Displays the dialog for typing in a filter string
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Feb 11, 2013, 10:37:56 AM 
 */
public class FilterPopup extends OKCancelDialog implements FilterDialog {

	private static final long serialVersionUID = 1L;
	
	private JTextField text = new JTextField();
	
	private String value;
	
	public FilterPopup( Frame owner ) {
		super( owner, "Filter on text:", true );
		this.setSize( new Dimension( 250, 100 ) );
		this.setLayout( new BorderLayout() );
		this.add( text, BorderLayout.CENTER );
		text.addKeyListener( new KeyAdapter() {
			public void keyPressed( KeyEvent e ) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
					ok();
				}
			}
		} );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		GU.sizes( ok, GU.SHORT );
		GU.sizes( cancel, GU.SHORT );
	}
	
	public void ok() {
		value = text.getText();
		super.ok();
	}
	
	public void cancel() {
		value = null;
		super.cancel();
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void display( Point location ) {
		if ( location != null ) {
			this.setLocation( location );
		}
		this.setVisible( true );
	}
}