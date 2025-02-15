package plugin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 5:22:33 PM
 */
public class PluginJarLoader {
	public static void loadPluginJar( String jar ) throws IOException, ClassNotFoundException {
		try {
			Method method = URLClassLoader.class.getDeclaredMethod( "addURL", new Class[] { URL.class } );
			method.setAccessible( true );
			method.invoke( (URLClassLoader)ClassLoader.getSystemClassLoader(), new Object[] { URI.create( "jar:file:" + jar + "!/" ).toURL() } );
		} catch ( Throwable t ) {
			t.printStackTrace();
			throw new IOException( "Error, could not add URL to system classloader" );
		}
	}
}