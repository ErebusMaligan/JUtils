package gui.table.icons;

import icon.DefaultIconLoader;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Feb 6, 2013, 12:03:32 PM 
 */
public class TableIconLoader extends DefaultIconLoader {
	
	/**
	 * singleton instance of this class
	 */
	private static TableIconLoader instance;

	/**
	 * Creates this class and inits all icons into the map
	 */
	private TableIconLoader() {
		names = TableIconConstants.ICONS;
		init();
	}
	
	/**
	 * Singleton access to this class
	 * 
	 * @return single instance of this class being used
	 */
	public static TableIconLoader getInstance() {
		if ( instance == null ) {
			instance = new TableIconLoader();
		}
		return instance;
	}	
}