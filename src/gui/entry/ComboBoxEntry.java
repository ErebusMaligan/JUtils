package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import statics.GU;
import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:06:10 PM
 */
public class ComboBoxEntry extends Entry {

	private static final long serialVersionUID = 1L;
	
	protected JComboBox<String> text;
	
	protected PropsVariable var;
	
	protected JLabel lbl;
	
	public ComboBoxEntry( String labelText, PropsVariable var ) {
		super( labelText, var );
	}
	
	public ComboBoxEntry( String labelText, PropsVariable var, Dimension trailing ) {
		super( labelText, var, trailing );
	}
	
	public ComboBoxEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading ) {
		super( labelText, var, trailing, leading );
	}
	
	public ComboBoxEntry( String labelText, PropsVariable var, Dimension[] dim ) {
		super( labelText, var, dim );
	}
	
	public ComboBoxEntry( String labelText, PropsVariable var, Dimension trailing, Dimension[] dim ) {
		super( labelText, var, trailing, dim );
	}
	
	public ComboBoxEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading, Dimension[] dim ) {
		super( labelText, var, trailing, leading, dim );
	}
	
	protected void init( String labelText, final PropsVariable var, Dimension trailing, Dimension leading, Dimension[] dim ) {
		this.var = var;
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		if ( leading != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, leading );
		}
		lbl = new JLabel( labelText );
		GUIUtils.setSizes( lbl, dim == null ? GUIUtils.FIELD : dim[ 0 ] );
		lbl.setHorizontalAlignment( SwingConstants.RIGHT );
		this.add( lbl );
		GUIUtils.spacer( this );
		text = new JComboBox<String>();
		text.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				String item = text.getItemAt( text.getSelectedIndex() );
				if ( item != null ) {
					if ( !var.toString().equals( item ) ) {
						var.fromString( text.getItemAt( text.getSelectedIndex() ) );
					}
				}
			}
		} );
		GUIUtils.setSizes( text, dim == null ? GUIUtils.FIELD : dim[ 1 ] );
		this.add( text );
		if ( trailing != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, trailing );
		}
	}
	
	public void setContents( List<String> contents ) {
		text.removeAllItems();
		for ( String s : contents ) {
			text.addItem( s );
		}
	}
	
	public void addContent( String s ) {
		text.addItem( s );
	}
	
	protected JTextField createText( PropsVariable var ) {
		return new JTextField( var.toString() );
	}
	
	public void setText( String s ) {
		text.setSelectedItem( s );
	}
	
	public void setIndex( int i ) {
		text.setSelectedIndex( i );
	}
	
	public void setTextSize( Dimension d ) {
		GU.setSizes( text, d );
	}
	
	public void setLabelSize( Dimension d ) {
		GU.setSizes( lbl, d );
	}
	
	public void reload() {
		text.setSelectedItem( var.toString() );
	}
}