package gui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Nov 9, 2013, 2:18:50 AM 
 */
public class OKCancelDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	public static final int OK = 1;
	
	public static final int CANCEL = 0;
	
	protected int result;
	
	protected JButton ok = new JButton( "Ok" );
	
	protected JButton cancel = new JButton( "Cancel" );
	
	public OKCancelDialog( Frame owner, String title, boolean modal ) {
		super( owner, title, modal );
		this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
	}
	
	protected JPanel getButtonPanel() {
		JPanel p = new JPanel();
		GU.sizes( ok, GU.FIELD );
		GU.sizes( cancel, GU.FIELD );
		p.add( ok );
		GU.spacer( p );
		p.add( cancel );
		ok.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				ok();
			}
		} );
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				cancel();
			}
		} );
		return p;
	}
	
	public void ok() {
		this.result = OK;
		this.dispose();
	}
	
	public void cancel() {
		this.result = CANCEL;
		this.dispose();
	}
	
	public int getResult() {
		return result;
	}
}