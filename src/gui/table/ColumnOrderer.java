package gui.table;

import java.util.Vector;

import javax.swing.JTable;

/**
 * @author Daniel J. Rivers
 *         2012
 *
 * Created: Jan 5, 2012, 3:24:03 PM 
 */
public class ColumnOrderer { 
	
	//In theory this will break completely if a table has two columns with the same name holding different data
	
	/**
	 * Stores the locations of the columns, allowing them to be moved
	 */
	private Vector<String> columnLocations = null;
	
	/**
	 * JTable this class is being utilized for
	 */
	private JTable table;
	
	/**
	 * Creates a new orderer for the given table
	 * 
	 * @param table table this class is being utilized for
	 */
	public ColumnOrderer( JTable table ) {
		this.table = table;
		columnLocations = new Vector<String>();
	}
	
	/**
	 * Return the column location list
	 * 
	 * @return vector of column names in the order in which they are currently ordered
	 */
	public Vector<String> getColumnLocations() {
		return columnLocations;
	}
	
	/**
	 * Set the column location order
	 * 
	 * @param columnLocations vector of column names that sets the order the columns are to be sorted in
	 */
	public void setColumnLocations( Vector<String> columnLocations ) {
		this.columnLocations = columnLocations;
	}
	
	/**
	 * Stores current location of columns for recall later.  
	 */
	public void storeColumnLocations() {
		columnLocations.removeAllElements();
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnLocations.add(table.getColumnName(i));
		}
	}
	
	/**
	 * Set the columns to be in the locations they were last saved as
	 */
	public void swapColumns() {
		boolean moved = false;
		for(int i = 0; i < columnLocations.size() && !moved; i++) {
            if(!(table.getColumnName(i).equals(columnLocations.get(i)))) {
                int properIndex = columnLocations.indexOf(table.getColumnName(i));
                if ( properIndex != -1 ) {
            		table.moveColumn(i, properIndex);
            		moved = true;
                }
            }
        }
		if ( moved ) {
			swapColumns();
		}
	}
}