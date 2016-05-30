package gui.menuitem;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 15, 2015, 10:15:23 AM 
 */
public class CustomMenuItemUI extends BasicMenuItemUI {
	@Override
    public void paint(Graphics g, JComponent c) {
		paintMenuItem( g, c, checkIcon, arrowIcon, c.getForeground().darker().darker(), selectionForeground, defaultTextIconGap );
    }
}