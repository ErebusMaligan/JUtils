package xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Aug 23, 2014, 1:17:25 AM 
 */
public class XMLValidator {

	public static boolean validate( String xml, String xsd ) throws SAXException {
		boolean ret = false;
		Source xmlFile = new StreamSource( new ByteArrayInputStream( xml.getBytes() ) );
		Source xsdFile = new StreamSource( new ByteArrayInputStream( xsd.getBytes() ) );
		Schema schema = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI ).newSchema( xsdFile );
		try {
			schema.newValidator().validate( xmlFile );
			ret = true;
		} catch ( SAXException | IOException e ) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean validate( String xml, File xsd ) throws SAXException {
		boolean ret = false;
		Source xmlFile = new StreamSource( new ByteArrayInputStream( xml.getBytes() ) );
		Source xsdFile = new StreamSource( xsd );
		Schema schema = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI ).newSchema( xsdFile );
		try {
			schema.newValidator().validate( xmlFile );
			ret = true;
		} catch ( SAXException | IOException e ) {
			ret = false;
		}
		return ret;
	}	
}