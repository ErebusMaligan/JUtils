package statics;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 6:28:57 PM
 */
public class ExceptionUtils {
	
	public static String toString( Exception e ) {
		try ( StringWriter sw = new StringWriter() ) {
			e.printStackTrace( new PrintWriter( sw ) );
			return sw.toString();
		} catch ( IOException e1 ) {
			return "Unable to convert Exception to String";
		}
	}
}