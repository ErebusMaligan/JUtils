package gui.table.renderer;

import gui.table.AbstractTableImplementation;
import gui.table.icons.TableIconConstants;
import gui.table.icons.TableIconLoader;
import gui.table.sorter.BasicTableSorter;
import icon.creator.TriangleIconCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Feb 5, 2013, 2:56:20 PM 
 */
public class DefaultHeaderCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	protected Border cellBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED );
	
	protected AbstractTableImplementation ati = null;
	
	protected Color text = null;
	
	protected Color gradient = null;
	
	protected Color back = null;
	
	protected Color iconLight = null;
	
	protected Color iconDark = null;
	
	protected Icon[] arrows;
	
	public DefaultHeaderCellRenderer( AbstractTableImplementation ati ) {
		this.ati = ati;
		generateIcons();
	}
	
	public DefaultHeaderCellRenderer( AbstractTableImplementation ati, Color text, Color gradient ) {
		this( ati );
		setTextColor( text );
		setGradientColor( gradient );
	}
	
	public void setTextColor( Color text ) {
		this.text = text;
	}
	
	public void setGradientColor( Color gradient ) {
		this.gradient = gradient;
		generateIcons();
	}
	
	public void setGradientBackgroundColor( Color back ) {
		this.back = back;
		generateIcons();
	}
	
	public void setIconColors( Color iconLight, Color iconDark ) {
		this.iconLight = iconLight;
		this.iconDark = iconDark;
		generateIcons();
	}
	
	private void generateIcons() {
		JLabel l = new JLabel();
		arrows = new TriangleIconCreator().getIcons( iconDark == null ? ( back == null ? l.getBackground() : back ) : iconDark, iconLight == null ? ( gradient == null ? Color.GRAY : gradient ) : iconLight, 10, 15 );
	}
	
	public void setBorder( Border cellBorder ) {
		this.cellBorder = cellBorder;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		JPanel p = new JPanel( new BorderLayout() );
		JLabel title = new GradientLabel( (String)value );
		if ( text != null ) {
			title.setForeground( text );
		}
		title.setHorizontalAlignment( JLabel.CENTER );
		p.add( title, BorderLayout.CENTER );
		p.setBorder( cellBorder );
		try {
			if ( table.convertColumnIndexToView( ( (BasicTableSorter)ati.getTableSorter() ).getModelIndex() ) == column ) { //This is only safe if you know for a fact that your sorter is/extends BasicTableSorter
				JLabel sort = new GradientLabel();
				sort.setOpaque( false );
				if ( ( (BasicTableSorter)ati.getTableSorter() ).isReversed() ) {
					sort.setIcon( arrows[ 1 ] );
	//				sort.setIcon( TableIconLoader.getInstance().getIcon( TableIconConstants.UP_ARROW ) );
				} else {
					sort.setIcon( arrows[ 0 ] );
	//				sort.setIcon( TableIconLoader.getInstance().getIcon( TableIconConstants.DOWN_ARROW ) );
				}
				p.add( sort, BorderLayout.WEST );
			}
			if ( ati.getFilterManager().hasFilter( table.convertColumnIndexToModel( column ) ) ) {
				JLabel filter = new GradientLabel();
				filter.setOpaque( false );
				filter.setIcon( TableIconLoader.getInstance().getIcon( TableIconConstants.FILTER ) );
				p.add( filter, BorderLayout.EAST );
			}
		} catch ( ArrayIndexOutOfBoundsException e ) {
			//do nothing with this since it doesn't really effect functionality and can only happen in very select cases
		}
		return p;
	}
	
	private class GradientLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public GradientLabel() {
		}
		
		public GradientLabel( String s ) {
			super( s );
		}
		
		public void paint( Graphics g ) {
			GradientPaint newPaint = new GradientPaint( 0, 0, back == null ? getBackground() : back, 0, getHeight(), gradient == null ? Color.GRAY : gradient, true );
			Graphics2D g2d = (Graphics2D)g;
			Paint oldPaint = g2d.getPaint();
			g2d.setPaint( newPaint );
			g2d.fillRect( 0, 0, getWidth(), getHeight() );
			g2d.setPaint( oldPaint );
			super.paint( g );
		}
	}
}
