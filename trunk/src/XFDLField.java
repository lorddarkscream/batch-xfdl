import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JTextField;

/**
 * 
 */

/**
 * Fields are represented as JTextFields when displayed on the page.
 * @author andy
 *
 */
public class XFDLField implements XFDLItem {

	private Font font;
	private Rectangle location; //Fields size & location on the page
	private String sid; //Unique identifier of the item within the page
	private String value; //vaule of the field.
	
	public XFDLField(String sid, String value, Rectangle location, Font font) {
		this.sid = sid;
		this.value = value;
		this.location = location;
		this.font = font;
	}
	
	/* (non-Javadoc)
	 * @see XFDLItem#getSID()
	 */
	@Override
	public String getSID() {
		// TODO Auto-generated method stub
		return sid;
	}

	/* (non-Javadoc)
	 * @see XFDLItem#getComponent()
	 */
	@Override
	public void addToPage(FormPanel page) {
		JTextField result = new JTextField();
		
		result.setName(sid);
		result.setBounds(location);
		result.setText(value);
		result.setFont(font);
		
		page.add(result);
	}
	
	/**
	 * Sets the value of the field.
	 * @param value New value for the item.
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
