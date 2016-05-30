package gui.tree;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 16, 2015, 5:20:21 PM 
 */
public class TestTree {
	
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
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode( "root" );
		for ( int i = 0; i < 20; i++ ) {
			DefaultMutableTreeNode n = new DefaultMutableTreeNode( "" + i );
			for ( int x = 0; x < 5; x++ ) {
				DefaultMutableTreeNode z = new DefaultMutableTreeNode( i + " - " + x );
				n.add( z );
			}
			root.add( n );
		}
		JTree test = new JTree( root );
		test.setLargeModel( true );
		CheckBoxTreeWrapper.wrapJTree( test );
		( (CheckBoxTreeSelectionModel)test.getSelectionModel() ).setPartitionSize( 6000 );
		JPanel panel = new JPanel( new BorderLayout() );
		panel.add( new JScrollPane( test ), BorderLayout.CENTER );
		frame.add( panel, BorderLayout.CENTER );
		frame.setVisible( true );
		
		JButton print = new JButton( "Print selected" );
		print.addActionListener( e -> { 
			for ( TreePath p :test.getSelectionModel().getSelectionPaths() ) {
				System.out.println( ( (DefaultMutableTreeNode)p.getLastPathComponent() ).getUserObject().toString() );
			}
			System.out.println( "-----------\n" );
		} );
		frame.add( print, BorderLayout.SOUTH );
	}
}