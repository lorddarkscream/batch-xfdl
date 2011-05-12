import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.miginfocom.swing.MigLayout;


public class FormRenderer {

	private XMLStreamReader reader;
	private ArrayList<FormPanel> pages;
	private int currentPage;
	private HashMap<JComponent, String> components;
	
	private static final String FILE_HEADER_BLOCK = 
		"application/vnd.xfdl;content-encoding=\"base64-gzip\""; 
	
	private static final String XFDL_PAGE = "page";
	private static final String XFDL_FIELD = "field";
	private static final String XFDL_LABEL = "label";
	private static final String XFDL_LINE = "line";
	private static final String XFDL_ATTRIBUTE_SID = "sid";
	private static final String XFDL_VALUE = "value";
	private static final String XFDL_ITEMLOCATION = "itemlocation";
	private static final String XFDL_LOCATION_STYLE_ABSOLUTE = "absolute";
	private static final String XFDL_LOCATION_SIZE_EXTENT = "extent";
	private static final String XFDL_FONT = "fontinfo";
	private static final String XFDL_FONT_STYLE_PLAIN = "plain";
	private static final String XFDL_FONT_STYLE_BOLD = "bold";
	private static final String XFDL_FONT_STYLE_UNDERLINE = "underline";
	private static final String XFDL_FONT_STYLE_ITALIC = "italic";
	
	
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
		
		pages = new ArrayList<FormPanel>();
		components = new HashMap<JComponent, String>();
	}
	
	/**
	 * Renders the form into a JPanel which can be retrieved with the getForm()
	 * method.
	 * 
	 * Controls (TextFields, Check Boxes, Radio buttons) are mapped to their
	 * related SID value
	 * @param control
	 * @throws XMLStreamException Thrown if the there is a fault in the XML
	 */
	public void renderForm(ActionListener control) throws XMLStreamException {
		try {
			while(reader.hasNext()) {
				reader.next();
				if(reader.isStartElement()) {
					if(reader.getLocalName().equals(XFDL_PAGE)) {
						addPage();
					}
					else if(reader.getLocalName().equals(XFDL_FIELD)) {
						addField();
					}
					else if(reader.getLocalName().equals(XFDL_LABEL)) {
						addLabel();
					}
					else if(reader.getLocalName().equals(XFDL_LINE)) {
						addLine();
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * Creates a new page of form.
	 * 
	 * Start State: Cursor is on the <page> start element.
	 * End State: Cursor does not change state in this method. 
	 */
	private void addPage() {
		FormPanel page = new FormPanel();
		page.setLayout(new MigLayout());
		pages.add(page);
		currentPage = pages.size() - 1;
		components.put(page, reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID));		
	}

	/**
	 * Adds a text field to the current page.
	 * @throws XMLStreamException
	 */
	private void addField() throws XMLStreamException {
		JTextField field = new JTextField();
		Rectangle bounds = new Rectangle();
		
		String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID);
		
		while(reader.hasNext() &&
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_FIELD))) 
		{
			reader.next();
			
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_ITEMLOCATION)) 
				{
					bounds = processItemLocation();
				}
				if(reader.getLocalName().equals(XFDL_VALUE)) {
					field.setText(processValue());
				}
				if(reader.getLocalName().equals(XFDL_FONT)) {
					field.setFont(processFont());
				}
			}
		}
		
		if(bounds != null) {
			field.setBounds(bounds);
			components.put(field, sid);
			pages.get(currentPage).add(field, 
					"pos " + field.getX() + " " + field.getY() + ", " +
					"w " + field.getWidth() + 
					", h " + field.getHeight());
		}
	}

	/**
	 * Adds a label element to the page.
	 * Start State: Cursor is on the <label> start element
	 * End State: Cursor is on the </label> end element and a JLabel has been
	 * added to the current page.
	 * @throws XMLStreamException 
	 */
	private void addLabel() throws XMLStreamException {
		
		JLabel label = new JLabel();
		Rectangle bounds = new Rectangle();
		String sid = reader.getAttributeValue(null, XFDL_ATTRIBUTE_SID); 
		
		while(reader.hasNext() && 
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_LABEL))) 
		{
			
			reader.next();
			
			if (reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_ITEMLOCATION)) {
					bounds = processItemLocation();
					if(bounds != null) {
						label.setBounds(bounds);
					}
				}
				else if(reader.getLocalName().equals(XFDL_VALUE)) {
					label.setText(processValue());
				}
				else if(reader.getLocalName().equals(XFDL_FONT)) {
					label.setFont(processFont());
				}
			}
			
		}
		
		if(bounds != null) {
			components.put(label, sid);
			pages.get(currentPage).add(label, 
					"pos " + label.getX() + " " + label.getY() + ", " +
					"w " + label.getWidth() + 
					", h " + label.getHeight());
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
	private void addLine() throws XMLStreamException {
		while(reader.hasNext() &&
				!(reader.isEndElement() && 
						reader.getLocalName().equals(XFDL_LINE))) {
			
			reader.next();
			if(reader.isStartElement()) {
				if(reader.getLocalName().equals(XFDL_ITEMLOCATION)) {
					pages.get(currentPage).addLine(processItemLocation());
				}
			}
		}
		
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
						reader.getLocalName().equals(XFDL_ITEMLOCATION)))
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
					result = null;
					x = attributes.size();
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
	private String processValue() throws XMLStreamException {
		String result = new String();
		
		if(reader.hasNext() && 
				reader.isStartElement() && 
				reader.getLocalName().equals(XFDL_VALUE)) 
		{
			reader.next();
			if(reader.isCharacters()) {
				result = reader.getText();
			}
		}
		
		return result;
	}
	

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
						reader.getLocalName().equals(XFDL_FONT))) 
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
			fontSize = 12;
		}
		
		Font result = new Font(attributes.get(0), 
				fontStyle, 
				fontSize);
		
		if(m.size() > 0) {
			result.deriveFont(m);
		}
		
		return result;
	}

	/**
	 * Retrieves an ArrayList of JPanel objects.  Each page of the form is 
	 * rendered to an element in the Array.
	 * @return ArrayList of JPanel pages.
	 */
	public FormPanel getPage(int page) {
		return pages.get(page);
	}
	
	public int getPageCount() {
		return pages.size();
	}
	
	/**
	 * Retrieves a Map with a JComponent as a key and related XFDL SID as
	 * a value.
	 * @return Map of JComponent to XFDL SID.
	 */
	public Map<JComponent, String> getFields() {
		return components;
	}
}
