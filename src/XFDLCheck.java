import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JCheckBox;


public class XFDLCheck implements XFDLItem {

	final String sid;
	final Rectangle bounds;
	boolean value;
	
	public XFDLCheck(String sid, Rectangle bounds, boolean value) {
		this.sid = sid;
		this.bounds = bounds;
		this.value = value;
	}
	
	@Override
	public String getSID() {
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		JCheckBox result = new JCheckBox();
		
		result.setName(sid);
		result.setBounds(bounds);
		result.setSelected(value);
		
		page.add(result);
	}	
	
	@Override
	public void addWithoutLocation(Container destination, String UIOptions) {
		JCheckBox result = new JCheckBox();
		
		result.setName(sid);
		result.setSize(bounds.getSize());
		result.setSelected(value);
		
		destination.add(result, UIOptions);
		
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}



}
