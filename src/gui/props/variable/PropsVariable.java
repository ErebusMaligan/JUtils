package gui.props.variable;

/**
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 3:30:23 PM
 */
public abstract class PropsVariable {
	
	protected PropsVariableListener listener;
	
	protected Object var;
	
	public void fromString( String var ) {
		if ( listener != null ) {
			listener.valueChanged( this );
		}
	}
	
	public abstract void fromStringViaListener( String var );
	
	public void setListener( PropsVariableListener listener ) {
		this.listener = listener;
	}
	
	public abstract String toString();
	
	public Object getValue() {
		return var;
	}
}