package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class Entry extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JTextField text;
	
	protected PropsVariable var;
	
	protected JLabel lbl;
	
	public Entry( String labelText, PropsVariable var ) {
		init( labelText, var, null, null, null );
	}
	
	public Entry( String labelText, PropsVariable var, Dimension trailing ) {
		init( labelText, var, trailing, null, null );
	}
	
	public Entry( String labelText, PropsVariable var, Dimension trailing, Dimension leading ) {
		init( labelText, var, trailing, leading, null );
	}
	
	public Entry( String labelText, PropsVariable var, Dimension[] dim ) {
		init( labelText, var, null, null, dim );
	}
	
	public Entry( String labelText, PropsVariable var, Dimension trailing, Dimension[] dim ) {
		init( labelText, var, trailing, null, dim );
	}
	
	public Entry( String labelText, PropsVariable var, Dimension trailing, Dimension leading, Dimension[] dim ) {
		init( labelText, var, trailing, leading, dim );
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
		text = createText( var );
		text.addKeyListener( new KeyAdapter() {
			public void keyReleased( KeyEvent e ) {
				if ( !var.toString().equals( text.getText() ) ) {
					var.fromString( text.getText() );
				}
			}
		} );
		text.addFocusListener( new FocusListener() {
			public void focusLost( FocusEvent e ) {
				text.select( 0, 0 );
			}
			public void focusGained( FocusEvent e ) {
				text.select( 0, text.getText().length() );
			}
		} );
		GUIUtils.setSizes( text, dim == null ? GUIUtils.FIELD : dim[ 1 ] );
		this.add( text );
		if ( trailing != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, trailing );
		}
	}
	
	protected JTextField createText( PropsVariable var ) {
		return new JTextField( var.toString() );
	}
	
	public void setText( String s ) {
		text.setText( s );
	}
	
	public void setTextSize( Dimension d ) {
		GU.setSizes( text, d );
	}
	
	public void setFonts( Font f ) {
		text.setFont( f );
		lbl.setFont( f );
	}
	
	public void setLabelSize( Dimension d ) {
		GU.setSizes( lbl, d );
	}
	
	public void reload() {
		text.setText( var.toString() );
	}
	
	public PropsVariable getVar() {
		return var;
	}
}