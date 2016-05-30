package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import statics.GU;
import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:05:02 PM
 */
public class DirectoryEntry extends Entry {

	private static final long serialVersionUID = 1L;
	
	public DirectoryEntry( String labelText, final PropsVariable var ) {
		super( labelText, var );
		GUIUtils.spacer( this );
		final JButton b = new JButton( "Browse..." );
		GUIUtils.setSizes( b, GU.SHORT );
		b.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				JFileChooser fc = new JFileChooser( var.toString() );
				fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
				fc.setMultiSelectionEnabled( false );
				if ( !var.toString().equals( "" ) ) {
					fc.setCurrentDirectory( new File( var.toString() ) );
//					fc.setSelectedFile( new File( var.toString() ) );
				}
				if ( JFileChooser.APPROVE_OPTION == fc.showOpenDialog( b ) ) {
					String s = fc.getSelectedFile().getPath();
					setText( s + "/" );
					var.fromString( s + "/" );
				}
			}
		} );
		this.add( b );
	}
}