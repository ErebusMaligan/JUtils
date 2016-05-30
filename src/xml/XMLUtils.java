package xml;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Nov 6, 2013, 1:52:12 PM 
 */
public class XMLUtils {
	
	public static final String SPACER = "_";
	
	public static String sanitize( String input ) {
		return input.replaceAll( "\\s", SPACER ).replaceAll( "\\+", "P" );
	}
	
	/**
	 * For a given node, retrieve and write all of it's values and sub-values
	 * 
	 * @param doc XML document to write to
	 * @param val batch of XML Values to write
	 * @param parent the parent node to write this set of values under
	 */
	public static void writeNodeRecursively( Document doc, XMLValues val, Node parent ) {
		Map<String, Map<String, String[]>> maps = val.saveParamsAsXML();
		if ( maps != null ) {

			XMLValuesWithAttributes xmlat = null;
			Map<String, Map<String, String>[]> attr = null;
			if ( val instanceof XMLValuesWithAttributes ) {
				xmlat = (XMLValuesWithAttributes)val;
				attr = xmlat.saveNodeAttributes();
			}
			
			Element e = null;
			for ( String s : maps.keySet() ) {
				
				if ( xmlat == null ) { //the enclosing node
					e = XMLFileWriter.createChildNode( doc, parent, s );
				} else {
					e = XMLFileWriter.createChildNodeWithAttributes( doc, parent, s, xmlat.saveAttributes() ); 
				}
				
				Map<String, String[]> values = maps.get( s ); //value tags of that node
				if ( values != null ) {
					for ( String k : values.keySet() ) {
						String[] vals = values.get( k );
						Map<String, String>[] at = null;
						if ( attr != null ) {
							 at = attr.get( k );
						}
						if ( vals != null ) {
							for ( int i = 0; i < vals.length; i++ ) {
								String m = vals[ i ];
								if ( at == null ) {
									XMLFileWriter.createChildNode( doc, e, k, m == null ? "NULL" : m );
								} else {
									XMLFileWriter.createChildNodeWithAttributes( doc, e, k, m == null ? "NULL" : m, at[ i ] );
								}
							}
							
						}
					}
				}
				
				if ( val.getChildNodes() != null ) {  //child enclosing nodes
					for ( XMLValues w : val.getChildNodes() ) {
						writeNodeRecursively( doc, w, e );
					}
				}
			}
		}
	}
	
	public static Map<String, String> convertAttributes( NamedNodeMap in ) {
		Map<String, String> ret = null;
		if ( in != null ) {
			ret = new HashMap<String, String>();
			for ( int i = 0; i < in.getLength(); i++ ) {
				Node n = in.item( i );
				ret.put( n.getNodeName(), n.getTextContent());
			}
		}
		return ret;
	}
	
	/**
	 * This is a debug method for printing out a values map passed back by an XMLValues component
	 * 
	 * Can be removed at a later date
	 * 
	 * @param values Map of values keyed on a component name
	 */
	public static void printXMLMap( Map<String, Map<String, String>> values ) {
		System.out.println( "***** MAP START *****" );
		for ( String s : values.keySet() ) {
			System.out.println( s );
			for ( String p : values.get( s ).keySet() ) {
				System.out.println( "( " + p + ", " + values.get( s ).get( p ) + " )" );
			}
		}
		System.out.println( "***** MAP END *****" );
	}
	
	/**
	 * Wraps an xml doc as a CDATA string
	 * 
	 * @param doc XML document to wrap
	 * @return CDATA string that represents the xml doc
	 * @throws TransformerException ?
	 * @throws TransformerFactoryConfigurationError ?
	 */
	public static String toCData( Document doc ) throws TransformerException, TransformerFactoryConfigurationError {
		String ret = "<![CDATA[";
		ret += toString( doc );
		ret += "]]>";
		return ret;
	}
	
	public static String toCData( String in ) {
		String ret = "<![CDATA[";
		ret += in;
		ret += "]]>";
		return ret;
	}
	
	public static String unescape( String eXML ) {
		return eXML.replace( "&apos;", "'" ).replace( "&quot;", "\"" ).replace( "&gt;", ">" ).replace( "&lt;", "<" ).replace( "&amp;", "&" );
	}
	
	public static String escape( String xml ) {
		return xml.replace( "&", "&amp;" ).replace( "'", "&apos;" ).replace( "\"", "&quot;" ).replace( ">", "&gt;" ).replace( "<", "&lt;" );
	}
	
	public static String stripFirstTag( String input ) {
		return input.substring( input.indexOf( '<', input.indexOf( '<' ) + 1 ) );
	}
	
	public static String getFirstTag( String input ) {
		return input.substring( input.indexOf( '<' ) + 1, input.indexOf( '>' ) );
	}
	
	public static String wrapInTag( String input, String tag ) {
		return "<" + tag + ">" + input + "</" + tag + ">";
	}
	
	public static String wrapInTag( String input, String tag, Map<String, String> attr ) {
		String ret;
		if ( attr == null ) {
			ret = wrapInTag( input, tag );
		} else {
			String first = "<" + tag;
			for ( String s : attr.keySet() ) {
				first += " " + s + "=\"" + attr.get( s ) +"\"";
			}
			ret = first + ">" + input + "</" + tag + ">";
		}
		return ret;
	}
	
	public static String cmdEscape( String xml ) {
		return xml.replace( "&", "\\&" ).replace( "'", "\\'" ).replace( "\"", "\\\"" ).replace( ">", "\\>" ).replace( "<", "\\<" ).replace( "(", "\\(" ).replace( ")", "\\)" );
	}
	
	public static String cmdUnescape( String eXML ) {
		return eXML.replace( "\\&", "&" ).replace( "\\'", "'" ).replace( "\\\"", "\"" ).replace( "\\>", ">" ).replace( "\\<", "<" ).replace( "\\(", "(" ).replace( "\\)", ")" );
	}
	
	public static String toString( Document doc ) throws TransformerException, TransformerFactoryConfigurationError {
		DOMSource domSource = new DOMSource( doc );
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult( writer );
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "2" );
		transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		transformer.transform( domSource, result );
		writer.flush();
		return writer.toString();
	}
	
	public static String toStringNoFormat( Document doc ) throws TransformerException, TransformerFactoryConfigurationError {
		DOMSource domSource = new DOMSource( doc );
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult( writer );
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform( domSource, result );
		writer.flush();
		return writer.toString();
	}
}