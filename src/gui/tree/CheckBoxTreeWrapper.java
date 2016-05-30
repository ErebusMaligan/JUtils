package gui.tree;

import javax.swing.JTree;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 17, 2015, 4:55:10 PM 
 */
public class CheckBoxTreeWrapper {
	
	public static void wrapJTree( JTree tree ) {
		CheckBoxTreeSelectionModel model = new CheckBoxTreeSelectionModel();
		tree.setSelectionModel( model );
		tree.setCellRenderer( new CheckBoxTreeCellRenderer( tree.getCellRenderer(), model ) );
	}
}