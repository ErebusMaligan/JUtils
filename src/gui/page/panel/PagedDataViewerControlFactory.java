package gui.page.panel;

import icon.DefaultIconLoader;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:49:41 PM 
 */
public interface PagedDataViewerControlFactory {
	
	public PageControlPanel getPageControlPanel();
	
	public SelectionControlPanel getSelectionControlPanel();
	
	public DefaultIconLoader getIconLoader();
}