package gui.menu;

import icon.creator.TriangleIconCreator;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 15, 2015, 10:40:04 AM 
 */
public class CustomMenuUI extends BasicMenuUI {
	
	@Override
    public void paint(Graphics g, JComponent c) {
		icon.creator.TriangleIconCreator tri = new TriangleIconCreator();
		Icon[] icons = tri.getIcons( c.getBackground(), c.getForeground(), arrowIcon.getIconWidth(), arrowIcon.getIconHeight() );
		paintMenuItem( g, c, checkIcon, icons[ TriangleIconCreator.Orientation.RIGHT.toIndex() ], c.getForeground().darker().darker(), selectionForeground, defaultTextIconGap );
    }	
}