package gui.page.panel;

import gui.page.PagedDataViewer;

import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:55:28 PM 
 */
public abstract class SelectionControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public abstract void construct( PagedDataViewer<?> viewer );	
}