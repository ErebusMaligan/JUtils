package xml;

import java.util.Map;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Sep 10, 2014, 11:25:46 PM 
 */
public interface XMLValuesWithAttributes extends XMLValues {
	
	public Map<String, Map<String, String>[]> saveNodeAttributes();
	
	public Map<String, String> saveAttributes();
}