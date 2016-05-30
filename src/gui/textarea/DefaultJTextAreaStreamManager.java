package gui.textarea;

import javax.swing.JTextArea;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:17:20 PM 
 */
public class DefaultJTextAreaStreamManager {

	private JTextAreaPrintStream out = new JTextAreaPrintStream( System.out );
	
	private JTextAreaPrintStream err = new JTextAreaPrintStream( System.err );
	
	public DefaultJTextAreaStreamManager() {
		System.setOut( out );
		System.setErr( err );
	}
	
	public void registerArea( JTextArea a ) {
		out.registerArea( a );
		err.registerArea( a );
	}
	
	public void removeArea( JTextArea a ) {
		out.removeArea( a );
		err.removeArea( a );
	}	
}