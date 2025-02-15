package gui.props.variable;

/**
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 3:27:24 PM
 */
public class BooleanVariable extends PropsVariable {

	public BooleanVariable() {
		var = Boolean.valueOf( true );
	}
	
	public BooleanVariable( boolean v ) {
		this.var = Boolean.valueOf( v );
	}
	
	@Override
	public void fromString( String v ) {
		fromStringViaListener( v );
		super.fromString( v );
	}

	@Override
	public String toString() {
		return String.valueOf( var );
	}

	@Override
	public void fromStringViaListener( String v ) {
		this.var = Boolean.parseBoolean( v );
	}

}
