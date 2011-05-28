import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;


public class XFDLLabel implements XFDLItem {
	
	private final Rectangle bounds;
	private final Font font;
	private final String sid;	
	private final String value;
	
	
	public XFDLLabel(String sid, Rectangle bounds, String value, Font font) {
		this.sid = sid;
		this.bounds = bounds;
		this.value = value;
		this.font = font;
	}

	@Override
	public String getSID() {
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		JLabel result = new JLabel(value);
		
		result.setName(sid);
		result.setBounds(bounds);
		result.setFont(font);
		
		page.add(result);
	}

}
