import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

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
	private Rectangle bounds; //Fields size & location on the page
	private String sid; //Unique identifier of the item within the page
	private String value; //Value of the field.
	private XFDLOptionScrollHoriz scrollHoriz; //horizontal scrolling option
	private String scrollvert; //vertical scrolling option
	
	
	/**
	 * Complete constructor allows for setting every option.
	 * @param sid
	 * @param value
	 * @param bounds
	 * @param font
	 */
	public XFDLField(String sid, 
			String value, 
			Rectangle bounds, 
			Font font, 
			XFDLOptionScrollHoriz scrollHoriz) {
		this.sid = sid;
		this.value = value;
		this.bounds = bounds;
		this.font = font;
		this.scrollHoriz = scrollHoriz;
		
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
		JTextArea result = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		Boolean insertScrollPane = false;
		
		result.setName(sid);
		result.setBounds(bounds);
		result.setText(value);
		result.setFont(font);
		
		
		switch(scrollHoriz) {

		case NEVER:
			result.setLineWrap(false);
			break;
		
		case ALWAYS:
			//Place the result inside of a scroll pane to support scrolling. 
			//Also turn on wordwrap to put text on more then one line.
			result.setLineWrap(true);
			result.setWrapStyleWord(true);
			
			scrollPane.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			scrollPane.setViewportView(result);
			scrollPane.setBounds(bounds);
			
			insertScrollPane = true;
					
			break;
			
		case WORDWRAP:
			result.setLineWrap(true);
			result.setWrapStyleWord(true);
			break;
		}
		
		if(insertScrollPane) {
			page.add(scrollPane);
		} else {
			page.add(result);
		}
	}
	
	@Override
	public void addWithoutLocation(Container destination, String UIOptions) {
		JTextComponent result;
		
		if(scrollHoriz == XFDLOptionScrollHoriz.NEVER) {
			result = new JTextField();
		} else {
			result = new JTextArea();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		Boolean insertScrollPane = false;
		
		result.setName(sid);
		result.setSize(bounds.getSize());
		result.setText(value);
		result.setFont(font);
		
		switch(scrollHoriz) {
	
		case ALWAYS:
			//Place the result inside of a scroll pane to support scrolling. 
			//Also turn on wordwrap to put text on more then one line.
			((JTextArea)result).setLineWrap(true);
			((JTextArea)result).setWrapStyleWord(true);
			
			scrollPane.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			scrollPane.setViewportView(result);
			scrollPane.setBounds(bounds);
			
			insertScrollPane = true;
					
			break;
			
		case WORDWRAP:
			((JTextArea)result).setLineWrap(true);
			((JTextArea)result).setWrapStyleWord(true);
			break;
		}
		
		if(insertScrollPane) {
			destination.add(scrollPane, UIOptions);
		} else {
			destination.add(result, UIOptions);
		}
		
	}
	
	/**
	 * Sets the value of the field.
	 * @param value New value for the item.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
