package gui.panel;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Aug 11, 2015, 9:57:10 PM 
 */
public class TransparentPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	{
		setOpaque( false );
	}
	
	public TransparentPanel() {
		super();
	}
	
	public TransparentPanel( LayoutManager layout ) {
		super( layout );
	}

	@Override
	public void paintComponent( Graphics g ) {
		g.setColor( getBackground() );
		Rectangle r = g.getClipBounds();
		g.fillRect( r.x, r.y, r.width, r.height );
		super.paintComponent( g );
	}
}