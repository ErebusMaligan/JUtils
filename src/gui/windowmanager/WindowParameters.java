package gui.windowmanager;

import java.awt.Dimension;
import java.util.Map;

import xml.XMLExpansion;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 9:30:54 PM 
 */
public class WindowParameters {

	public boolean maximum;
	
	public Dimension size;
	
	public int x;
	
	public int y;
	
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		Map<String, String[]> values = e.getValues();
		size = new Dimension( Integer.parseInt( values.get( WindowManagerConstants.XWW )[ 0 ] ), Integer.parseInt( values.get( WindowManagerConstants.XWH )[ 0 ] ) );
		x = Integer.parseInt( values.get( WindowManagerConstants.XWX )[ 0 ] );
		y = Integer.parseInt( values.get( WindowManagerConstants.XWY )[ 0 ] );
		maximum = Boolean.parseBoolean( values.get( WindowManagerConstants.XWM )[ 0 ] );
	}
}