package gui.page.model;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 30, 2015, 7:14:56 PM 
 */
public class PageControlModel {
	
	public enum PAGES { FIRST, NEXT, PREVIOUS, LAST };
	
	protected int pages = 1;
	
	protected int page = 0;
	
	protected int per = -1;
	
	protected PageListener l;
	
	protected int size = 0;
	
	protected int maxPer = 1000;
	
	protected int minPer = 10;
	
	public PageControlModel( PageListener l ) {
		this.l = l;
	}
	
	public void setDataElements( int size ) {
		this.size = size;
		setPerPage( size > 10 && size / 10 > 10 ? size / 10 : 10 );
	}
	
	private void setPages() {
		pages = size / per;
		if ( pages == 0 || size % per != 0 ) {
			pages++;
		}
	}
	
	public void setPerPage( int per ) {
		this.per = per < minPer ? minPer : per > maxPer ? maxPer : per;
		setPages();
		if ( !SwingUtilities.isEventDispatchThread() ) {
			try {
				SwingUtilities.invokeAndWait( () -> setPage( PAGES.FIRST ) );
			} catch ( InvocationTargetException | InterruptedException e ) {
				//don't care
			}
		} else {
			setPage( PAGES.FIRST );
		}
	}
	
	public void setMaxPerPage( int maxPer ) {
		this.maxPer = maxPer;
	}
	
	public void setMinPerPage( int minPer ) {
		this.minPer = minPer;
	}
	
	public void setPage( int p ) {
		this.page = p < 0 ? 0 : p > pages - 1 ? pages - 1 : p;
		l.pageSet( page );
	}
	
	public void setPage( PAGES p ) {
		switch ( p ) {
			case FIRST:  setPage( 0 ); break;
			case NEXT: setPage( page + 1 ); break;
			case PREVIOUS: setPage( page - 1 ); break;
			case LAST: setPage( pages - 1 ); break;
		}
	}
	
	public int translatePageRowToDataIndex( int row ) {
		return ( page * per ) + row;
	}
	
	public int getDataIndexStart() {
		return page * per;
	}
	
	public int getDataIndexEnd() {
		int end = ( page + 1 ) * per;
		return end > size ? size : end;
	}
	
	public int getCurrentPage() {
		return page;
	}
	
	public int getNumPages() {
		return pages;
	}
	
	public int getElementsPerPage() {
		return per;
	}

	public int getMaxPer() {
		return maxPer;
	}

	public int getMinPer() {
		return minPer;
	}	
}