package gui.entry;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import statics.GU;
import statics.GUIUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 1, 2013, 4:08:58 PM
 */
public class ListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList<String> list = new JList<String>( new DefaultListModel<String>() );
	
	private JTextField f;
	
	public ListPanel( String title ) {
		this( title, true );
	}
	
	public ListPanel( String title, boolean text ) {
		list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setLayout( new BorderLayout() );
		JScrollPane scroll = new JScrollPane( list );
		scroll.setBorder( BorderFactory.createTitledBorder( scroll.getBorder(), title, TitledBorder.CENTER, TitledBorder.ABOVE_TOP ) );
		this.add( scroll, BorderLayout.CENTER );
		
		JPanel south = new JPanel();
		GUIUtils.spacer( south );
		south.setLayout( new BoxLayout( south, BoxLayout.Y_AXIS ) );
		
		if ( text ) {
			f = new JTextField();
			GUIUtils.setSizes( f, GUIUtils.FIELD );
			south.add( f );
			GUIUtils.spacer( south );
		}
		
		JPanel buttons = new JPanel();
		buttons.setLayout( new BoxLayout( buttons, BoxLayout.X_AXIS ) );
		JButton add = new JButton( "Add" );
		JButton remove = new JButton( "Remove" );
		GU.hp( buttons, add, remove );
		south.add( buttons );
		this.add( south, BorderLayout.SOUTH );
		add.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				add();
			}
		} );
		remove.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				remove();
			}
		} );
	}
	
	public void add() {
		if ( !f.getText().equals( "" ) ) {
			( (DefaultListModel<String>)list.getModel() ).addElement( f.getText() );
			f.setText( "" );
		}
	}

	
	public void remove() {
		( (DefaultListModel<String>)list.getModel() ).remove( list.getSelectedIndex() );
	}
	
	public List<String> getAllItems() {
		ArrayList<String> ret = new ArrayList<String>();
		for ( int i = 0; i < list.getModel().getSize(); i++ ) {
			ret.add( list.getModel().getElementAt( i ) );
		}
		return ret;
	}
	
	public void addItem( String s ) {
		( (DefaultListModel<String>)list.getModel() ).addElement( s );
	}
	
	public void addItems( String[] items ) {
		for ( String s : items ) {
			( (DefaultListModel<String>)list.getModel() ).addElement( s );
		}
	}
}