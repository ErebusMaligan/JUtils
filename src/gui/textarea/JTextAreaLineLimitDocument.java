package gui.textarea;

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Limits the number of lines that will appear in a JTextArea before the first line is removed
 * creating a circular buffer in essence.
 * 
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 16, 2015, 12:12:43 AM 
 */
public class JTextAreaLineLimitDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;

	/**
	 * Number of lines to allow in the text area
	 */
	private int lineLimit;
	
	/**
	 * JTextarea this document is assigned to
	 */
	private JTextArea area;
	
	/**
	 * Create a document assigned to the given JTextArea with the given line limit
	 * 
	 * @param area JTextArea this document is assigned to
	 * @param lineLimit Number of lines to allow in the text area
	 */
	public JTextAreaLineLimitDocument( JTextArea area, int lineLimit ) {
		this.area = area;
		this.lineLimit = lineLimit;
	}
	
	@Override
	public void insertString( int offs, String str, AttributeSet a ) throws BadLocationException {
		super.insertString( offs, str, a );
		if ( area.getLineCount() > lineLimit ) {
			remove( 0, area.getLineEndOffset( 0 ) );
		}
	}
}