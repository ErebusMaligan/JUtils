package statics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author Daniel J. Rivers
 *         2008
 *
 * Created: Nov 24, 2008 1:26:45 PM
 */
public class GUIUtils {

	//Spacer available for use if you don't want to make your own... generally this value is a good fit
	public static final Dimension SPACER = new Dimension( 10, 10 );
	
	//General width for components that i found myself using often enough to put in here as a default option
	public static final Dimension FIELD = new Dimension( 150, 24 );
	
	public static final Dimension SHORTEST = new Dimension( 20, 24 );
	
	public static final Dimension SHORTER = new Dimension( 40, 24 );
	
	public static final Dimension SHORT = new Dimension( 80, 24 );
	
	public static final Dimension LONG = new Dimension( 275, 24 );
	
	public static final Dimension SQUARE = new Dimension( 24, 24 );
	
	public static final Dimension ONE = new Dimension( 1, 1 );
	
	public static void center( Component c ) {
		c.setLocation( new Point( c.getLocation().x - ( c.getSize().width / 2 ), c.getLocation().y - ( c.getSize().height / 2 ) ) );
	}
	
	/**
	 * Sets the boundary sizes of a component, so that layouts which respect these sizes will display them properly.
	 * This was created just to save lines of code in places where it would be used often on multiple components.
	 * 
	 * @param c component to size
	 * @param d dimension to size the given component to
	 * 
	 * @return The passed in component
	 */
	public static Component setSizes( Component c, Dimension d ) {
		c.setPreferredSize( d );
		c.setMaximumSize( d );
		c.setMinimumSize( d );
		return c;
	}
    
    /**
     * Derives a dimension from an existing dimension
     * 
     * @param d Input dimension
     * @param dif amount to add to existing dimension (can be negative)
     * @return new dimension created by adding the difference to the given dimension
     */
    public static Dimension deriveDimension( Dimension d, int dif ) {
    	return new Dimension( ( (int)d.getWidth() ) + dif, ( (int)d.getHeight() ) + dif );
    }
	
    /**
     * Creates a visibly empty space with the given dimension on a GUI container 
     * 
     * @param c Container to add the space to
     * @param d Dimension to size the space to
     */
    public static void spacer( Container c, Dimension d ) {
    	c.add( Box.createRigidArea( d ) );
    }
    
    /**
     * Creates a visibly empty space with the default dimension on a GUI container
     * 
     * @param c Container to add the space to
     */
    public static void spacer( Container c ) {
    	spacer( c, SPACER );
    }
    
    public static void vGlue( Container c ) {
    	c.add( Box.createVerticalGlue() );
    }
    
    public static void vGlue2( Container c ) {
    	c.add( Box.createVerticalGlue(), 0 );
    	c.add( Box.createVerticalGlue() );
    }
    
    public static void hGlue( Container c ) {
    	c.add( Box.createHorizontalGlue() );
    }
    
    public static void hGlue2( Container c ) {
    	c.add( Box.createHorizontalGlue(), 0 );
    	c.add( Box.createHorizontalGlue() );
    }
    
    /**
     * Allows the font size and style to be modified for an existing component.  Primarily this is used for taking an existing font
     * and enlarging the font or making it bold but not having to actually create a new font and lookup a valid font title.
     * 
     * @param c Component to change the font on
     * @param size Size of the new font
     * @param style Style of the new font
     */
    public static void setFontStyle( Component c, int size, int style ) {
    	c.setFont( c.getFont().deriveFont( (float)size ) );
    	c.setFont( c.getFont().deriveFont( style ) );
    }
    
    /**
     * Derive a font from the given component with the size and style settings given
     * 
     * @param c Component to change the font on
     * @param size Size of the new font
     * @param style Style of the new font
     * @return The newly derived font
     */
    public static Font deriveFont( Component c, int size, int style ) {
    	return c.getFont().deriveFont( (float)size ).deriveFont( style );
    }
    
