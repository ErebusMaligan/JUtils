package xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import statics.ArrayUtils;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Nov 1, 2013, 12:51:29 AM 
 */
public class XMLExpansion {
	
	private List<String> ordered = new ArrayList<String>();
	
	private Map<String, String[]> values;
	
	private Map<String, String> attributes;
	
	private Map<String, Map<String, String>[]> nodeAttributes = new HashMap<String, Map<String, String>[]>();
	
	private String name;
	
	private List<XMLExpansion> children;
	
	public XMLExpansion( String name, Map<String, String[]> values, List<XMLExpansion> children ) {
		this.name = name;
		this.values = values;
		this.children = children;
		if ( values != null ) {
			for ( String s : values.keySet() ) {
				if ( !ordered.contains( s ) ) {
					ordered.add( s );
				}
			}
		}
		if ( children != null ) {
			for ( XMLExpansion c : children ) {
				if ( !ordered.contains( c.getName() ) ) {
					ordered.add( c.getName() );
				}
			}
		}
	}
	
	public XMLExpansion( String name, Map<String, String[]> values, List<XMLExpansion> children, List<String> ordered ) {
		this( name, values, children );
		if ( ordered != null ) {
			this.ordered = ordered;
		}
	}
	
	public static XMLExpansion fromXMLValues( XMLValues val, List<String> ordered ) {
		List<XMLExpansion> child = new ArrayList<XMLExpansion>();
		if ( val.getChildNodes() != null ) {
			for ( XMLValues v : val.getChildNodes() ) {
				child.add( fromXMLValues( v ) );
			}
		}
		Map<String, Map<String, String[]>> map = val.saveParamsAsXML();
		XMLExpansion e = null;
		for ( String s : map.keySet() ) {  //no good way to pull only 1.. but there should only be 1 key
			e = new XMLExpansion( s, map.get( s ), child, ordered );
			
			if ( val instanceof XMLValuesWithAttributes ) { //attribute only stuff
				XMLValuesWithAttributes xmlat = (XMLValuesWithAttributes)val;
				e.setAttributes( xmlat.saveAttributes() );
				Map<String, Map<String, String>[]> at = xmlat.saveNodeAttributes();
				if ( at != null ) {
					for ( String k : at.keySet() ) {
						e.setNodeAttributes( k, at.get( k ) );
					}
				}
			}
			
		}
		return e;
	}
	
	public static XMLExpansion fromXMLValues( XMLValues val ) {  //WARNING: this is predicated on the fact that map returned from val.saveParamsAsXML only has one key in it... which really should always be the case
		return fromXMLValues( val, null );
	}
	
	public XMLExpansion( String name, Node root ) {
		this.name = name;
		attributes = XMLUtils.convertAttributes( root.getAttributes() );
		NodeList nList = root.getChildNodes();
		children = new ArrayList<XMLExpansion>();
		values = new HashMap<String, String[]>();
		if ( nList.getLength() > 0 ) {
			Map<String, List<Map<String, String>>> attr = new HashMap<>();
			for ( int i = 0; i < nList.getLength(); i++ ) {
				Node n = (Node)nList.item( i );
				if ( n instanceof Element ) {
					String nn = n.getNodeName();
					if ( n.hasChildNodes() && n.getChildNodes().getLength() == 1 && !n.getChildNodes().item( 0 ).hasChildNodes() ) {
						ArrayList<String> l = new ArrayList<>();
						boolean addOrdered = true;
						if ( values.containsKey( nn ) ) {
							l = new ArrayList<>( Arrays.asList( values.get( nn ) ) );
							addOrdered = false;
						}
						
						List<Map<String, String>> atl = new ArrayList<Map<String, String>>();
						if ( attr.containsKey( nn ) ) {
							atl = attr.get( nn );
						}
						if ( n.getAttributes() != null ) {
							atl.add( XMLUtils.convertAttributes( n.getAttributes() ) );
							attr.put( nn, atl );
						}
						
						l.add( n.getTextContent() );
						values.put( nn, l.toArray( new String[] {} ) );
						if ( addOrdered ) {
							ordered.add( nn );
						}
					} else {
						if ( !ordered.contains( nn ) ) {  //this sort of makes an assumption that if there are multiple child nodes with the same name, they will be grouped together
							ordered.add( nn );
						}
						children.add( new XMLExpansion( nn, n ) );
					}
				}
			}
			for ( String s : attr.keySet() ) {
				List<Map<String, String>> l = attr.get( s );
				@SuppressWarnings("unchecked")
				Map<String, String>[] m = new Map[ l.size() ];
				setNodeAttributes( s, l.toArray( m ) );
			}
		}
	}
	
