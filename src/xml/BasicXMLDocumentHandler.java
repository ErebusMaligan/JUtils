package xml;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Nov 8, 2013, 11:57:13 PM 
 */
public abstract class BasicXMLDocumentHandler {

	protected Component parent;
	
	protected String extension;
	
	protected String rootNodeName;
	
	protected String defaultPath;
	
	protected String fileDescription;
	
	protected File selectedFile;
	
	public BasicXMLDocumentHandler( Component parent, String extension, String fileDescription, String defaultPath, String rootNodeName ) {
		init( parent, extension, fileDescription, defaultPath, rootNodeName );
	}
	
	public BasicXMLDocumentHandler() {
		//this is only to be used in subclasses, so they can call init - sort of a hack to get around not being
		//able to init class constants first
		//this constructor isn't really valid if you don't call init as the last line of the subclass constructor
	}
	
	public void init( Component parent, String extension, String fileDescription, String defaultPath, String rootNodeName ) {
		this.parent = parent;
		this.extension = extension;
		this.fileDescription = fileDescription;
		this.defaultPath = defaultPath;
		this.rootNodeName = rootNodeName;
	}
	
	/**
	 * Responsible for prompting the user for information, creating the xml config document and beginning the serialization process. 
	 */
	public void createDoc() {
		JFileChooser fc = getFileChooser();
		if ( JFileChooser.APPROVE_OPTION == fc.showSaveDialog( parent ) ) {
			String path = fc.getSelectedFile().getAbsolutePath();
			if ( !path.endsWith( extension ) ) {
				path += extension;
			}
			try {
				Document doc = XMLFileWriter.createNewDocument( rootNodeName );
				writeDocument( doc );
				XMLFileWriter.writeDocumentToFile( doc, path );
			} catch ( ParserConfigurationException | TransformerException e ) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Prompts a user to select a window configuration and then make a call to parse the resulting XML document
	 * @param f file to load
	 */
	public void loadDoc( File f ) {
		if ( f == null || !f.exists() ) {
			JFileChooser fc = getFileChooser();
			if ( JFileChooser.APPROVE_OPTION == fc.showOpenDialog( parent ) ) {
				selectedFile = fc.getSelectedFile();
			} else {
				selectedFile = null;
			}
		} else {
			selectedFile = f;
		}
		if ( selectedFile != null ) {
			String path = selectedFile.getAbsolutePath();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse( path );
				Node root = doc.getFirstChild();
				XMLExpansion ex = new XMLExpansion( "ROOT", root );
				parseDocument( ex );
			} catch ( ParserConfigurationException | SAXException | IOException e ) {
				e.printStackTrace();
			}
		}
	}
	
	protected JFileChooser getFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled( false );
		fc.setCurrentDirectory(  new File( defaultPath ) );
		fc.setFileFilter( new FileNameExtensionFilter( fileDescription, extension.substring( 1 ) ) );
		return fc;
	}
	
	/**
	 * Parses the resulting XML document and recreates top level componenets as needed
	 * 
	 * @param ex XMLExpansion represeenting an XML Document
	 */
	protected abstract void parseDocument( XMLExpansion ex );
	
	/**
	 * Tells all top level components to write their configuration information to the XML document provided
	 * 
	 * @param doc XML document provided.
	 */
	protected abstract void writeDocument( Document doc );
	
}
