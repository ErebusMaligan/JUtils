package gui.entry;

import gui.props.variable.PropsVariable;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:06:10 PM
 */
public class PasswordEntry extends Entry {

	private static final long serialVersionUID = 1L;
	
	protected JTextField text;
	
	protected PropsVariable var;
	
	protected JLabel lbl;
	
	public PasswordEntry( String labelText, PropsVariable var ) {
		super( labelText, var );
	}
	
	public PasswordEntry( String labelText, PropsVariable var, Dimension trailing ) {
		super( labelText, var, trailing );
	}
	
	public PasswordEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading ) {
		super( labelText, var, trailing, leading );
	}
	
	public PasswordEntry( String labelText, PropsVariable var, Dimension[] dim ) {
		super( labelText, var, dim );
	}
	
	public PasswordEntry( String labelText, PropsVariable var, Dimension trailing, Dimension[] dim ) {
		super( labelText, var, trailing, dim );
	}
	
	public PasswordEntry( String labelText, PropsVariable var, Dimension trailing, Dimension leading, Dimension[] dim ) {
		super( labelText, var, trailing, leading, dim );
	}
	
	@Override
	protected JTextField createText( PropsVariable var ) {
		return new JPasswordField( var.toString() );
	}
}