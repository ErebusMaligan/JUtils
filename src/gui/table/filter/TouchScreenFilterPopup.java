package gui.table.filter;

import gui.table.icons.TableIconConstants;
import gui.table.icons.TableIconLoader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import statics.GU;

/**
 * Displays the dialog for typing in a filter string
 * 
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Feb 11, 2013, 10:37:56 AM 
 */
public class TouchScreenFilterPopup extends JDialog implements FilterDialog {

	private static final long serialVersionUID = 1L;
	
	private JTextField text = new JTextField();
	
	private String value;
	
	public TouchScreenFilterPopup() {
		this.setSize( new Dimension( 150, 75 ) );
		this.setModal( true );
		this.setLayout( new BorderLayout() );
		this.add( text, BorderLayout.CENTER );
		text.addKeyListener( new KeyAdapter() {
			public void keyPressed( KeyEvent e ) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
					ok();
				}
			}
		} );
		this.add( createButtonPanel(), BorderLayout.SOUTH );
		this.setUndecorated( true );
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout( panel, BoxLayout.X_AXIS ) );
		JButton ok = new JButton( TableIconLoader.getInstance().getIcon( TableIconConstants.OK ) );
		ok.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				ok();
			}
		} );
		JButton cancel = new JButton( TableIconLoader.getInstance().getIcon( TableIconConstants.CLOSE ) );
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cancel();
			}
		} );
		panel.add( ok );
		GU.spacer( panel, new Dimension( 43, 10 ) );
		panel.add( cancel );
		return panel;
	}
	
	private void ok() {
		value = text.getText();
		this.dispose();
	}
	
	private void cancel() {
		value = null;
		this.dispose();
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void display( Point location ) {
		if ( location != null ) {
			this.setLocation( location );
		}
		this.setVisible( true );
	}
}