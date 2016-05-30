package gui.table.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Jan 31, 2013, 3:44:53 PM
 */
public class CustomizableTableSorter extends BasicTableSorter {
	
	/**
	 * Map of values to rows
	 */
	protected Map<String, Vector<Object[]> > map = new HashMap<String, Vector<Object[]>>();
	
	/**
	 * Vector that is a "bin" of data rows that have the same value in a given column
	 */
	protected Vector<Object[]> vec;
	
	
	protected List<String> keys;
	
	
	/**
	 * This gets the "key" for binning this data row.  Generally it's the string value of the column, but for specialized behavior you might overwrite to map it to some other string
	 * 
	 * @param row Single row of the table
	 * @param modelIndex column index of the table model
	 * @return Key to catalogue this data with
	 */
	protected String getIndex( Object[] row, int modelIndex ) {
		return row[ modelIndex] == null ? (String)null : row[ modelIndex ].toString();
	}
	
	/**
	 * Handles actually sorting the values provided - override to use a custom comparator
	 */
	protected void specificSort() {
		ArrayList<Integer> intKeys = null;
		try {
			intKeys = new ArrayList<Integer>();
			for ( String s : keys ) {
				intKeys.add( Integer.parseInt( s ) );
			}
		} catch (NumberFormatException e ) {
			intKeys = null;
		}
		if ( intKeys != null ) {
			Collections.sort( intKeys );
			keys = new ArrayList<String>();
			for ( Integer i : intKeys ) {
				keys.add( String.valueOf( i ) );
			}
		} else {
			Collections.sort( keys );
		}
	}
	
	
	/**
	 * Sorts columns
	 */
	public Object[][] sort( int size, int columns, Object[][] data ) {
		initContainers();
		binData( size, data );
		//Sort the keys - this is a default string sort - if you have numbers or something else you would need to modify this part  - MAY CHANGE
		specificSort();
		handleOther();
		return finalizeSort( size, columns, keys );
	}
	
	/**
	 * Initializes the various data containers that need to be cleared out for each new sort - just always do this
	 */
	protected void initContainers() {
		map.clear();
		vec = null;
	}
	
	/**
	 * Handles some additional cases that need to be dealt with - always do this unless you don't want null handling and reversing behavior
	 */
	protected void handleOther() {
		if ( vec != null ) {
			keys.add( 0, null );
		}
		if ( reverse ) { //handles reversing the sort if the header was clicked on twice
			Collections.reverse( keys );
		}
	}
	
	/**
	 * Take all data and "bin" it by the data value in the column being sorted by
	 * By default, it bins the rows by the string value of the given column - this can be altered by overriding the getIndex method to return some other string of your choice
	 * 
	 * @param size Number of rows
	 * @param data All data in the table
	 */
	protected void binData( int size, Object[][] data ) {
		//bin the values in the given column by putting them in vectors together if they - ALWAYS DO THIS - but see inner comment
		for ( int i = 0; i < size; i++ ) {
			Object[] row = data[ i ];
			String idx = getIndex( row, modelIndex );//this line may need changing depending on context, but should work for anything i can think of - MAY CHANGE
			if ( map.get( idx ) == null ) {
				map.put( idx, new Vector<Object[]>() );
			}
			vec = map.get( idx );
			vec.add( row );
			map.put( idx, vec );
		}
		vec = map.get( null );
		map.remove( null );
		keys = new ArrayList<String>( map.keySet() );
	}
	
	/**
	 * Finishes the sort by sorting the underlying data to match the way the particular column was sorted -
	 * My best explanation is: All the code before this sorted only the values for the specific column.  This goes and sorts the table rows to match that.
	 * 
	 * @param size Number of Rows
	 * @param columns Number of columns
	 * @param keys List of sorted keys for the selected column
	 * @return Sorted Table Data
	 */
	protected Object[][] finalizeSort( int size, int columns, List<String> keys ) {
		Object[][] out = new Object[ size ][ columns ];
		int index = -1;
		for ( int i = 0; i < keys.size(); i++ ) {
			Vector<Object[]> rows = vec;
			if ( keys.get( i ) != null ) {
				rows = map.get( keys.get( i ) );
			}
			for ( int j = 0; j < rows.size(); j++ ) {
				out[ ++index ] = rows.get( j );
			}
		}
		return out;
	}
}