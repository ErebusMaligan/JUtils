package gui.page.panel;

import gui.format.EnhancedFormattedTextField;
import gui.page.PagedDataViewer;
import gui.page.icon.PagedDataViewerIconLoader;
import gui.page.model.PageControlModel;
import gui.page.model.PageControlModel.PAGES;

import java.awt.Component;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 1, 2015, 4:38:27 PM 
 */
public class DefaultPageControlPanel extends PageControlPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField cur = new EnhancedFormattedTextField( NumberFormat.getIntegerInstance() );
	
	private JLabel pages = new JLabel();
	
	private JTextField per = new EnhancedFormattedTextField( NumberFormat.getIntegerInstance() );

	@Override
	public void construct( PagedDataViewer<?> dataViewer ) {
		PageControlModel model = dataViewer.getPageControlModel();
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		int x = 10;
		this.setBorder( BorderFactory.createEmptyBorder( x, x, x, x ) );
		Arrays.asList( new JComponent[] { cur, per, pages } ).forEach( c -> GU.setSizes( c, GU.SHORTER ) );
		Arrays.asList( new JComponent[] { new JLabel( "Page: ", JLabel.RIGHT ), cur, pages } ).forEach( c -> this.add( c ) );
		cur.addActionListener( e -> model.setPage( Integer.parseInt( cur.getText() ) - 1 ) );
		PagedDataViewerIconLoader i = new PagedDataViewerIconLoader();
		Dimension bDim = new Dimension( 35, 35 );
		GU.spacer( this, bDim );
		Arrays.asList( new Object[][] { new Object[] { PagedDataViewerIconLoader.FIRST, PAGES.FIRST, "Display first page" }, new Object[] { PagedDataViewerIconLoader.PREVIOUS, PAGES.PREVIOUS, "Display previous page" }, 
				new Object[] { PagedDataViewerIconLoader.NEXT, PAGES.NEXT, "Display next page" }, new Object[] { PagedDataViewerIconLoader.LAST, PAGES.LAST, "Display last page" } } )
		.forEach( arr -> {
			JButton b = GU.createButton( "", GU.SHORT, e -> model.setPage( (PAGES)arr[ 1 ] ) );
			b.setToolTipText( (String)arr[ 2 ] );
//			b.setContentAreaFilled( false );
			GU.setSizes( b, bDim );
			b.setIcon( i.getIcon( (String)arr[ 0 ] ) );
			this.add( b );
			GU.spacer( this, bDim );
		} );
		Arrays.asList( new Component[] { per, Box.createRigidArea( GU.SPACER ), new JLabel( "Elements Per Page", JLabel.LEFT ) } ).forEach( c -> this.add( c ) );
		per.addActionListener( e -> model.setPerPage( Integer.parseInt( per.getText() ) ) );
		GU.hGlue2( this );		
	}

	@Override
	public void setPageInfo( int cur, int pages, int per, int minPer, int maxPer ) {
		this.cur.setText( String.valueOf( cur + 1 ) );
		this.pages.setText( " /  " + pages );
		this.per.setText( String.valueOf( per ) );
		this.per.setToolTipText( "Must be a value between: " + minPer + " - " + maxPer );
	}
}