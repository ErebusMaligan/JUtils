package gui.internalframe;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import statics.StringUtils;

/**
 * In order to use this class properly, certain properties must have been set by first creating a new CustomInternalFrameProperties class somewhere in the application
 * 
 * Mostly constructed from carefully chosen sections of BasicInternalFrameTitlePane to override
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 10, 2015, 4:05:00 AM 
 */
public class CustomInternalFrameTitlePane extends BasicInternalFrameTitlePane {

	private static final long serialVersionUID = 1L;

	protected int wp = UIManager.getInt( "InternalFrame.titleButtonWidth" );

	protected int hp = UIManager.getInt( "InternalFrame.titleButtonHeight" );

	protected int thp = UIManager.getInt( "InternalFrame.titlePaneHeight" );

	protected LayoutManager layout;
	
	protected int minTitleLength = 9;

	public CustomInternalFrameTitlePane( JInternalFrame f ) {
		super( f );
	}

	@Override
	protected void paintTitleBackground( Graphics g ) {
		boolean sel = frame.isSelected();
//		GradientPaint gp = new GradientPaint( getWidth() / 4, 0, sel ? selectedTitleColor : notSelectedTitleColor,  3 * getWidth() / 4, getHeight(), sel ? notSelectedTitleColor : selectedTitleColor );
		LinearGradientPaint gp = new LinearGradientPaint( new Point2D.Float( getWidth() / 2, 0 ), new Point2D.Float( getWidth() / 2, getHeight() / 2 ), new float[] { 0f, .6f }, 
				new Color[] { sel ? notSelectedTitleColor : selectedTitleColor, sel ? selectedTitleColor : notSelectedTitleColor }, MultipleGradientPaint.CycleMethod.REFLECT  );
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect( 0, 0, getWidth(), getHeight() );
	    g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g2.setPaint( gp );
		g2.fillRect( 0, 0, getWidth(), getHeight() );
		g2.setColor( selectedTitleColor );
		g2.drawRect( 0 - 1, 0, getWidth() + 1, getHeight() - 1 );
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		
		//this prevents the default aero button outlines from showing around the edges of the buttons
		for ( JButton b : new JButton[] { iconButton, maxButton, closeButton } ) {
			b.setContentAreaFilled( false );
		}
		
		//this stuff enables button highlighting on rollover
		closeButton.addMouseListener( new MouseAdapter() {
			public void mouseEntered( MouseEvent e ) {
				closeButton.setIcon( (ImageIcon)UIManager.get( "InternalFrame.closeIconI" ) );
			}
			
			public void mouseExited( MouseEvent e ) {
				closeButton.setIcon( (ImageIcon)UIManager.get( "InternalFrame.closeIcon" ) );
			}
		} );
		maxButton.addMouseListener( new MouseAdapter() {
			public void mouseEntered( MouseEvent e ) {
				maxButton.setIcon( frame.isMaximum() ? 
						frame.isIcon() ? (ImageIcon)UIManager.get( "InternalFrame.maximizeIconI" ) : (ImageIcon)UIManager.get( "InternalFrame.minimizeIconI" ) : 
						(ImageIcon)UIManager.get( "InternalFrame.maximizeIconI" ) );
			}
			
			public void mouseExited( MouseEvent e ) {
				maxButton.setIcon( frame.isMaximum() ? 
						frame.isIcon() ? (ImageIcon)UIManager.get( "InternalFrame.maximizeIcon" ) : (ImageIcon)UIManager.get( "InternalFrame.minimizeIcon" ) : 
							(ImageIcon)UIManager.get( "InternalFrame.maximizeIcon" ) );
			}
		} );
		iconButton.addMouseListener( new MouseAdapter() {
			public void mouseEntered( MouseEvent e ) {
				iconButton.setIcon( frame.isIcon() ? (ImageIcon)UIManager.get( "InternalFrame.minimizeIconI" ) : (ImageIcon)UIManager.get( "InternalFrame.iconifyIconI" ) );
			}
			
			public void mouseExited( MouseEvent e ) {
				iconButton.setIcon( frame.isIcon() ? (ImageIcon)UIManager.get( "InternalFrame.minimizeIcon" ) : (ImageIcon)UIManager.get( "InternalFrame.iconifyIcon" ) );
			}
		} );
	}
	
	@Override
	protected String getTitle( String text, FontMetrics fm, int availTextWidth ) {
		String c = text;
		if ( text != null ) {
			if ( text.length() > minTitleLength ) {
				c = text.substring( 0, minTitleLength );
			}
		}
		return StringUtils.ellipsize( c, Math.max( fm.stringWidth( c ), availTextWidth ) );
	}

	@Override
	protected LayoutManager createLayout() {
		if ( layout == null ) {
			layout = new CustomLayout();
		}
		return layout;
	}

	@Override
	public Dimension getMinimumSize() {
		return layout.minimumLayoutSize( this );
	}

	protected class CustomLayout extends TitlePaneLayout {

		@Override
		public Dimension preferredLayoutSize( Container c ) {
			return minimumLayoutSize( c );
		}

		@Override
		public Dimension minimumLayoutSize( Container c ) {
			// Calculate width.
			int width = 22;

			for ( Boolean b : new Boolean[] { frame.isClosable(), frame.isMaximizable(), frame.isIconifiable() } ) {
				if ( b ) {
					width += wp;
				}
			}

			FontMetrics fm = frame.getFontMetrics( getFont() );
			String frameTitle = frame.getTitle();
			int title_w = frameTitle != null ? fm.stringWidth( frameTitle ) : 0;
			int title_length = frameTitle != null ? frameTitle.length() : 0;

			// Leave room for 9 characters in the title.  was originally 3
			if ( title_length > minTitleLength ) {
				int subtitle_w = fm.stringWidth( frameTitle.substring( 0, minTitleLength - 3 ) + "..." );  //was substring( 0, 3 ) by default
				width += ( title_w < subtitle_w ) ? title_w : subtitle_w;
			} else {
				width += title_w;
			}

			Dimension dim = new Dimension( width, thp );

			// Take into account the border insets if any.
			if ( getBorder() != null ) {
				Insets insets = getBorder().getBorderInsets( c );
				dim.height += insets.top + insets.bottom;
				dim.width += insets.left + insets.right;
			}
			return dim;
		}

		@Override
		public void layoutContainer( Container c ) {
			boolean leftToRight = frame.getComponentOrientation().isLeftToRight();

			int w = getWidth();
			int h = getHeight();
			int x;

			Icon icon = frame.getFrameIcon();
			int iconHeight = 0;
			if ( icon != null ) {
				iconHeight = icon.getIconHeight();
			}
			x = ( leftToRight ) ? 2 : w - 16 - 2;
			menuBar.setBounds( x, ( h - iconHeight ) / 2, 16, 16 );

			int off = 0; //was -4 with windows icons to make them fit close together

			x = ( leftToRight ) ? w - wp - off : off;

			if ( frame.isClosable() ) {
				closeButton.setBounds( x, ( h - hp ) / 2, wp - off, hp );
				x += ( leftToRight ) ? -( wp + off ) : wp + off;
			}

			if ( frame.isMaximizable() ) {
				maxButton.setBounds( x, ( h - hp ) / 2, wp - off, hp );
				x += ( leftToRight ) ? -( wp + off ) : wp + off;
			}

			if ( frame.isIconifiable() ) {
				iconButton.setBounds( x, ( h - hp ) / 2, wp - off, hp );
			}
		}
	}
}