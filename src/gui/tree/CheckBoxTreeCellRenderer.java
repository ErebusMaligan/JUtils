package gui.tree;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author Daniel J. Rivers 2015
 *
 * Created: Apr 16, 2015, 4:51:48 PM
 */
public class CheckBoxTreeCellRenderer extends JPanel implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;
	private TreeCellRenderer original;
	private JCheckBox checkBox = new JCheckBox();
	private CheckBoxTreeSelectionModel model;

	public CheckBoxTreeCellRenderer( TreeCellRenderer original, CheckBoxTreeSelectionModel model ) {
		this.original = original;
		this.model = model;
		setLayout( new BorderLayout() );
		setOpaque( false );
		checkBox.setOpaque( false );
	}

	@Override
	public Component getTreeCellRendererComponent( JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus ) {
		Component renderer = original.getTreeCellRendererComponent( tree, value, selected, expanded, leaf, row, hasFocus );
		checkBox.getModel().setPressed( false );
		checkBox.getModel().setArmed( false );
		checkBox.setSelected( selected );
		if ( !leaf && !selected ) {
			TreePath path = tree.getPathForRow( row );
			if ( path != null && model.isAnyChildSelected( path ) ) {
				checkBox.setSelected( true );
				checkBox.getModel().setPressed( true );
				checkBox.getModel().setArmed( true );
			}
		}
		add( checkBox, BorderLayout.WEST );
		add( renderer, BorderLayout.CENTER );
		return this;
	}

}
