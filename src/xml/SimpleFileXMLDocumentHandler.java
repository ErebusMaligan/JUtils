package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Sep 13, 2014, 4:24:44 PM 
 */
public abstract class SimpleFileXMLDocumentHandler extends BasicXMLDocumentHandler {

	protected String DIR = "DIRECTORY";
	
	protected String FILE = "FILE NAME WITHOUT EXTENSION";
	
	protected String EXT = ".EXTENSION";
	
	protected String READABLE = "SOMETHING HUMAN READABLE FOR FILE TYPE";
	
	protected String ROOT_NODE_NAME = "ROOT";
	
	protected XMLValues val;
	
	public SimpleFileXMLDocumentHandler() {
		
	}
	
	//Subclass constructors should really just duplicate this constructor, not call it
	//set string variables first, then these lines - it's hacky i know
//	public SimpleNodeListXMLDocumentHandler( XMLValues val ) {
//		this.val = val;
//		init( null, EXT, READABLE + " (" + EXT + ")", DIR, ROOT_NODE_NAME );
//	}
	
	public void loadDoc() {
		StringBuilder b = new StringBuilder( FILE + EXT );
		selectedFile = new File( defaultPath + "/" + b.toString() );
		if ( selectedFile.exists() ) {
			String path = selectedFile.getAbsolutePath();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse( path );
				Node root = doc.getFirstChild();
				XMLExpansion ex = new XMLExpansion( ROOT_NODE_NAME, root );
				parseDocument( ex );
			} catch ( ParserConfigurationException | SAXException | IOException e ) {
				e.printStackTrace();
			}
			System.out.println( "Loaded: " + FILE + EXT );
		}
	}
	
	public void createDoc() {
		StringBuilder b = new StringBuilder( FILE + EXT );
		selectedFile = new File( defaultPath + "/" + b.toString() );
		String path = selectedFile.getAbsolutePath();
		try {
			Document doc = XMLFileWriter.createNewDocument( rootNodeName );
			writeDocument( doc );
			XMLFileWriter.writeDocumentToFile( doc, path );
		} catch ( ParserConfigurationException | TransformerException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void parseDocument( XMLExpansion e ) {
		val.loadParamsFromXMLValues( e );
	}

	@Override
	protected void writeDocument( Document doc ) {
		XMLUtils.writeNodeRecursively( doc, val, doc.getFirstChild() );
	}
}