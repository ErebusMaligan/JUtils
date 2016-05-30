package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Sep 3, 2013, 2:33:24 PM 
 */
public class XMLFileWriter {
	
	public static Document createNewDocument( String rootName ) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement( rootName );
		doc.appendChild( rootElement );
		return doc;
	}
	
	public static void writeDocumentToFile( Document doc, String filePath ) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "2" );
		transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		DOMSource source = new DOMSource( doc );
		StreamResult result = new StreamResult( new File( filePath ) );
		transformer.transform( source, result );
	}
	
	public static Element createChildNodeWithAttributes( Document doc, Node parent, String name, String value, Map<String, String> attr ) {
		Element e = createChildNode( doc, parent, name, value );
		if ( attr != null ) {
			for ( String s : attr.keySet() ) {
				e.setAttribute( s, attr.get( s ) );			
			}
		}
		return e;
	}
	
	public static Element createChildNodeWithAttributes( Document doc, Node parent, String name, Map<String, String> attr ) {
		return createChildNodeWithAttributes( doc, parent, name, null, attr );
	}
	
	public static Element createChildNode( Document doc, Node parent, String name, String value ) {
		Element e = createChildNode( doc, parent, name );
		if ( value != null ) {
			e.appendChild( doc.createTextNode( value ) );			
		}
		return e;
	}
	
	public static Element createChildNode( Document doc, Node parent, String name ) {
		Element e = doc.createElement( name );
		parent.appendChild( e );
		return e;
	}
	
	public static void appendSimpleNodeGroup( Document doc, Node parent, Map<String, String> children, String[] order ) {
		if ( children != null ) {
			Iterable<String> set = (Iterable<String>)( order != null ? new ArrayList<String>( Arrays.asList( order ) ) : children.keySet() );
			for ( String s : set ) {
				String v = children.get( s );
				if ( v != null ) {
					XMLFileWriter.createChildNode( doc, parent, s, v );
				} else {
					XMLFileWriter.createChildNode( doc, parent, s );
				}
			}
		}
	}
	
	public static Element createSimpleNodeGroup( Document doc, Node parent, String name, Map<String, String> children ) {
		return createSimpleNodeGroup( doc, parent, name, children, null );
	}
	
	public static Element createSimpleNodeGroup( Document doc, Node parent, String name, Map<String, String> children, String[] order ) {
		Element e = doc.createElement( name );
		parent.appendChild( e );
		appendSimpleNodeGroup( doc, e, children, order );
		return e;
	}
}