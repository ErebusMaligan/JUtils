package statics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Wrapper class for GUIUtils for shorter syntax
 * 
 * @author Daniel J. Rivers
 *         2012
 *
 * Created: Dec 10, 2012, 5:00:52 PM 
 */
public class GU extends GUIUtils {
	
	public static Component sizes( Component c, Dimension d ) {
		return GUIUtils.setSizes( c, d );
	}
	
	public static void vg( Container c ) {
		GUIUtils.vGlue( c );
	}
	
	public static void hg( Container c ) {
		GUIUtils.hGlue( c );
	}
	
	public static JPanel hp( JPanel panel, Component... components ) {
		return GUIUtils.createHPanel( panel, components );
	}
	
	public static JPanel vp( JPanel panel, Component... components ) {
		return GUIUtils.createVPanel( panel, components );
	}
	
	public static JPanel hp( JPanel panel, Dimension spacer, Dimension field, Component... components ) {
		return GUIUtils.createHPanel( panel, spacer, field, components );
	}
	
	public static JPanel vp( JPanel panel, Dimension spacer, Dimension field, Component... components ) {
		return GUIUtils.createVPanel( panel, spacer, field, components );
	}
	
	public static JPanel hp( JPanel panel, Dimension spacer, Object[]... components ) {
		return GUIUtils.createHPanel( panel, spacer, components );
	}
	
	public static JPanel vp( JPanel panel, Dimension spacer, Object[]... components ) {
		return GUIUtils.createVPanel( panel, spacer, components );
	}
	
	 public static void cv( JPanel panel, Component...components ) {
		 GUIUtils.centerV( panel, components );
	 }
	 
	 public static void ch( JPanel panel, Component...components ) {
		 GUIUtils.centerH( panel, components );
	 }
	
	public static Object[] pr( Component c, Dimension d ) {
		return GUIUtils.pair( c, d );
	}
	
	public static Dimension dDim( Dimension d, int dif ) {
		return GUIUtils.deriveDimension( d, dif );
	}
	
	//creates various borders around all sub JComponents so you can see how they are aligned
	public static void debug( JPanel c ) {
		for ( Component x : GUIUtils.getAllComponents( c ) ) {
			if ( x instanceof JComponent ) {
				JComponent jc = ( (JComponent)x );
				if ( jc instanceof Box.Filler ) {
					jc.setBorder( BorderFactory.createLineBorder( Color.GREEN, 1 ) );
				} else if ( jc instanceof JPanel ) {
					jc.setBorder( BorderFactory.createLineBorder( Color.LIGHT_GRAY, 1 ) );
				} else {
					if ( jc instanceof JRadioButton ) {
    					JRadioButton rb = (JRadioButton)jc;
    					rb.setBorderPainted( true  );
					}
					jc.setBorder( BorderFactory.createLineBorder( Color.RED, 2 ) );
				}
			} else {
				System.out.println( x.getClass() );
			}
		}
	}
}