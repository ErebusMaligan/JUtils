package xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Feb 16, 2014, 9:19:58 PM 
 */
public class SOAPUtils {
	
	public static Document toXMLDocument( SOAPMessage soapMsg ) throws TransformerConfigurationException, TransformerException, SOAPException, IOException {
		Source src = soapMsg.getSOAPPart().getContent();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMResult result = new DOMResult();
		transformer.transform( src, result );
		return (Document)result.getNode();
	}
	
	public static String toString( SOAPMessage soapMsg ) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, SOAPException, IOException {
		return XMLUtils.toString( toXMLDocument( soapMsg ) );
	}
	
	public static SOAPMessage convertToUTF8( SOAPMessage sm ) throws UnsupportedEncodingException, TransformerConfigurationException, IOException, SOAPException, TransformerException, TransformerFactoryConfigurationError {
		return MessageFactory.newInstance().createMessage( null, new ByteArrayInputStream( SOAPUtils.toString( sm ).getBytes( "UTF-8" ) ) );
	}
}