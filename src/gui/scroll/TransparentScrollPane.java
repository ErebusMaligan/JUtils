package gui.scroll;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 11, 2015, 10:19:04 PM 
 */
public class TransparentScrollPane extends JScrollPane {
	
	private static final long serialVersionUID = 1L;

	{
		setOpaque( false );
	}
	
	public TransparentScrollPane() {
		super();
	}
	
	public TransparentScrollPane( Component view ) {
		super( view );
	}

	@Override
	public void paintComponent( Graphics g ) {
		g.setColor( getBackground() );
		Rectangle r = g.getClipBounds();
		g.fillRect( r.x, r.y, r.width, r.height );
		super.paintComponent( g );
	}
	
	@Override
    protected JViewport createViewport() {
        return new TransparentViewport();
    }
}