package gui.props.variable;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jun 25, 2014, 10:28:42 PM 
 */
public class LongVariable extends PropsVariable {

	public LongVariable() {
		var = new Long( 0 );
	}
	
	public LongVariable( long var ) {
		this.var = new Long( var );
	}
	
	@Override
	public void fromString( String var ) {
		fromStringViaListener( var );
		super.fromString( var );
	}
	
	@Override
	public void fromStringViaListener( String var ) {
		try {
			this.var = Long.parseLong( var );
		} catch ( NumberFormatException e ) {
			this.var = 0l;
		}
	}

	@Override
	public String toString() {
		return String.valueOf( (Long)var );
	}
}