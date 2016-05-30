package statics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: May 4, 2014, 2:56:26 PM 
 */
public class StringUtils {

	public static String toProperString( String s ) {
		return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 ).toLowerCase();
	}
	
	public static String addZeroes( int i ) {
		return i < 10 ? "0" + String.valueOf( i ) : String.valueOf( i );
	}
	
	public static String readFully( InputStream inputStream, String encoding ) throws IOException {
		return new String( readFully( inputStream ), encoding );
	}

	private static byte[] readFully( InputStream inputStream ) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ( ( length = inputStream.read( buffer ) ) != -1 ) {
			baos.write( buffer, 0, length );
		}
		return baos.toByteArray();
	}
	
	public static String multipleSpacesToSingle( String in ) {
		return in.replaceAll( " +", " " );
	}
	
	public static String ellipsize( String input, int maxLength ) {
		String ellip = "...";
		return ( input == null || input.length() <= maxLength || input.length() < ellip.length() ) ? input : input.substring( 0, maxLength - ellip.length() ).concat( ellip );
	}
}