package gui.table.filter;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Mar 4, 2013, 4:32:53 PM 
 */
public class ExcludeFilterManager extends FilterManager {
	
	protected int[] indices;
	
	public ExcludeFilterManager( int[] indices, boolean touchScreen ) {
		super( touchScreen );
		this.indices = indices;
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
		boolean exclude = false;
		for ( int i : indices ) {
			if ( modelIndex == i ) {
				exclude = true;
				break;
			}
		}
		if ( modelIndex != -1 && !exclude ) {
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
	
}
