import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class FormRenderer {

	private XMLStreamReader reader;
	
	private static final String FILE_HEADER_BLOCK = 
		"application/vnd.xfdl;content-encoding=\"base64-gzip\""; 
	
	private static final String XFDL_CELL = "cell";
	private static final String XFDL_CHECK = "check";
	private static final String XFDL_COMBOBOX = "combobox";
	private static final String XFDL_FIELD = "field";
	private static final String XFDL_LABEL = "label";
	private static final String XFDL_LINE = "line";
	private static final String XFDL_PAGE = "page";
	private static final String XFDL_ATTRIBUTE_SID = "sid";

	private static final String XFDL_OPTION_FONT = "fontinfo";
	private static final String XFDL_OPTION_GROUP = "group";
	private static final String XFDL_OPTION_ITEMLOCATION = "itemlocation";
	private static final String XFDL_OPTION_VALUE = "value";
	
	private static final String XFDL_LOCATION_STYLE_ABSOLUTE = "absolute";
	private static final String XFDL_LOCATION_SIZE_EXTENT = "extent";

	private static final String XFDL_FONT_STYLE_PLAIN = "plain";
	private static final String XFDL_FONT_STYLE_BOLD = "bold";
	private static final String XFDL_FONT_STYLE_UNDERLINE = "underline";
	private static final String XFDL_FONT_STYLE_ITALIC = "italic";
	
	private static final String XFDL_VALUE_TRUE = "on";	
	
	private static final Rectangle INVALID_LOCATION = new Rectangle();
	private static final int DEFAULT_FONT_SIZE = 8;
	private static final Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, DEFAULT_FONT_SIZE);
	
	
	public FormRenderer(String inputFile) 
	throws XMLStreamException, IOException 
	{
		
		//create file object
		File f = new File(inputFile);
		if(!f.exists()) {
			throw new IOException("Specified File could not be found!");
		}
		
		//open file stream from file
		FileInputStream fis = new FileInputStream(inputFile);
		
		//Skip past the MIME header
		fis.skip(FILE_HEADER_BLOCK.length());	
		
		//Decompress from base 64					
		Base64.InputStream bis = new Base64.InputStream(fis, 
				Base64.DECODE);
		
		//UnZIP the resulting stream
		GZIPInputStream gis = new GZIPInputStream(bis);
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		reader = inputFactory.createXMLStreamReader(gis);
	}
	
	/**
	 * Renders the form into a JPanel which can be retrieved with the getForm()
	 * method.
	 * 
	 * Controls (TextFields, Check Boxes, Radio buttons) are mapped to their
	 * related SID value
	 * @param control
	 * @throws XMLStreamException Thrown if the 
	 * there is a fault in the XML
	 */
	public XFDLDocumentDisplayer renderForm(ActionListener control) 
	throws XMLStreamException {
		
		XFDLDocumentDisplayer doc = new XFDLDocumentDisplayer();
		
		try {
			while(reader.hasNext()) {
				if(reader.isStartElement()) {
					if(reader.getLocalName().equals(XFDL_CELL)) {
						doc.getPage(doc.getPageCount() - 1).addCell(addCell());
					}
					else if(reader.getLocalName().equals(XFDL_CHECK)) {
						doc.getPage(doc.getPageCount() - 1).addItem(addCheck());
					}
					else if(reader.getLocalName().equals(XFDL_COMBOBOX)) {
						doc.getPage(doc.getPageCount() - 1).addItem(
							addComboBox(doc.getPage(doc.getPageCount() - 1)));
					}
					else if(reader.getLocalName().equals(XFDL_FIELD)) {
						doc.getPage(doc.getPageCount() -1).addItem(addField());
					}
					else if(reader.getLocalName().equals(XFDL_LABEL)) {
						doc.getPage(doc.getPageCount() - 1).addItem(addLabel());
					}
					else if(reader.getLocalName().equals(XFDL_LINE)) {
						doc.getPage(doc.getPageCount() - 1).addItem(addLine());
					}
					else if(reader.getLocalName().equals(XFDL_PAGE)) {
						doc.addPage(addPage(doc));
					}
					
				}
				reader.next();
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw e;
		}
		
		return doc;
	}
	
	
	/**
	 * Adds a check box to the form.
	 * 
	 * Start State: Cursor is positioned on the <check> start element.
	 * End State: Cursor is positioned on the <check> end element.
	 * @throws XMLStreamException 
	 */
	private XFDLItem addCheck() throws XMLStreamException {

		Rectangle bounds = INVALID_LOCATION;
		boolean value = false;
		final String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		
		while(reader.hasNext() &&
				!(reader.isEndElement() &&
						reader.getLocalName().equals(XFDL_CHECK))) {
			
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)) {
					bounds = processItemLocation();
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_VALUE)) {
					String result = processSimpleOption(XFDL_OPTION_VALUE);
					value = result.equals(XFDL_VALUE_TRUE);
				}
			}
			

		}
		
		if(!bounds.equals(INVALID_LOCATION)) {
			return new XFDLCheck(sid, bounds, value);
		} else {
			return new InvalidXFDLItem();
		}
		
		
	}
	
	/**
	 * Adds a cell to a group inside of the form.
	 * 
	 * Currently does not differentiate between cell types. 
	 * 
	 * Start State: Cursor is positioned on the <cell> start element.
	 * End State: Cursor is positioned on the <cell> end element.
	 * 
	 * @throws XMLStreamException 
	 */
	private XFDLCell addCell() throws XMLStreamException {
		String value = "";
		String label = "";
		String group = "";
		
		final String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		
		while(reader.hasNext() && 
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_CELL))) {
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_VALUE)) {
					value = processSimpleOption(XFDL_OPTION_VALUE);
				}
				if(reader.getLocalName().equals(XFDL_LABEL)) {
					label = processSimpleOption(XFDL_LABEL);
				}
				if(reader.getLocalName().equals(XFDL_OPTION_GROUP)) {
					group = processSimpleOption(XFDL_OPTION_GROUP);
				}
			}
		}
		
		return new XFDLCell(sid, value, label, group);
		
	}

	/**
	 * Adds a combobox to the form. Combobox is added to the form empty, but 
	 * associated with the group to which it belongs.  <cell> items are then 
	 * processed and added to the groups.  Combobox is then filled at display
	 * time by the FormPanel class.
	 * 
	 * Start State: Cursor is positioned on the <combobox> start element.
	 * End State: Cursor is positioned on the <combobox> end element.
	 * @throws XMLStreamException 
	 */
	private XFDLItem addComboBox(XFDLPage page) throws XMLStreamException {
		
		final String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		Rectangle bounds = INVALID_LOCATION;
		String value = new String();
		String groupName = new String();
		Font font = DEFAULT_FONT;
			
		while(reader.hasNext() &&
				!(reader.isEndElement() &&
						reader.getLocalName().equals(XFDL_COMBOBOX))) {
			
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)) {
					bounds = processItemLocation();
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_VALUE)) {
					value = processSimpleOption(XFDL_OPTION_VALUE);
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_GROUP)) {
					groupName = processSimpleOption(XFDL_OPTION_GROUP);
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_FONT)) {
					font = processFont();
				}
			}
			

		}
		
		if(!bounds.equals(INVALID_LOCATION)) {
			return new XFDLComboBox(sid, bounds, groupName, value, font, page);
		} else { 
			return new InvalidXFDLItem();
		}
		
	}

	/**
	 * Adds a text field to the current page.
	 * @throws XMLStreamException
	 */
	private XFDLItem addField() throws XMLStreamException {
		
		Rectangle bounds = INVALID_LOCATION;
		Font font = DEFAULT_FONT;
		String value = "";
		
		String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		
		while(reader.hasNext() &&
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_FIELD))) 
		{
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)) 
				{
					bounds = processItemLocation();
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_VALUE)) {
					value = processSimpleOption(XFDL_OPTION_VALUE);
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_FONT)) {
					font = processFont();
				}
			}
			
		}
		
		if(bounds.equals(INVALID_LOCATION)) {
			return new InvalidXFDLItem();
		}
		else {
			return new XFDLField(sid, value, bounds, font);
		}
	}

	/**
	 * Adds a label element to the page.
	 * Start State: Cursor is on the <label> start element
	 * End State: Cursor is on the </label> end element and a JLabel has been
	 * added to the current page.
	 * @throws XMLStreamException 
	 */
	private XFDLItem addLabel() throws XMLStreamException {
		
		final String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		String value = new String();
		Font font = DEFAULT_FONT;
		Rectangle bounds = INVALID_LOCATION;
		 
		while(reader.hasNext() && 
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_LABEL))) 
		{
			reader.next();

			if (reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)) {
					bounds = processItemLocation();
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_VALUE)) {
					value = processSimpleOption(XFDL_OPTION_VALUE);
				}
				else if(reader.getLocalName().equals(XFDL_OPTION_FONT)) {
					font = processFont();
				}
			}
			
		}
		
		if(!bounds.equals(INVALID_LOCATION)) {
			return new XFDLLabel(sid, bounds, value, font);
		}
		else {
			return new InvalidXFDLItem();
		}
	}


	/**
	 * Draws a new line on the Panel
	 * 
	 * Start State: Cursor is positioned on the <line> start element.
	 * End State: Cursor is positioned on the <line> end element.
	 * 
	 * @throws XMLStreamException 
	 */
	private XFDLItem addLine() throws XMLStreamException {
		
		String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID); 
		Rectangle bounds = INVALID_LOCATION;
		
		while(reader.hasNext() &&
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_LINE))) {
			
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)) {
					bounds = processItemLocation();
				}
			}
		}
		
		if(!bounds.equals(INVALID_LOCATION)) {
			return new XFDLLine(sid, bounds);
		} else {
			return new InvalidXFDLItem();
		}
		
	}

	/**
	 * Creates a new page of form.
	 * 
	 * Start State: Cursor is on the <page> start element.
	 * End State: Cursor does not change state in this method. 
	 */
	private XFDLPage addPage(XFDLDocumentDisplayer doc) {
		return new XFDLPage(reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID), doc);
	}
	
	
	/**
	 * Processes an item location field into a Rectangle object used to set
	 * the bounds and location of form item.
	 * Start State: Cursor positioned on the itemlocation start element.
	 * End State: Cursor positioned on the itemlocation end element
	 * 
	 * Currently only handles absolute positioning and extent sizes.
	 * Any other form of positioning data returns null.
	 * 
	 * @return Rectangle representing the location and size of item.
	 * @throws XMLStreamException 
	 */
	private Rectangle processItemLocation() throws XMLStreamException {
		Rectangle result = new Rectangle();
		
		ArrayList<String> attributes = new ArrayList<String>();
	
		while(reader.hasNext() &&
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_OPTION_ITEMLOCATION)))
		{
			reader.next();
			
			if(reader.isCharacters() && !reader.isWhiteSpace()) {
				attributes.add(reader.getText());
			}

		}
		
		for(int x=0; x<attributes.size(); x++) {
			if(attributes.get(x).equals(XFDL_LOCATION_STYLE_ABSOLUTE)) {
				result.setLocation(Integer.parseInt(attributes.get(x+1)),
						Integer.parseInt(attributes.get(x+2)));
			} else if(attributes.get(x).equals(XFDL_LOCATION_SIZE_EXTENT)) {
				result.setSize(Integer.parseInt(attributes.get(x+1)),
						Integer.parseInt(attributes.get(x+2)));
			} else {
				try{
					Integer.parseInt(attributes.get(x));
				} catch (NumberFormatException nfe) {
					return INVALID_LOCATION;
				}
			}
		}
		
		return result;
	}

	/**
	 * Process a value an item
	 * 
	 * Start State: Cursor positioned on the <value> start element.
	 * End State: Cursor positioned on the <value> end element
	 * @return
	 * @throws XMLStreamException 
	 */
