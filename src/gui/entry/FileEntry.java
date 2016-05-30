package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import statics.GU;
import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:07:49 PM
 */
public class FileEntry extends Entry {
	
	private static final long serialVersionUID = 1L;
	
	private JButton b;

	public FileEntry( String labelText, PropsVariable var ) {
		this( labelText, var, null, false );
	}
	
	public FileEntry( String labelText, PropsVariable var, FileNameExtensionFilter filter ) {
		this( labelText, var, filter, false );
	}
	
	public FileEntry( final String labelText, final PropsVariable var, final FileNameExtensionFilter filter, final boolean save ) {
		super( labelText, var );
		GUIUtils.spacer( this );
		b = new JButton( "Browse..." );
		b.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				JFileChooser fc = new JFileChooser( var.toString() );
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
				if ( filter != null ) {
					fc.addChoosableFileFilter( filter );
					fc.setFileFilter( filter );
				}
				if ( !var.toString().equals( "" ) ) {
					fc.setSelectedFile( new File( var.toString() ) );
				}
				fc.setMultiSelectionEnabled( false );
				if ( save ) {
					if ( JFileChooser.APPROVE_OPTION == fc.showSaveDialog( b ) ) {
						String s = fc.getSelectedFile().getAbsolutePath();
						setText( s );
						var.fromString( s );
					}
				} else {
					if ( JFileChooser.APPROVE_OPTION == fc.showOpenDialog( b ) ) {
						String s = fc.getSelectedFile().getAbsolutePath();
						setText( s );
						var.fromString( s );
					}
				}
			}
		} );
		this.add( b );
	}
	
	public void setBrowseButtonDimension( Dimension d ) {
		GU.setSizes( b, d );
	}
}