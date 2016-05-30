package statics;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 6:42:59 PM
 */
public class TimeUtils {
	
	public static String toTOD( long millis ) {
		return new SimpleDateFormat( "HH:mm:ss:SSS" ).format( new Date( millis ) );
	}
}