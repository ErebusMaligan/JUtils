package gui.page;

import gui.page.model.PageControlModel;
import gui.page.model.PageListener;
import gui.page.panel.DefaultPagedDataViewerControlFactory;
import gui.page.panel.PageControlPanel;
import gui.page.panel.PagedDataViewerControlFactory;
import gui.page.panel.SelectionControlPanel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 29, 2015, 9:28:06 AM 
 */
public class PagedDataViewer<T> extends JPanel implements PageListener, ListSelectionListener {
	
	public enum SELECTION { SELECT_PAGE, SELECT_ALL_PAGES, CLEAR_PAGE, CLEAR_ALL_PAGES } 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<T> data;
	
	protected PageControlModel pcm;

	protected DefaultListModel<T> model = new DefaultListModel<>();
	
	protected JList<T> list = new JList<>( model );
	
	protected HashSet<Integer> selection = new HashSet<>();
	
	protected SelectionControlPanel scp;
	
	protected PageControlPanel pcp;
	
	public PagedDataViewer() {
		this( null, null );
	}
	
	public PagedDataViewer( List<T> data ) {
		this( null, data );
	}
	
	public PagedDataViewer( PagedDataViewerControlFactory controls ) {
		this( controls, null );
	}
	
	public PagedDataViewer( PagedDataViewerControlFactory controls, List<T> data ) {
		if ( controls == null ) {
			controls = new DefaultPagedDataViewerControlFactory();
		}
		if ( data == null ) {
			data = new ArrayList<>();
		}
		pcm = new PageControlModel( this );
		list.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		list.addListSelectionListener( this );
		this.setLayout( new BorderLayout() );
		pcp = controls.getPageControlPanel();
		pcp.construct( this, controls.getIconLoader() );
		this.add( pcp, BorderLayout.NORTH );
		this.add( new JScrollPane( list ), BorderLayout.CENTER );
		scp = controls.getSelectionControlPanel();
		scp.construct( this );
		this.add( scp, BorderLayout.SOUTH );
		setData( data );
	}
	
	public void setData( List<T> data ) {
		selection.clear();
		this.data = data;
		pcm.setDataElements( data.size() );
	}
	

	@Override
	public void pageSet( int p ) {
		list.removeListSelectionListener( this );  //remove so the clearing the model doesn't cause the selection hashset to also get cleared
		model.clear();
		if ( data != null ) {
			data.subList( pcm.getDataIndexStart(), pcm.getDataIndexEnd() ).forEach( e -> model.addElement( e ) );
			for ( int i = 0; i < model.getSize(); i++ ) {
				if ( selection.contains( pcm.translatePageRowToDataIndex( i ) ) ) {
					list.getSelectionModel().addSelectionInterval( i, i );
				}
			}
		}
		pcp.setPageInfo( pcm.getCurrentPage(), pcm.getNumPages(), pcm.getElementsPerPage(), pcm.getMinPer(), pcm.getMaxPer() );
		list.addListSelectionListener( this );  //add back now after the new page has loaded
	}

	@Override
	public void valueChanged( ListSelectionEvent e ) {
		if ( !e.getValueIsAdjusting() ) {
			for ( int i = e.getFirstIndex(); i <= e.getLastIndex(); i++ ) {
				if ( list.getSelectionModel().isSelectedIndex( i ) ) {
					selection.add( pcm.translatePageRowToDataIndex( i ) );
				} else {
					selection.remove( pcm.translatePageRowToDataIndex( i ) );
				}
			}
		}
	}
	
	public void setSelection( SELECTION s ) {
		switch ( s ) {
			case SELECT_ALL_PAGES: list.getSelectionModel().setSelectionInterval( 0, model.getSize() - 1 ); for ( int i = 0; i < data.size(); i++ ) { selection.add( i ); }; break;
			case SELECT_PAGE:  list.getSelectionModel().setSelectionInterval( 0, model.getSize() - 1 ); break;
			case CLEAR_PAGE:  list.getSelectionModel().clearSelection(); break;
			case CLEAR_ALL_PAGES: list.getSelectionModel().clearSelection(); selection.clear(); break;
		}
	}
	
	public JList<T> getList() { return list; }
	
	public PageControlModel getPageControlModel() { return pcm; }
	
	public List<T> getSelectedData() {
		final List<T> ret = new ArrayList<T>();
		if ( data.size() != selection.size() ) {
			selection.forEach( s -> ret.add( data.get( s ) ) );
		}
		return data.size() == selection.size() ? data : ret;
	}
	
	public List<T> getAllData() {
		return data;
	}
	
	public void setEnabled( boolean enabled ) {
		Arrays.asList( list, pcp, scp ).forEach( c -> c.setEnabled( enabled ) );
	}
}