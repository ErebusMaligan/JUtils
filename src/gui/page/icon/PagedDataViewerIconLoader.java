package gui.page.icon;

import icon.DefaultIconLoader;
import static gui.page.icon.PagedDataViewerIconConstants.*;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 5:59:24 PM 
 */
public class PagedDataViewerIconLoader extends DefaultIconLoader {

	public PagedDataViewerIconLoader() {
		names = new String[] { FIRST, NEXT, PREVIOUS, LAST };
		init();
	}
}