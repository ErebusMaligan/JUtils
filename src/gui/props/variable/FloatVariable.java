package gui.props.variable;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: May 5, 2014, 2:57:37 AM 
 */
public class FloatVariable extends PropsVariable {

	public FloatVariable() {
		this( 0f );
	}
	
	public FloatVariable( float var ) {
		this.var = new Float( var );
	}
	
	@Override
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}
	
	@Override
	public void fromStringViaListener( String var ) {
		try {
			this.var = Float.parseFloat( var );
		} catch ( NumberFormatException e ) {
			this.var = 0f;
		}
	}

	@Override
	public String toString() {
		return String.valueOf( (Float)var );
	}
}