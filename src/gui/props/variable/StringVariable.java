package gui.props.variable;

/**
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 3:33:14 PM
 */
public class StringVariable extends PropsVariable {
	
	public StringVariable() {
		var = new String();
	}
	
	public StringVariable( String var ) {
		this.var = var;
	}
	
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}
	
	public String toString() {
		return (String)var;
	}

	@Override
	public void fromStringViaListener( String var ) {
		this.var = var;
	}
}