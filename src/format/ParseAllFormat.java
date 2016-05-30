package format;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class ParseAllFormat extends Format {

	private static final long serialVersionUID = 1L;

	private final Format format;

	public ParseAllFormat( Format format ) {
		this.format = format;
	}

	@Override
	public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos ) {
		return format.format( obj, toAppendTo, pos );
	}

	@Override
	public AttributedCharacterIterator formatToCharacterIterator( Object obj ) {
		return format.formatToCharacterIterator( obj );
	}

	@Override
	public Object parseObject( String source, ParsePosition pos ) {
		int initialIndex = pos.getIndex();
		Object result = format.parseObject( source, pos );
		if ( result != null && pos.getIndex() < source.length() ) {
			int errorIndex = pos.getIndex();
			pos.setIndex( initialIndex );
			pos.setErrorIndex( errorIndex );
			result = null;
		}
		return result;
	}

	@Override
	public Object parseObject( String source ) throws ParseException {
		return super.parseObject( source );
	}
}