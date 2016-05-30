package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 9, 2016, 2:32:44 AM 
 */
public class RefreshBuffer {
	
	private BlockingQueue<Thread> buffer;
	
	private volatile boolean running = true;
	
	private InterruptedException exception = null;
	
	private volatile Thread updateThread;
	
	public RefreshBuffer() {
		 buffer = new LinkedBlockingQueue<>();
	}
	
	public RefreshBuffer( int capacity ) {
		buffer = new LinkedBlockingQueue<>( capacity );
	}
	
	public void start() {
		if ( updateThread == null ) {
			exception = null;
			updateThread = new Thread() {
				public void run() {
					while ( running ) {
						try {
							Thread element = buffer.take();
							SwingUtilities.invokeLater( element );
//							element.start();
							element.join();
						} catch ( InterruptedException e ) {
							exception = e;
							running = false;
							updateThread = null;
						}
					}
					updateThread = null;
				}
			};
			updateThread.start();
		}
	}
	
	public boolean enqueue( Thread t ) throws InterruptedException {
		if ( exception != null ) {
			throw exception;
		}
		return buffer.offer( t );
	}
	
	public void stop() {
		running = false;
		new Thread( () -> {
			try {
				Thread.sleep( 1000 );
			} catch ( Exception e ) {
				if ( updateThread != null ) {
					updateThread.interrupt();
				}
			}
			if ( updateThread != null ) {
				updateThread.interrupt();
			}
		} ).start();
	}
}