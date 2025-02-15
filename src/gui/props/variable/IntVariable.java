package gui.props.variable;

/**
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 3:30:30 PM
 */
public class IntVariable extends PropsVariable {

	public IntVariable() {
		var = Integer.valueOf( 0 );
	}
	
	public IntVariable( int var ) {
		this.var = Integer.valueOf( var );
	}
	
	@Override
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}

	@Override
	public String toString() {
		return String.valueOf( var );
	}

	@Override
	public void fromStringViaListener( String var ) {
		try {
			this.var = Integer.parseInt( var );
		} catch ( NumberFormatException e ) {
			this.var = 0;
		}
	}
}