package gui.page.panel;

import gui.page.PagedDataViewer;
import gui.page.PagedDataViewer.SELECTION;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:56:52 PM 
 */
public class DefaultSelectionControlPanel extends SelectionControlPanel {
	private static final long serialVersionUID = 1L;
	public void construct( PagedDataViewer<?> pdv ) {
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		int b = 10;
		this.setBorder( BorderFactory.createEmptyBorder( b, b, b, b ) );
		Arrays.asList( new Object[][] { new Object[] { "Select All Pages", SELECTION.SELECT_ALL_PAGES }, new Object[] { "Select Page", SELECTION.SELECT_PAGE }, new Object[] { "Deselect Page", SELECTION.CLEAR_PAGE }, new Object[] { "Deselect All Pages", SELECTION.CLEAR_ALL_PAGES } } ).forEach( arr -> { this.add( GU.createButton( (String)arr[ 0 ], GU.FIELD, e -> pdv.setSelection( (SELECTION)arr[ 1 ] ) ) ); GU.spacer( this ); } );
		this.remove( this.getComponentCount() - 1 );
		GU.hGlue2( this );
	}
}