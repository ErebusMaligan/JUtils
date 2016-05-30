package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:07:25 PM
 */
public class CheckEntry extends JPanel {

	private static final long serialVersionUID = 1L;

	public CheckEntry( String name, PropsVariable var ) {
		init( name, var, null );
	}
	
	public CheckEntry( String name, PropsVariable var, Dimension size ) {
		init( name, var, size );
	}
	
	private void init( String name, final PropsVariable var, Dimension size ) {
		final JCheckBox b = new JCheckBox( name, Boolean.parseBoolean( var.toString() ) );
		if ( size != null ) {
			GUIUtils.setSizes( b, size );
		}
		b.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				var.fromString( "" + b.isSelected() );
			}
		} );
		this.add( b );
	}
}