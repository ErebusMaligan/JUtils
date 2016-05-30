package gui.textarea;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JTextArea;import javax.swing.SwingUtilities;


/**
 * This stream is primarily intended to be assigned to to System.Out or System.Err or both in order to 
 * capture all output going through those and print it to a registered JTextArea.  
 * The output will continue to print to console as well.
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:02:20 PM 
 */
public class JTextAreaPrintStream extends PrintStream {

	private List<JTextArea> area = new ArrayList<>();

	public JTextAreaPrintStream( OutputStream out ) {
		super( out );
	}
	
	public void registerArea( JTextArea a ) {
		area.add( a );
	}
	
	public void removeArea( JTextArea a ) {
		synchronized( area ) {
			area.remove( a );
		}
	}

	@Override
	public void write( int b ) {
		super.write( b );
		synchronized( area ) {
			area.forEach( a -> {
				a.append( String.valueOf( (char)b ) );
				a.setCaretPosition( a.getDocument().getLength() );
			} );
		}
	}

	@Override
	public void write( byte[] b, int off, int len ) {
		super.write( b, off, len );
		byte[] b2 = new byte[len];
		for ( int i = off; i < len; i++ ) {
			b2[ i - off ] = b[ i ];
		}
		synchronized( area ) {
			area.forEach( a -> {
				try {
					a.append( new String( b2 ) );
					SwingUtilities.invokeLater( () -> {
						a.setCaretPosition( a.getDocument().getLength() );
					} );
				} catch ( Exception e ) {
					//don't care, it only happens when the gui is being shutdown
				}
			} );
		}
	}
}