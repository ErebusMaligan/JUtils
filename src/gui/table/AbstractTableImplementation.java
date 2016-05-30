package gui.table;

import gui.table.filter.FilterManager;
import gui.table.renderer.DefaultHeaderCellRenderer;
import gui.table.sorter.CustomizableTableSorter;
import gui.table.sorter.SimpleTableSorter;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import thread.RefreshBuffer;

/**
 * This class handles almost all the setup of a JTable that is sortable and can be rearranged, and reloads when data changes if it is attached as a listener to a data source.
 *
 * Only the abstract methods should need to be filled in by the subclass, and most of those should be one line at most.
 * 
 * Subclasses may want to override the SimpleTableSorter with a custom one if they have more complex behavior than string sorting.
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Jan 31, 2013, 4:42:16 PM 
 */

public abstract class AbstractTableImplementation {

	protected List<String> selected = new ArrayList<>();
	
	protected Vector<Object> data;
	
	protected Object[][] sortedFilteredData;
	
	protected JTable table = new JTable() {
		private static final long serialVersionUID = 1L;
		public Component prepareRenderer( TableCellRenderer renderer, int row, int column ) {
			return AbstractTableImplementation.this.adjustRenderer( super.prepareRenderer( renderer, row, column ), row, column );  // i do not remember why this was done... think it has something to do with colorizing the header
		}
	};
	
	protected ColumnOrderer order = new ColumnOrderer( table );
	
	protected SimpleTableSorter sorter = new CustomizableTableSorter();
	
	protected volatile boolean dragging = false;

	protected RefreshBuffer buffer = new RefreshBuffer();
	
	/**
	 * Pull all data being displayed and add it to data vector
	 */
	protected abstract void pullSpecificData();
	
	/**
	 * Add this as a listener to appropriate CacheEntities
	 */
	protected abstract void addAsListener();
	
	/**
	 * Remove this is a listener from appropriate CacheEntities
	 */
	public void shutdown() {
		buffer.stop();
	}
	
	/**
	 * Return a list of Column names - usually String[] not really Object[]...
	 * 
	 * @return array of column names
	 */
	protected abstract Object[] getColumnNames();
	
	/**
	 * For each element of the data vector, convert it to a displayable row in the JTable
	 * 
	 * @param i index of the element in the data vector
	 * @return Object[] representing a row in the JTable
	 */
	protected abstract Object[] convertRow( int i ); 

	protected TableCellRenderer headerRenderer = new DefaultHeaderCellRenderer( this );
	
	protected MouseAdapter headerListener;
	
	protected FilterManager filterManager = new FilterManager( false );
	
	public void init( final boolean allowFiltering ) {
		buffer.start();
		addAsListener();
		headerListener = new MouseAdapter() {
			public void mousePressed( MouseEvent e ) {
				dragging = true;
			}
			
			public void mouseReleased( MouseEvent e ) {
				dragging = false;
			}
			
			public void mouseClicked( MouseEvent e ) {
				dragging = false;
				if ( e.getButton() == MouseEvent.BUTTON1 ) {
					sorter.processHeaderSelection( table, e );	
				}
				if ( allowFiltering ) {
    				if ( e.getButton() == MouseEvent.BUTTON3 ) {
    					filterManager.processHeaderSelection( table, e );
    				}
				}
				pullData();
				
			}
		};
		table.getTableHeader().addMouseListener( headerListener );
		pullData();
	}
	
	public void setHeaderRenderer( TableCellRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
	}

	public Component adjustRenderer( Component c, int row, int column ) {
		return c;
	}
	
	public synchronized void pullData() {
		try {
			buffer.enqueue( new Thread( () ->  {
				storeSelected();
				pullSpecificData();
				reconstruct();
//				if ( !SwingUtilities.isEventDispatchThread() ) {
//					SwingUtilities.invokeLater( () -> restoreSelected() );
//				} else {
					restoreSelected();
//				}
			} ) );
		} catch ( InterruptedException e ) {
			buffer.start();
		}
	}
	
	private void storeSelected() {
//		System.out.println( "\n---------------------" );
//		System.out.println( "STORING\n" );
		selected.clear();
		int[] indicies = table.getSelectedRows();
		for ( int i = 0; i < indicies.length; i++ ) {
//			System.out.println( "ROW: " + indicies[ i ] );
			if ( sortedFilteredData != null && sortedFilteredData.length > 0 ) {
				selected.add( getIDFromDataObject( sortedFilteredData[ indicies[ i ] ] ) );
			}
		}
//		System.out.println( "\nSTORED: " );
//		selected.forEach( s -> System.out.println( s ) );
		
//		System.out.println( "---------------------" );
	}
	
