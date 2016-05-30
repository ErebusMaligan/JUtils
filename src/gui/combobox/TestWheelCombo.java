package gui.combobox;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 16, 2015, 5:20:21 PM 
 */
public class TestWheelCombo {
	
	public static void main( String[] args ) {
		
//		try {
//			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
//		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
//			System.err.println( "Critical JVM Failure!" );
//			e.printStackTrace();
//		}
		JFrame frame = new JFrame();
		frame.setSize( new Dimension( 800, 600 ) );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLayout( new BorderLayout() );
		
	
		JPanel panel = new JPanel( new BorderLayout() );
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for ( int i = 0; i < 50; i++ ) {
			model.addElement( "" + i );
		}
		JComboBox<String> box = new JComboBox<>( model );
		box.addMouseWheelListener( new ScrollableComboBoxListener( box ) );
		box.addActionListener( e -> System.out.println( box.getSelectedIndex() ) );
		panel.add( box, BorderLayout.NORTH );
		frame.add( panel, BorderLayout.CENTER );
		frame.setVisible( true );
	}
}