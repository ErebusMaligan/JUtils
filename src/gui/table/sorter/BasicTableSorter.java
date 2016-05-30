package gui.table.sorter;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * Class to be extended for specific sorts.  This class has a method which should be added to a table header listener, to handle sorted vs reversed ordering.
 * 
 * @author Daniel J. Rivers
 *         2012
 *
 * Created: Jan 10, 2012, 10:51:09 AM 
 */
public abstract class BasicTableSorter implements SimpleTableSorter {
	
	/**
	 * Column being sorted on (model location, not UI location)
	 */
	protected int modelIndex = 0;
	
	/**
	 * previously selected index (for determining if sort should reverse order)
	 */
	protected int previousIndex = -1;
	
	/**
	 * Whether during the next sort
	 */
	protected boolean reverse = false;
	
	@Override
	public void processHeaderSelection( JTable table, MouseEvent e ) {
		TableColumnModel model = table.getColumnModel();
		int index = model.getColumnIndexAtX(e.getX());
		if ( index != -1 ) {
			previousIndex = modelIndex;
			modelIndex = table.convertColumnIndexToModel(index);
		}
		if ( previousIndex == modelIndex && !reverse ) {
			reverse = true;
		} else {
			reverse = false;
		}
	}
	
	@Override
	public abstract Object[][] sort( int size, int columns, Object[][] data );
	
	public void setModelIndex( int index ) {
		modelIndex = index;
	}
	
	public int getModelIndex() {
		return modelIndex;
	}
	
	public boolean isReversed() {
		return reverse;
	}
}