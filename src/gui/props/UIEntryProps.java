package gui.props;

import gui.props.variable.BooleanVariable;
import gui.props.variable.FloatVariable;
import gui.props.variable.IntVariable;
import gui.props.variable.PropsVariable;
import gui.props.variable.StringVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 3:38:18 PM
 */
public class UIEntryProps {

	protected Map<String, PropsVariable> map = new HashMap<String, PropsVariable>();

	/**
	 * Copy all elements from the given props to this one - deep copy
	 * 
	 * @param p given UIEntryProps to copy from
	 */
	public void copy( UIEntryProps p ) {
		for ( String s : p.map.keySet() ) {
			PropsVariable v = p.map.get( s );
			if ( v != null ) {
				if ( map.get( s ) != null && map.get( s ).getClass().getName().equals( v.getClass().getName() ) ) {
					map.get( s ).fromString( v.toString() );
				} else {
					PropsVariable n = null;
					if ( v instanceof StringVariable ) {
						n = new StringVariable();
					} else if ( v instanceof IntVariable ) {
						n = new IntVariable();
					} else if ( v instanceof BooleanVariable ) {
						n = new BooleanVariable();
					} else if ( v instanceof FloatVariable ) {
						n = new FloatVariable();
					}
					n.fromString( v.toString() );
					map.put( s, n );	
				}
			}
		}
	}
	
	public void addVariable( String key, PropsVariable var ) {
		map.put( key, var );
	}
	
	public PropsVariable getVariable( String key ) {
		return map.get( key );
	}
	
	public String getString( String key ) {
		return map.get( key ).toString();
	}
	
	public List<String> getNames() {
		return new ArrayList<String>( map.keySet() );
	}
	
	public void removeVariable( String key ) {
		map.remove( key );
	}
}