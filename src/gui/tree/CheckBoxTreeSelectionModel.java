package gui.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 17, 2015, 1:58:10 PM 
 */
public class CheckBoxTreeSelectionModel extends DefaultTreeSelectionModel {

	private static final long serialVersionUID = 1L;
	
	private int pSize = 20;
	
	public CheckBoxTreeSelectionModel() {
		this.setSelectionMode( DISCONTIGUOUS_TREE_SELECTION );	
	}
	
	public void setPartitionSize( int pSize ) {
		this.pSize = pSize;
	}
	
	@Override
	public void setSelectionPath( TreePath path ) {
		if ( isPathSelected( path ) ) {
			removeSelectionPath( path );
		} else {
			addSelectionPath( path );		
		}
	}
	
	private List<SwingWorker<Boolean, Void>> performPartitionAction( TreeNode node, PartitionCallable generator ) {
		List<SwingWorker<Boolean, Void>> tList = new ArrayList<>();
		int part = ( node.getChildCount() / pSize ) + node.getChildCount() % pSize > 0 ? 1 : 0;
		for ( int p = 0; p < part; p++ ) {
			int cur = p * pSize;
			tList.add( generator.generateSwingWorker( cur, p == part - 1 ? node.getChildCount() : ( p + 1 ) * pSize ) );
		}
		tList.forEach( ( t ) -> t.execute() );
		return tList;
	}
	
	@FunctionalInterface
	private interface PartitionCallable {
		public SwingWorker<Boolean, Void> generateSwingWorker( int cur, int end );
	}

	private void addSelectionPathInternal( TreePath path ) {
		super.addSelectionPath( path );
		//also select all children
		TreeNode node = (TreeNode)path.getLastPathComponent();
		performPartitionAction( node, ( cur, end ) -> { return new SwingWorker<Boolean, Void>() {
			public Boolean doInBackground() {
				SwingUtilities.invokeLater( () -> {
					for ( int i = cur; i < end; i++ ) {
						final int x = i;
						addSelectionPathInternal( path.pathByAddingChild( (TreeNode)node.getChildAt( x ) ) );
					}
				} );
				return true; //this return value is never used.. but makes the utility partitioning code reusable
			}
		}; } );
	}
	
	@Override
	public void addSelectionPath( TreePath path ) {
		addSelectionPathInternal( path );
		selectParentIfAllChildrenSelected( path );
	}
	
	private void selectParentIfAllChildrenSelected( TreePath path ) {
		//if parent is not selected, but all children now are, select it
		TreePath par = path.getParentPath();
		if ( par != null ) {
			if ( !isPathSelected( par ) ) {
				TreeNode pNode = (TreeNode)par.getLastPathComponent();
				boolean selected = true;
				List<SwingWorker<Boolean, Void>> tList = performPartitionAction( pNode, ( cur, end ) -> { return new SwingWorker<Boolean, Void>() {
					public Boolean doInBackground() {
						boolean sel = true;
						for ( int i = cur; i < end; i++ ) {
							sel = isPathSelected( par.pathByAddingChild( (TreeNode)pNode.getChildAt( i ) ) );
							if ( !sel ) {
								break;
							}
						
						}
						return sel;
					}
				}; } );
				
				for ( SwingWorker<Boolean, Void> t : tList ) {
					try {
						selected = t.get();
						if ( !selected ) {
							break;
						}
					} catch ( InterruptedException | ExecutionException e ) {}
				}
				if ( selected ) {
					addSelectionPath( par );
				}
			}
			changeParentPaths( par );
		}
	}
	
	private void removeSelectionPathInternal( TreePath path, boolean recurse ) {
		if ( recurse ) {
			//also deselect all children
			TreeNode node = (TreeNode)path.getLastPathComponent();
			List<SwingWorker<Boolean, Void>> tList = performPartitionAction( node, ( cur, end ) -> { return new SwingWorker<Boolean, Void>() {
				public Boolean doInBackground() {
					for ( int i = cur; i < end; i++ ) {
						final int x = i;
						SwingUtilities.invokeLater( () -> removeSelectionPathInternal( path.pathByAddingChild( (TreeNode)node.getChildAt( x ) ), true ) );
					}
					return true; //this return value is never used.. but makes the utility partitioning code reusable
				}
			}; } );
			for ( SwingWorker<Boolean, Void> t : tList ) { try { t.get(); } catch ( InterruptedException | ExecutionException e ) {} }//this is just to make the program wait before moving on
		}
		super.removeSelectionPath( path );  //this has to be last, otherwise parent nodes render as partially selected... all children must be deselected FIRST then when deselecting this node it will be properly unchecked
	}
	
	@Override
	public void removeSelectionPath( TreePath path ) {
		removeSelectionPathInternal( path, true );
		deselectParentIfChildUnselected( path );
	}
	
	private void deselectParentIfChildUnselected( TreePath path ) {
		//if parent is selected but any child is not, then deselect parent... but then reselect all other selected children that were auto-unselected by deselecting parent
		TreePath par = path.getParentPath();
		if ( par != null ) {
			if ( isPathSelected( par ) ) {
				removeSelectionPathInternal( par, false );	//deselect parent path
			}
			changeParentPaths( par );
		}
	}
	
	private void changeParentPaths( TreePath path ) {
		try {
			TreePath par = path.getParentPath();
			if ( par != null ) {
				changeParentPaths( par );
			}
			this.fireValueChanged( new TreeSelectionEvent( this, path, false, null, null ) ); //repaint the parent path, otherwise it wasn't becoming fully unchecked when all children were deselected
		} catch ( Exception e ) {
			//this is a swing synchronization problem, just ignore it, seems to have no effect
		}
	}
	
	public boolean isAnyChildSelected( TreePath path ) {
		boolean ret = false;
		TreeNode node = (TreeNode)path.getLastPathComponent();
		for ( int i = 0; i < node.getChildCount(); i++ ) {
			TreePath cPath = path.pathByAddingChild( (TreeNode)node.getChildAt( i ) );
			ret = isPathSelected( cPath );
			if ( ret ) {
				break;
			} else {
				ret = isAnyChildSelected( cPath );
				if ( ret ) {
					break;
				}
			}
		}
		return ret;
	}
}
