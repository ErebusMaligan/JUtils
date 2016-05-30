package gui.page.panel;

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
}