	public void setOrdered( List<String> ordered ) {
		this.ordered = ordered;
	}
	
	public void setAttributes( Map<String, String> attributes ) {
		this.attributes = attributes;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	
	public void setNodeAttributes( String node, Map<String, String>[] attributes ) {
		this.nodeAttributes.put( node, attributes );
	}
	
	public Map<String, String> getNodeAttributes( String node, int index ) {
		Map<String, String> ret = null;
		Map<String,String>[] map = nodeAttributes.get( node );
		if ( map != null && map.length > index ) {
			if ( map[ index ] != null ) {
				ret = map[ index ];
			}
		}
		return ret;
	}
	
	//convenience method
	public String get( String key ) {
		return values.get( key ) == null ? null : values.get( key )[ 0 ];
	}
	
	public String[] getAll( String key ) {
		return values.get( key );
	}
	
	public void modifyValues( String[] keys, XMLValueModifier mod, boolean recursive ) {
		for ( String s : values.keySet() ) {
			if ( ArrayUtils.contains( keys, s ) ) {
				ArrayList<String> l = new ArrayList<String>();
				for ( String m : values.get( s ) ) {
					l.add( mod.modify( m ) );
				}
				values.put( s, l.toArray( new String[ l.size() ]) );
			}
		}
		if ( recursive ) {
			for ( XMLExpansion c : children ) {
				c.modifyValues( keys, mod, recursive );
			}
		}
	}
	
	public Map<String, String[]> getValues() {
		return values;
	}
	
	public List<XMLExpansion> getChildren() {
		return children;
	}
	
	public static Map<String, String[]> convertSimpleMap( Map<String, String> in ) {
		Map<String, String[]> ret = new HashMap<String, String[]>();
		for ( String s : in.keySet() ) {
			ret.put( s, new String[] { in.get( s ) } );
		}
		return ret;
	}
	
	public XMLExpansion getChild( String n ) {
		List<XMLExpansion> e = getChildren( n );
		return e == null ? null : e.get( 0 );
	}
	
	public List<XMLExpansion> getChildren( String n ) {
		List<XMLExpansion> ret = new ArrayList<XMLExpansion>();
		for ( XMLExpansion x : children ) {
			if ( x.getName().equals( n ) ) {
				ret.add( x );
			}
		}
		return ret.size() > 0 ? ret : null;
	}
	
	public String getName() {
		return name;
	}
	
	public String toXML( boolean recursive ) {
		String ret = "";
		for ( String s : ordered ) {
			String[] vals = values.get( s );
			if ( vals == null ) {
				if ( recursive ) {
					for ( XMLExpansion c : children ) {
						if ( c.getName().equals( s ) ) {
							ret += XMLUtils.wrapInTag( c.toXML( recursive ), c.getName(), c.getAttributes() );
						}
					}
				}
			} else {
				Map<String, String>[] attr = nodeAttributes.get( s );
				for ( int i = 0; i < vals.length; i++ ) {
					Map<String, String> at = null;
					if ( attr != null ) {
						at = attr[ i ];
					}
					ret += XMLUtils.wrapInTag( vals[ i ], s, at );
				}
			}
		}
		return ret;
	}
	
	public String toXMLWithWrapper( boolean recursive ) {
		String sub = toXML( recursive );
		return XMLUtils.wrapInTag( sub, name, getAttributes() );
	}
	
	public void print( boolean recursive ) {
		System.out.println( "NAME:  " + name );
		String myattr = "";
		for ( String a : attributes.keySet() ) {
			myattr += "(" + a + "," + attributes.get( a ) + ")";
		}
		System.out.println( "MYATTR: " + myattr );
		if ( values != null ) {
			for ( String s : ordered ) {
				String subs = "";
				String attr = "";
				
				String[] vals = values.get( s );
				Map<String, String>[] attrs = nodeAttributes.get( s );
				if ( vals != null ) {
					for ( int i = 0; i < vals.length; i++ ) {
						subs += "[" + vals[ i ] + "]";
						if ( attrs != null && attrs.length > i ) {						
							Map<String, String> at = attrs[ i ];
							attr += "[";
							for ( String a : at.keySet() ) {
								attr += "(" + a + "," + at.get( a ) + ")";
							}
							attr +="]";
						}
					}
				}
				System.out.println( "K,V:  " + s + ", " + subs );
				if ( !attr.equals( "" ) ) {
					System.out.println( "ATR:  " + s + ", " + attr );
				}
			}
		}
		if ( recursive ) {
			for ( XMLExpansion c : children ) {
				c.print( recursive );
			}
		}
	}
}