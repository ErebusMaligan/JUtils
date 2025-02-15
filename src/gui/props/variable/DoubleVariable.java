package gui.props.variable;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: May 5, 2014, 2:57:37 AM 
 */
public class DoubleVariable extends PropsVariable {

	public DoubleVariable() {
		this( 0d );
	}
	
	public DoubleVariable( double v ) {
		this.var = Double.valueOf( v );
	}
	
	@Override
	public void fromString( String v ) {
		fromStringViaListener( v );
		super.fromString( v );
	}
	
	@Override
	public void fromStringViaListener( String v ) {
		try {
			this.var = Double.parseDouble( v );
		} catch ( NumberFormatException e ) {
			this.var = 0f;
		}
	}

	@Override
	public String toString() {
		return String.valueOf( (double)var );
	}
}