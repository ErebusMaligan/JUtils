package gui.table.filter;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Feb 8, 2013, 1:27:16 PM 
 */
public class FilterManager {
	
	protected Map<Integer, String> filters = new HashMap<Integer, String>();
	
	protected FilterDialog fd;
	
	public FilterManager( FilterDialog fd ) {
		this.fd = fd;
	}
	
	public FilterManager( boolean touchScreen ) {
		fd = touchScreen ? new TouchScreenFilterPopup() : new FilterPopup( null );
	}
	
	public void addFilter( int modelIndex, String acceptable ) {
		filters.put( modelIndex, acceptable );
	}
	
	public void removeAllFilters() {
		filters.clear();
	}
	
	public void removeFilter( int modelIndex ) {
		filters.remove( modelIndex );
	}
	
	/**
	 * Handles all the listener and filter add/remove code from clicking on the table header
	 * 
	 * @param table Table this FilterManager is working on
	 * @param e Event from mouseListener
	 */
	public void processHeaderSelection( JTable table, MouseEvent e ) {
		TableColumnModel model = table.getColumnModel();
		int index = model.getColumnIndexAtX(e.getX());
		int modelIndex = -1;
		if ( index != -1 ) {
			modelIndex = table.convertColumnIndexToModel(index);
		}
		if ( modelIndex != -1 ) {
			if ( !filters.containsKey( modelIndex ) ) {
				fd.display( e.getLocationOnScreen() );
				if ( fd.getValue() != null ) {
					filters.put( modelIndex, fd.getValue() );
				}
			} else {
				removeFilter( modelIndex );
			}
		}
	}
	
	/**
	 * Determines if the value in the given row, for the column being examined passes the search filter
	 * 
	 * @param row table data row
	 * @return true if this value should be displayed, false if it should be filtered out
	 */
	public boolean acceptable( Object[] row ) {
		boolean ret = true;
		for ( Integer i : filters.keySet() ) {
			String desired = filters.get( i );
			if ( desired.startsWith( "\"" ) && desired.endsWith( "\"" ) ) {
				if ( !getIndex( row, i ).toUpperCase().equals( desired.substring( 1, desired.length() - 1 ) ) ) {
					ret = false;
					break;
				}
			} else {
				if ( !getIndex( row, i ).toUpperCase().contains( desired.toUpperCase() ) ) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	protected String getIndex( Object[] row, int modelIndex ) {
		return row[ modelIndex] == null ? (String)null : row[ modelIndex ].toString();
	}
	
	public boolean hasFilter( int modelIndex ) {
		return filters.containsKey( modelIndex );
	}
}