package statics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 27, 2015, 5:58:50 AM 
 */
public class UIUtils {
	
	public static Color BACKGROUND = Color.BLACK;
	
	public static Color FOREGROUND = Color.RED;
	
	public static int LIGHTS_OFF = 4;
	
	public static void setTabUI( JTabbedPane tab ) {
		LAFUtils.setThemedTabPane( tab, BACKGROUND, FOREGROUND );
	}
	
	public static JScrollPane setJScrollPane( JScrollPane scroll ) {
		LAFUtils.setThemedScrollPane( scroll, BACKGROUND, FOREGROUND.darker() );
		scroll.setBorder( null );
		return scroll;
	}
	
	public static void setJButton( JButton b ) {
		LAFUtils.setThemedButton( b );
		b.setBorder( BorderFactory.createLineBorder( FOREGROUND.darker().darker().darker() ) );
		UIUtils.setColors( b );
	}
	
	public static Color lightsOff( Color c, int count ) {
		return count == 0 ? c : lightsOff( c.darker(), count - 1 );
	}
	
	public static Color lightsOn( Color c, int count ) {
		return count == 0 ? c : lightsOn( c.brighter(), count - 1 );
	}
	
	public static void setColorsOff( Component c ) {
		c.setBackground( lightsOff( BACKGROUND, LIGHTS_OFF ) );
		c.setForeground( lightsOff( FOREGROUND, LIGHTS_OFF ) );
	}
	
	public static void setColors( Component c ) {
		c.setBackground( BACKGROUND );
		c.setForeground( FOREGROUND );
	}
	
	public static void setColorsRecursiveOff( Container in ) {
		UIUtils.setColorsOff( in );
		for ( Component c : in.getComponents() ) {
			if ( c instanceof Container ) {
				setColorsRecursiveOff( (Container)c );
			} else {
				UIUtils.setColorsOff( c );
			}
		}
	}
	
	public static void setColorsRecursive( Container in ) {
		UIUtils.setColors( in );
		for ( Component c : in.getComponents() ) {
			if ( c instanceof Container ) {
				setColorsRecursive( (Container)c );
			} else {
				UIUtils.setColors( c );
			}
		}
	}
	
	public static void setColors( Component... c ) {
		for ( Component a : c ) {
			setColors( a );
		}
	}
	
	public static void setColorsOff( Component... c ) {
		for ( Component a : c ) {
			setColorsOff( a );
		}
	}
}