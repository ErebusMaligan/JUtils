package gui.page.panel;

import gui.page.icon.PagedDataViewerIconLoader;
import icon.DefaultIconLoader;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:59:10 PM 
 */
public class DefaultPagedDataViewerControlFactory implements PagedDataViewerControlFactory {

	@Override
	public PageControlPanel getPageControlPanel() {
		return new DefaultPageControlPanel();
	}

	@Override
	public SelectionControlPanel getSelectionControlPanel() {
		return new DefaultSelectionControlPanel();
	}

	@Override
	public DefaultIconLoader getIconLoader() {
		return new PagedDataViewerIconLoader();
	}
	
}