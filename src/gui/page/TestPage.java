package gui.page;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 30, 2015, 8:54:41 PM 
 */
public class TestPage {

	public static void main( String args[] ) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}

		JFrame frame = new JFrame();
		frame.setSize( new Dimension( 800, 600 ) );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLayout( new BorderLayout() );

		ArrayList<String> testData = new ArrayList<>();
		for ( int i = 0; i < 22000; i++ ) {
			testData.add( String.valueOf( i ) );
		}
		PagedDataViewer<String> viewer = new PagedDataViewer<>( testData );
//		PagedDataViewer<String> viewer = new PagedDataViewer<>();
		frame.add( viewer, BorderLayout.CENTER );
		viewer.getPageControlModel().setPerPage( 10 );
		viewer.getPageControlModel().setMaxPerPage( 500 );
		frame.setVisible( true );
//		viewer.setData( testData );
	}
}