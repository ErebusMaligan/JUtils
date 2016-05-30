package statics;

import java.io.File;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 4, 2013, 12:10:31 PM 
 */
public class FileUtils {
	
	public static File getPathForFile( File f ) {
		return new File( f.getAbsolutePath().substring( 0, f.getAbsolutePath().lastIndexOf( File.separator ) ) );
	}
	
	public static String getSuffix( File f ) {
		return f.getName().substring( f.getName().lastIndexOf( '.' ) + 1 );
	}
	
	public static String getSuffix( String path ) {
		return path.substring( path.lastIndexOf( '.' ) + 1 );
	}
}