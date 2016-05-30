package process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 5:24:54 PM
 */
public class SimpleProcess {

	protected ProcessBuilder b;
	
	protected Process p;
	
	protected boolean showOutput = false;
	
	public SimpleProcess( String...commands ) {
		this( true, commands );
	}
	
	public SimpleProcess( boolean showOutput, String...commands ) {
		b = new ProcessBuilder( commands );
		b.redirectErrorStream( true );
		this.showOutput = showOutput;
	}
	
	public void start() throws IOException {
		p = b.start();
		if ( showOutput ) {
			new Thread( () -> {
				BufferedReader reader = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
				String line;
				try {
					while ( ( line = reader.readLine() ) != null ) {
						System.out.println( line );
					}
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			} ).start();
		}
	}	
}