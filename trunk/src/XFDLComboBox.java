import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JComboBox;


public class XFDLComboBox implements XFDLItem {

	private final String sid;
	private final Rectangle bounds;
	private final String group;
	private final String value;
	private final Font font;
	
	public XFDLComboBox(String sid, Rectangle bounds, String group, String value, Font font) {
		this.sid = sid;
		this.bounds = bounds;
		this.group = group;
		this.value = value;
		this.font = font;
	}
	
	@Override
	public String getSID() {
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		JComboBox result = new JComboBox();
		
		result.setName(sid);
		result.setBounds(bounds);
		result.setFont(font);
		
		page.add(result);
	}

}