	private void restoreSelected() {
//		System.out.println( "\n---------------------" );
//		System.out.println( "RESTORING\n" );
		List<Integer> l = new ArrayList<>();
		if ( sortedFilteredData != null && sortedFilteredData.length > 0 ) {
			for ( int i = 0; i < sortedFilteredData.length; i++ ) {
				if ( selected.contains( getIDFromDataObject( sortedFilteredData[ i ] ) ) ) {
//					System.out.println( "MATCHED: " + getIDFromDataObject( sortedFilteredData[ i ] ) );
					l.add( i );
				}
			}
		}
		l.forEach( i -> table.getSelectionModel().addSelectionInterval( i, i ) );
//		l.forEach( i -> System.out.println( i ) );
//		System.out.println( "---------------------" );
	}
	
	/**
	 * Convert the data to the appropriate format for the table.
	 * 
	 * @return A "table" of data converted to what the user wants to see.
	 */
	protected Object[][] convertData() {
		int size = data.size();
		Object[][] converted = new Object[size][getColumnNames().length];
		for ( int i = 0; i < size; i++ ) {
			converted[ i ] = convertRow( i );
		}
		order.storeColumnLocations();
		return sorter.sort( size, getColumnNames().length, converted );
	}
	
	
	protected Object[][] filterData( Object[][] input ) {
		Object[][] ret = input;
		if ( input.length != 0 ) {
    		List<Object[]> results = new ArrayList<Object[]>();
    		for ( Object[] row : input ) {
    			if ( filterManager.acceptable( row ) ) {
    				results.add( row );
    			}
    		}
    		ret = new Object[ results.size() ][ input[ 0 ].length ];
    		for ( int i = 0; i < results.size(); i++ ) {
    			ret[ i ] = results.get( i );
    		}
		}
		return ret;
	}
	
	protected Object[][] getSortedFilteredData() {
		sortedFilteredData = filterData( convertData() ); //stored for later use
		return sortedFilteredData;
	}
	
	
	/**
	 * Goes through the columnNames returned by getColumnNames() and adds the data for each row according to the column names
	 * 
	 * By default all cells are set to not editable.  If a change in this behavior is desired, a new TableModel should be constructed
	 * using filterData( convertData() ) to get the data and getColumnNames() to get the column names.
	 * 
	 * @return The constructed table model from the data and column names held by this table implementation
	 */
	protected TableModel createNewModel() {
		TableModel model = new DefaultTableModel( getSortedFilteredData(), getColumnNames() ) {
    		private static final long serialVersionUID = 1L;
    		public boolean isCellEditable( int row, int column ) {
    			return false;
    		}
    	};
    	return model;
	}
	
	/**
	 * Fill the table with data.
	 */
	protected void reconstruct() {
    	if ( !dragging ) {
//    		Runnable run = new Runnable() {
//        		public void run() {
        			table.setModel( createNewModel() );
        	    	if ( headerRenderer != null ) {
        	        	for ( int i = 0; i < table.getColumnCount(); i++ ) {
        	    			table.getColumnModel().getColumn( i ).setHeaderRenderer( headerRenderer );
        	    		}
        	    	}
        	    	reconstructHook();
        			order.swapColumns();		
//        		}    		
//        	};
//        	if (SwingUtilities.isEventDispatchThread()) {
//        		run.run();
//          	} else {
//          		SwingUtilities.invokeLater(run);
//          	}
    	}
	}
	
	/**
	 * can be used to do anything that needs to be done after the table has been reloaded as a new model
	 * 
	 * Good examples of when to override this would be to set custom cell renderers for certain columns, or special spacing for columns
	 */
	protected void reconstructHook() {}
	
	/**
	 * Determines a unique ID from a data Object.  This is used to be able to tell which objects to reselect
	 * when the table reloads.
	 * 
	 * Defaults as data.toString()
	 * 
	 * Should be overridden when necessary (almost always if the data can change) to provide a more unique id match
	 * 
	 * @param data Data object that a table row is constructed from
	 * @return a customized ID String to represent the data row - (data.toString()) is the default
	 */
	protected String getIDFromDataObject( Object[] data ) {
		return data.toString();
	}
	
	public SimpleTableSorter getTableSorter() {
		return sorter;
	}
	
	public FilterManager getFilterManager() {
		return filterManager;
	}

	public JTable getTable() {
		return table;
	}
}