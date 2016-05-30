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
	
	public DoubleVariable( double var ) {
		this.var = new Double( var );
	}
	
	@Override
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}
	
	@Override
	public void fromStringViaListener( String var ) {
		try {
			this.var = Double.parseDouble( var );
		} catch ( NumberFormatException e ) {
			this.var = 0f;
		}
	}

	@Override
	public String toString() {
		return String.valueOf( (double)var );
	}
}