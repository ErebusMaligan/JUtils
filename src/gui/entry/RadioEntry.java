package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 20, 2014, 6:47:39 PM 
 */
public class RadioEntry extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected JRadioButton radio;
	
	protected PropsVariable var;
	
	public RadioEntry( String labelText, PropsVariable var ) {
		init( labelText, var, null, null, null );
	}
	
	public RadioEntry( String labelText, PropsVariable var, Dimension dim ) {
		init( labelText, var, null, null, dim );
	}
	
	public RadioEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading ) {
		init( labelText, var, trailing, leading, null );
	}
	
	public RadioEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading, Dimension dim ) {
		init( labelText, var, trailing, leading, dim );
	}
	
	protected void init( String labelText, final PropsVariable var, Dimension trailing, Dimension leading, Dimension dim ) {
		this.var = var;
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		if ( leading != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, leading );
		}
		radio = new JRadioButton( labelText, false );
		GUIUtils.setSizes( radio, dim == null ? GUIUtils.FIELD : dim );
		this.add( radio );
		radio.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				var.fromString( "" + radio.isSelected() );
			}
		} );
		if ( trailing != null ) {
			GUIUtils.spacer( this );
			GUIUtils.spacer( this, trailing );
		}
	}

	public void setText( String s ) {
		radio.setText( s );
	}
	
	public void reload() {
		radio.setSelected( (Boolean)var.getValue() );
	}
	
	public PropsVariable getVar() {
		return var;
	}	
}