package gui.table.sorter;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

/**
 * @author Daniel J. Rivers
 *         2012
 *
 * Created: Jan 4, 2012, 12:00:49 PM 
 */
public interface SimpleTableSorter {
	public Object[][] sort( int size, int columns, Object[][] data );
	
	public void processHeaderSelection( JTable table, MouseEvent e );
	
	public void setModelIndex( int index );
}