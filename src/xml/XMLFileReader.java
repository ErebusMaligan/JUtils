package xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 3, 2013, 2:38:45 PM 
 */
public class XMLFileReader {

	public static Document readExistingFile( String filePath ) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.parse( filePath );
	}
	
	public static Document readFromWellFormedString( String input ) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.parse( new ByteArrayInputStream( input.getBytes() ) );
	}
	
	public static Document readFromString( String input ) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.parse( new ByteArrayInputStream( ( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + input ).getBytes() ) );
	}
}