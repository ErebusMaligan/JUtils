package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import statics.GU;
import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:06:10 PM
 */
public class LabelEntry extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JLabel text;
	
	protected PropsVariable var;
	
	protected JLabel lbl;
	
	public LabelEntry( String labelText, PropsVariable var ) {
		init( labelText, var, null, null, null );
	}
	
	public LabelEntry( String labelText, PropsVariable var, Dimension trailing ) {
		init( labelText, var, trailing, null, null );
	}
	
	public LabelEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading ) {
		init( labelText, var, trailing, leading, null );
	}
	
	public LabelEntry( String labelText, PropsVariable var, Dimension[] dim ) {
		init( labelText, var, null, null, dim );
	}
	
	public LabelEntry( String labelText, PropsVariable var, Dimension trailing, Dimension[] dim ) {
		init( labelText, var, trailing, null, dim );
	}
	
	public LabelEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading, Dimension[] dim ) {
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
		GUIUtils.setSizes( text, dim == null ? GUIUtils.FIELD : dim[ 1 ] );
		this.add( text );
		if ( trailing != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, trailing );
		}
	}
	
	protected JLabel createText( PropsVariable var ) {
		return new JLabel( var.toString() );
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