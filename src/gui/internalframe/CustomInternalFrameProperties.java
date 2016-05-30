package gui.internalframe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 10, 2015, 6:12:44 AM 
 */
public class CustomInternalFrameProperties {
	
	private Color fore;
	
	private Color back;
	
	private int buttonWidth;
	
	private int buttonHeight;
	
	private int s = 3;
	
	private int bw;
	
	private int bh;
	
	/**
	 * Picks some colors out of the UIManager properties... looked good for a dark Aero theme, but generally better to specify specific colors
	 */
	public CustomInternalFrameProperties() {
		this( UIManager.getColor( "InternalFrame.activeTitleBackground" ), UIManager.getColor( "InternalFrame.activeBorderColor" ) );
	}
	
	public CustomInternalFrameProperties( Color fore, Color back ) {
		setColors( fore, back );
		init();
	}
	
	public CustomInternalFrameProperties( Color fore, Color back, int buttonWidth, int buttonHeight, int titleHeight ) {
		setSizes( buttonWidth, buttonHeight, titleHeight );
		setColors( fore, back );
		init();
	}
	
	//InternalFrame.border
	
	private void setColors( Color fore, Color back ) {
		this.fore = fore;
		this.back = back;
		UIManager.put( "InternalFrame.activeTitleBackground", fore );
		UIManager.put( "InternalFrame.activeTitleForeground", back );
		UIManager.put( "InternalFrame.inactiveTitleBackground", back );
		UIManager.put( "InternalFrame.inactiveTitleForeground", fore );
	}
	
	private void setSizes( int buttonWidth, int buttonHeight, int titleHeight ) {
		UIManager.put( "InternalFrame.titleButtonHeight", buttonHeight );
		UIManager.put( "InternalFrame.titleButtonWidth", buttonWidth );
		UIManager.put( "InternalFrame.titlePaneHeight", titleHeight );
	}
	
	private void init() {
		buttonWidth = UIManager.getInt( "InternalFrame.titleButtonWidth" );
		buttonHeight = UIManager.getInt( "InternalFrame.titleButtonHeight" );
		
		bw = buttonWidth * 4 / 5;
		bh = buttonHeight * 4 / 5;
		
		Border e = BorderFactory.createEtchedBorder( fore, back );
		Border l = BorderFactory.createLineBorder( back, 2 );
		Border b = BorderFactory.createBevelBorder( BevelBorder.RAISED, back, back.brighter() );
		Border c2 = BorderFactory.createCompoundBorder( e, l );
		Border c = BorderFactory.createCompoundBorder( c2, b );

		UIManager.put( "InternalFrame.border", c );
		
		UIManager.put( "DesktopIconUI", CustomDesktopIconUI.class.getName() );
		UIManager.put( "InternalFrameUI", CustomInternalFrameUI.class.getName() );
	
        UIManager.put( "InternalFrame.closeIcon", getCloseIcon( false ) );
        UIManager.put( "InternalFrame.maximizeIcon", getMaxIcon( false ) );
        UIManager.put( "InternalFrame.iconifyIcon", getIconifyIcon( false ) );     
        UIManager.put( "InternalFrame.minimizeIcon", getMinIcon( false ) );
        
        UIManager.put( "InternalFrame.closeIconI", getCloseIcon( true ) );
        UIManager.put( "InternalFrame.maximizeIconI", getMaxIcon( true ) );
        UIManager.put( "InternalFrame.iconifyIconI", getIconifyIcon( true ) );     
        UIManager.put( "InternalFrame.minimizeIconI", getMinIcon( true ) );
	}
	
	private BufferedImage createIconImage() {
		return new BufferedImage( bw, bh, BufferedImage.TYPE_INT_ARGB );
	}
	
	private Graphics2D setStandardGraphics( BufferedImage i, boolean invert ) {
		Graphics2D g2 = i.createGraphics();
		g2.setColor( invert ? fore : back );
		g2.fillRoundRect( 0, 0, bw, bh, 5, 5 );
		g2.setColor( invert ? back : fore );
		g2.setStroke( new BasicStroke( 2 ) );
		g2.drawRoundRect( 0, 0, bw, bh, 5, 5 );
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		return g2;
	}
	
	protected ImageIcon getCloseIcon( boolean invert ) {
		BufferedImage i = createIconImage();
		Graphics2D g2 = setStandardGraphics( i, invert );
		g2.drawLine( bw / s, bh / s, bw * ( s - 1 ) / s, bh * ( s - 1 ) / s );
		g2.drawLine( bw / s, bh * ( s - 1 ) / s, bw * ( s - 1 ) / s, bh / s );
		return new ImageIcon( i );
	}

	protected ImageIcon getMaxIcon( boolean invert ) {
		BufferedImage i = createIconImage();
		Graphics2D g2 = setStandardGraphics( i, invert );
	    g2.drawRect( bw / s, bh / s, bw / s, bh / s );
		return new ImageIcon( i );
	}
	
	protected ImageIcon getIconifyIcon( boolean invert ) {
		BufferedImage i = createIconImage();
		Graphics2D g2 = setStandardGraphics( i, invert );
		g2.drawLine( bw / s, bh / 2,  2 * bw / s, bh / 2 );
		return new ImageIcon( i );
	}
	
	protected ImageIcon getMinIcon( boolean invert ) {
		BufferedImage i = createIconImage();
		Graphics2D g2 = setStandardGraphics( i, invert );
		g2.drawRect( bw / 2, bh / s, bw / 4, bh / 4 );
		g2.drawRect( bw / s, bh / 2, bw / 4, bh / 4 );
		return new ImageIcon( i );
	}
}