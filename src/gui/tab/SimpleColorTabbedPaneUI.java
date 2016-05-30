package gui.tab;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 31, 2015, 12:27:51 AM 
 */
public class SimpleColorTabbedPaneUI extends BasicTabbedPaneUI {

	protected Color bg;
	
	protected Color fg;
	
	protected Insets borderInsets;
	
	protected Insets tabAreaInsets;
	
	public SimpleColorTabbedPaneUI( Color bg, Color fg ) {
		this.bg = bg;
		this.fg = fg;
	}
	@Override
 	protected void paintTabBorder( Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected ) {
		int i = 2;
		g.setColor( isSelected ? bg : fg.darker().darker() );
		g.drawRect( x + i, y + i, w - i, h - i );
 	}
	
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		g.setColor( isSelected ? fg.darker().darker() : bg );
		g.fillRect( x, y, w, h );
	}
	
	@Override
	protected void 	paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected ) {
		super.paintText( g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected );
	}
	
	public void setContentBorderInsets( Insets borderInsets ) {
		this.borderInsets = borderInsets;
	}
	
	@Override
	protected Insets getContentBorderInsets( int tabPlacement ) {
		return borderInsets != null ? borderInsets : super.getContentBorderInsets( tabPlacement );
	}
	
	public void setTabAreaInsets( Insets tabAreaInsets ) {
		this.tabAreaInsets = tabAreaInsets;
	}

	@Override
	protected Insets getTabAreaInsets( int tabPlacement ) {
		return tabAreaInsets != null ? tabAreaInsets : super.getTabAreaInsets( tabPlacement );
	}
}