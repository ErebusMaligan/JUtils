package statics;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 2, 2015, 3:10:32 PM 
 */
public class LambdaUtils {
	
	public static <T> Supplier<T> handleException( Callable<T> callable ) {
		return () -> {
			try {
				return callable.call();
			} catch ( RuntimeException e ) {
				throw e;
			} catch ( Exception e ) {
				throw new RuntimeException( e );
			}
		};
	}

}
