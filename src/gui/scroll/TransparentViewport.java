package gui.scroll;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JViewport;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 11, 2015, 10:23:52 PM 
 */
public class TransparentViewport extends JViewport {

	private static final long serialVersionUID = 1L;

	{
		setOpaque( false );
	}
	
	@Override
	public void paintComponent( Graphics g ) {
		g.setColor( getBackground() );
		Rectangle r = g.getClipBounds();
		g.fillRect( r.x, r.y, r.width, r.height );
		super.paintComponent( g );
	}
}