    /**
     * Centers the given components on the provided panel, by adding them to an intermediate panel and
     * adding glue above and below them.
     * 
     * @param panel Panel to add components to
     * @param components Components to add (can be a single laid out and constructed JPanel)
     */
    public static void centerV( JPanel panel, Component...components ) {
    	center( panel, true, components );
    }
    
    /**
     * Centers the given components on the provided panel, by adding them to an intermediate panel and
     * adding glue to the left and the right of them.
     * 
     * @param panel Panel to add components to
     * @param components Components to add (can be a single laid out and constructed JPanel)
     */
    public static void centerH( JPanel panel, Component...components ) {
    	center( panel, false, components );
    }
    
    private static void center( JPanel panel, boolean vertical, Component...components ) {
    	JPanel temp = new JPanel();
    	temp.setLayout( new BoxLayout( temp, vertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS ) );
    	for ( Component c : components ) {
    		temp.add( c );
    	}
    	if ( vertical ) {
    		vGlue2( temp );
    	} else {
    		hGlue2( temp );
    	}
    	panel.add( temp );
    }
    

    public static JPanel createHPanel( JPanel panel, Component...components ) {
    	return createHPanel( panel, SPACER, FIELD, components );
    }
    
    public static JPanel createVPanel( JPanel panel, Component...components ) {
    	return createVPanel( panel, SPACER, FIELD, components );
    }
    
    /**
     * Creates a new horizontally laid out panel with the components supplied, at the field dimension given, with empty spaces 
     * the size of the spacer dimension in between each.
     * 
     * Saves quite a bit of layout code for creating a panel style that occurs all too often.
     * 
     * 
     * @param panel Panel to add this newly create panel to
     * @param spacer Dimension of null space between each component laid out
     * @param field Size to set all components to
     * @param components Set of all components to add in order
     * 
     * @return The newly created JPanel
     */
    public static JPanel createHPanel( JPanel panel, Dimension spacer, Dimension field, Component...components ) {
    	return createHPanel( panel, spacer, buildPanel( field, components ) );
    }
    
    /**
     * Creates a new vertically laid out panel with the components supplied, at the field dimension given, with empty spaces 
     * the size of the spacer dimension in between each.
     * 
     * Saves quite a bit of layout code for creating a panel style that occurs all too often.
     * 
     * 
     * @param panel Panel to add this newly create panel to
     * @param spacer Dimension of null space between each component laid out
     * @param field Size to set all components to
     * @param components Set of all components to add in order
     * 
     * @return The newly created JPanel
     */
    public static JPanel createVPanel( JPanel panel, Dimension spacer, Dimension field, Component...components ) {
    	return createVPanel( panel, spacer, buildPanel( field, components ) );
    }
    
    /**
     * Similar to the other createHPanel methods, however no component dimension is supplied.  Instead, the components and a separate dimension for each is supplied.
     * 
     * @param panel Panel to add this newly create panel to
     * @param spacer Dimension of null space between each component laid out
     * @param components  Array of component/dimension pairs in the form [[Component 1][Dimension 1], [Component 2][Dimension 2]...etc ]
     * 
     * @return The newly created JPanel
     */
    public static JPanel createHPanel( JPanel panel, Dimension spacer, Object[]...components ) {
    	return createHorVPanel( BoxLayout.X_AXIS, panel, spacer, components );
    }

    /**
     * Similar to the other createVPanel methods, however no component dimension is supplied.  Instead, the components and a separate dimension for each is supplied.
     * 
     * @param panel Panel to add this newly create panel to
     * @param spacer Dimension of null space between each component laid out
     * @param components  Array of component/dimension pairs in the form [[Component 1][Dimension 1], [Component 2][Dimension 2]...etc ]
     * 
     * @return The newly created JPanel
     */
    public static JPanel createVPanel( JPanel panel, Dimension spacer, Object[]...components ) {
    	return createHorVPanel( BoxLayout.Y_AXIS, panel, spacer, components );	
    }
    
