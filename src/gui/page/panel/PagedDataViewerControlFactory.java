package gui.page.panel;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:49:41 PM 
 */
public interface PagedDataViewerControlFactory {
	
	public PageControlPanel getPageControlPanel();
	
	public SelectionControlPanel getSelectionControlPanel();
}