package statics;

import java.util.Arrays;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 29, 2016, 6:28:33 PM
 */
public class ArrayUtils {

	public static <T> T[] concat( T[] first, T[] second ) {
		T[] result = Arrays.copyOf( first, first.length + second.length );
		System.arraycopy( second, 0, result, first.length, second.length );
		return result;
	}
	
	//this one was copied from stack overflow
	@SafeVarargs
	public static <T> T[] concatAll( T[] first, T[]... rest ) {
		int totalLength = first.length;
		for ( T[] array : rest ) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf( first, totalLength );
		int offset = first.length;
		for ( T[] array : rest ) {
			System.arraycopy( array, 0, result, offset, array.length );
			offset += array.length;
		}
		return result;
	}
	
	public static <T> boolean contains( T[] arr, T compare ) {
		boolean ret = false;
		for ( T item : arr ) {
			if ( item.equals( compare ) ) {
				ret = true;
				break;
			}
		}
		return ret;
	}
}