    public static JPanel createHorVPanel( int direction, JPanel panel, Dimension spacer, Object[]...components ) {
    	JPanel p = new JPanel();
    	p.setLayout( new BoxLayout( p, direction ) );
    	for ( int i = 0; i < components.length; i++ ) {
    		Component comp = (Component)components[ i ][ 0 ];
    		p.add( setSizes( comp, (Dimension)components[ i ][ 1 ] ) );
    		if ( i != components.length -1 ) {
    			if ( spacer != null ) {
    				p.add( Box.createRigidArea( spacer ) );
    			}
    		}
    	}
    	panel.add( p );
    	return p;
    }
    
    /**
     * Create a JButton with the associated text and actionlistener given
     * 
     * @param name The text on the button - not sure why i called this name
     * @param al The actionlistener to attach to the button
     * @return The created JButton
     */
    public static JButton createButton( String name, ActionListener al ) {
     	JButton ret = new JButton( name );
        ret.addActionListener( al );
    	return ret;
    }
    
    /**
     * Create a JButton with the associated text, dimension, and actionlistener given
     * 
     * @param name Text on the button
     * @param dim Dimension of the button
     * @param al actionlistener to attach to the button
     * @return The created JButton
     */
    public static JButton createButton( String name, Dimension dim, ActionListener al ) {
    	return (JButton)setSizes( createButton( name, al ), dim );
    }
    
    public static JButton createButton( Icon icon, ActionListener al ) {
    	JButton ret = new JButton( icon );
    	ret.addActionListener( al );
    	return ret;
    }
    
    public static JButton createButton( Icon icon, Dimension dim, ActionListener al ) {
    	return (JButton)setSizes( createButton( icon, al ), dim );
    }
    
    public static JButton createButton( Icon icon, Dimension dim, String command, ActionListener al ) {
    	JButton ret = createButton( icon, dim, al );
    	ret.setActionCommand( command );
    	return ret;
    }
    
    /**
     * Convenience method for creating a text field with the given text that is uneditable - not sure why i created this instead of using labels... but i'm sure there was a reason
     * 
     * @param txt Text to fill the text field with
     * @return The created, uneditable text field
     */
	public static JTextField createUneditableTextField( String txt ) {
		JTextField ret = new JTextField( txt );
		ret.setEditable( false );
		return ret;
	}
	
	/**
	 * Used for finding the frame or dialog that the given component is within (traverses ancestors to get to it)
	 * 
	 * @param c Component being examined
	 * @return Window that this component is within
	 */
    public static Window findWindow(Component c) {
		Window ret = null;
    	if ( c instanceof Frame || c instanceof Dialog ) {
			ret = (Window)c;
		}
		return ret == null ? findWindow( c.getParent() ) : ret;
	}
    
    /**
     * Gets a list of all child components of a given container, recursively
     * 
     * @param c Container being examined
     * @return Recursive list of all child components within the given container
     */
    public static List<Component> getAllComponents( JPanel c ) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for ( Component comp : comps ) {
			compList.add( comp );
			if ( comp instanceof JPanel ) {
				compList.addAll( getAllComponents( (JPanel)comp ) );
			}
			if ( comp instanceof JScrollPane ) {
				Component scrollChild = ( (JScrollPane)comp ).getViewport().getView();
				if (  scrollChild instanceof JPanel ) {
					compList.addAll( getAllComponents( (JPanel)scrollChild ) );
				}
			}
		}
		return compList;
    }
    
    public static Object[][] buildPanel( Dimension field, Component...components ) {
    	Object[][] ret = new Object[ components.length ][ 2 ];
    	for ( int i = 0; i < components.length; i++ ) {
    		ret[ i ] = pair( components[ i ], field );
    	}
    	return ret;
	}
    
    public static Object[] pair( Component c , Dimension d ) {
    	return new Object[] { c, d };
    }
}