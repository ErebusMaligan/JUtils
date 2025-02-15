package listeners;

import java.util.Vector;

public class BasicObservable {

	private Vector<BasicObserver> obs = new Vector<>();

	public synchronized void addObserver( BasicObserver o ) {
		if ( o == null )
			throw new NullPointerException();
		if ( !obs.contains( o ) ) {
			obs.addElement( o );
		}
	}

	public synchronized void deleteObserver( BasicObserver o ) {
		obs.removeElement( o );
	}

	public void notifyObservers() {
		notifyObservers( null );
	}

	public void notifyObservers( Object arg ) {
		/*
		 * a temporary array buffer, used as a snapshot of the state of
		 * current Observers.
		 */
		Object[] arrLocal;

		synchronized ( this ) {
			arrLocal = obs.toArray();
		}

		for ( int i = arrLocal.length - 1; i >= 0; i-- )
			( (BasicObserver)arrLocal[ i ] ).update( this, arg );
	}
}
