package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: May 20, 2014, 4:03:56 PM 
 */
public abstract class BusyDialog extends JDialog implements Runnable {
	
	private static final long serialVersionUID = 1L;

	private JProgressBar bar;
	
	public BusyDialog( Frame owner ) {
		this( owner, "Working..." );
	}
	
	public BusyDialog( Frame owner, String title ) {
		super( owner, title, true );
		bar = getProgressBar();
		this.setLocationRelativeTo( owner );
		this.setSize( 300, 75 );
		GU.center( this );
		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		this.setLayout( new BorderLayout() );
		bar.setIndeterminate( true );
		int b = 5;
		bar.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( b ,b, b, b ), BorderFactory.createBevelBorder( BevelBorder.LOWERED ) ) );
		this.add( bar, BorderLayout.CENTER );
		setVisible( true );
	}

	public abstract void executeTask();
	
	/**
	 * This provides the actual JProgressBar used - made this available so it can be overridden with a bar with a custom look and feel if desired
	 * 
	 * @return A JProgressBar that indicates busy status
	 */
	public JProgressBar getProgressBar() {
		return new JProgressBar();
	}
	
	@Override
	public void run() {
		executeTask();
		try {
			SwingUtilities.invokeAndWait( new Runnable() { public void run() { setVisible( false ); } } );
		} catch ( InvocationTargetException | InterruptedException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void setVisible( boolean visible ) {
		if ( visible == true ) {
			new Thread( this ).start();
		} else {
			bar.setIndeterminate( false );
		}
		super.setVisible( visible );
	}
}