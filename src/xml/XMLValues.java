package xml;

import java.util.List;
import java.util.Map;


/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Oct 31, 2013, 12:13:50 PM 
 */
public interface XMLValues {
	
	public List<XMLValues> getChildNodes();
	
	public Map<String, Map<String, String[]>> saveParamsAsXML();
	
	public void loadParamsFromXMLValues( XMLExpansion expansion );

}
