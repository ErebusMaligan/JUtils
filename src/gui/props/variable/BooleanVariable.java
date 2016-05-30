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
		var = new Boolean( true );
	}
	
	public BooleanVariable( boolean var ) {
		this.var = new Boolean( var );
	}
	
	@Override
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}

	@Override
	public String toString() {
		return String.valueOf( (Boolean)var );
	}

	@Override
	public void fromStringViaListener( String var ) {
		this.var = Boolean.parseBoolean( var );
	}

}
