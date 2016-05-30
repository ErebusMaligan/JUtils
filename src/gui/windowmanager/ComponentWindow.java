package gui.windowmanager;

import java.awt.BorderLayout;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 9:33:53 PM 
 */
public class ComponentWindow extends JInternalFrame implements XMLValues {

	private static final long serialVersionUID = 1L;
	
	private WindowDefinition def;
	
	private WindowManager wm;
	
	private JComponent centerComponent = null;
	
	public ComponentWindow( Object state, WindowManager wm, WindowDefinition def ) {
		super( def.getTitle(), true, true, true, true );
		this.wm = wm;
		this.def = def;
		setLayout( new BorderLayout() );
		centerComponent = def.getCenterComponent( state );
		add( centerComponent, BorderLayout.CENTER );
		additionalConfiguration( state );
//		add( new JScrollPane( def.getCenterComponent( state ) ), BorderLayout.CENTER );
		pack();
		addInternalFrameListener( new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed( InternalFrameEvent e ) {
				ComponentWindow.this.wm.removeWindow( ComponentWindow.this );
				if ( centerComponent instanceof WindowClosedHook ) { //couldn't think of another way to do this at the moment
					( (WindowClosedHook)centerComponent ).closed();
				}
			}
		} );
	}
	
	/**
	 * Override in subclasses if you need it - this is called from the constructor after the layout is set and component is added
	 * 
	 * @param state Application State object passed to the window from the WindowManager
	 */
	public void additionalConfiguration( Object state ) {}
	
	public void setParameters( WindowParameters p ) {
		try {
			setMaximum( p.maximum );
		} catch ( PropertyVetoException e ) {
			e.printStackTrace();
		}
		this.setLocation( new Point( p.x, p.y ) );
		if ( p.size != null ) { 
			this.setSize( p.size );
		}
	}

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<>();
		Map<String, String[]> values = new HashMap<>();
		values.put( WindowManagerConstants.XWINTITLE, new String[] { def.getTitle() } );
		values.put( WindowManagerConstants.XWH, new String[] { String.valueOf( this.getHeight() ) } );
		values.put( WindowManagerConstants.XWW, new String[] { String.valueOf( this.getWidth() ) } );
		values.put( WindowManagerConstants.XWX, new String[] { String.valueOf( this.getX() ) } );
		values.put( WindowManagerConstants.XWY, new String[] { String.valueOf( this.getY() ) } );
		values.put( WindowManagerConstants.XWM, new String[] { String.valueOf( this.isMaximum() ) } );
		ret.put( WindowManagerConstants.XWINDOW, values );
		return ret;
	}
}