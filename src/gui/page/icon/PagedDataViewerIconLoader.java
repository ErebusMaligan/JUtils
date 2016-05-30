package gui.page.icon;

import icon.DefaultIconLoader;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 5:59:24 PM 
 */
public class PagedDataViewerIconLoader extends DefaultIconLoader {

	public static final String FIRST = "first";
	
	public static final String NEXT = "next";
	
	public static final String PREVIOUS = "previous";
	
	public static final String LAST = "last";
	
	public PagedDataViewerIconLoader() {
		names = new String[] { FIRST, NEXT, PREVIOUS, LAST };
		init();
	}
}