package statics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jun 15, 2016, 1:50:51 PM
 */
public class SerializationUtils {

	public static byte[] serialize( Object o ) throws IOException {
		byte[] ret = null;
		try ( ByteArrayOutputStream bos = new ByteArrayOutputStream() ) {
			try ( ObjectOutput out = new ObjectOutputStream( bos ) ) {
				out.writeObject( o );
				out.flush();
				ret = bos.toByteArray();
			}
		}
		return ret;
	}

	public static Object deserialize( byte[] b ) throws IOException, ClassNotFoundException {
		Object ret = null;
		try ( ByteArrayInputStream bis = new ByteArrayInputStream( b ) ) {
			try ( ObjectInput in = new ObjectInputStream( bis ) ) {
				ret = in.readObject();
			}
		}
		return ret;
	}
}