//	private String processValue() throws XMLStreamException {
//		String result = new String();
//		
//		if(reader.hasNext() && 
//				reader.isStartElement() && 
//				reader.getLocalName().equals(XFDL_OPTION_VALUE)) 
//		{
//			reader.next();
//			
//			if(reader.isCharacters()) {
//				result = reader.getText();
//			}
//
//		}
//		
//		return result;
//	}
	

	/**
	 * Creates a font object from XFDL fontinfo tag information
	 * 
	 * Start State: Cursor positioned on the <fontinfo> start element
	 * End State: Cursor positioned on the <fontinfo> end element
	 * 
	 * @return
	 * @throws XMLStreamException 
	 */
	private Font processFont() throws XMLStreamException {
		ArrayList<String> attributes = new ArrayList<String>();
		
		while(reader.hasNext() && 
				!(reader.isEndElement() &&
						reader.getLocalName().equals(XFDL_OPTION_FONT))) 
		{
			
			reader.next();
			
			if(reader.isCharacters() && !reader.isWhiteSpace()) {
				attributes.add(reader.getText());
			}
		}
		
		Map<TextAttribute, Object> m = new Hashtable<TextAttribute, Object>();
		int fontStyle = Font.PLAIN;
		
		if(attributes.size() > 2) {
			if(attributes.get(2).equals(XFDL_FONT_STYLE_PLAIN)) {
				fontStyle = fontStyle | Font.PLAIN;
			} else if (attributes.get(2).equals(XFDL_FONT_STYLE_BOLD))	{
				fontStyle = fontStyle | Font.BOLD;
			} else if (attributes.get(2).equals(XFDL_FONT_STYLE_ITALIC)) {
				fontStyle = fontStyle | Font.ITALIC;
			} else if (attributes.get(2).equals(XFDL_FONT_STYLE_UNDERLINE))	{
				m.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			}			
		}
		
		
		//convert to font size to number or set standard size
		int fontSize;
		try{
			fontSize = Integer.parseInt(attributes.get(1));
		} catch (NumberFormatException nfe) {
			fontSize = DEFAULT_FONT_SIZE;
		}
		
	    int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
	    fontSize = (int)Math.round(fontSize * screenRes / 72.0);
		
		Font result = new Font(attributes.get(0), 
				fontStyle, 
				fontSize);
		
		if(m.size() > 0) {
			result.deriveFont(m);
		}
		
		return result;
	}

	/**
	 * Processes a simple value option in the XFDL file and returns the text value of 
	 * the option.
	 * 
	 * Supports the following options: <group>, <value>
	 * 
	 * Start State: Cursor positioned on the start element.
	 * End State: Cursor positioned on the end element.
	 * 
	 * @return String value of the options. Null if no value is found.
	 * @throws XMLStreamException
	 */
	private String processSimpleOption(String optionName) throws XMLStreamException {
		String result = null;
		
		while(reader.hasNext() && 
				!(reader.isEndElement() &&
						reader.getLocalName().equals(optionName))) {
			
			reader.next();
			
			if(reader.isCharacters()) {
				result = reader.getText();
			}
	
		}
		if(result == null) 	{
			System.err.println("WTF? Null result in processSimpleOption");
		}
		
		return result;
	}
}
