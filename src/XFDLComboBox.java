import java.awt.Font;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;



public class XFDLComboBox implements XFDLItem {

	private final String sid;
	private final Rectangle bounds;
	private final String groupName;
	private Vector<XFDLCell> group;
	private final String value;
	private final Font font;
	
	public XFDLComboBox(String sid, 
			Rectangle bounds, 
			String groupName, 
			String value, 
			Font font,
			XFDLPage page) {
		this.sid = sid;
		this.bounds = bounds;
		this.groupName = groupName;
		this.value = value;
		this.font = font;
		
		group = page.getGroup(groupName);
		
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
		
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		Iterator<XFDLCell> it = group.iterator();
		while(it.hasNext()) {
			XFDLCell cell = it.next();
			String newItem;
			
			if(!cell.getLabel().isEmpty()) {
				newItem = cell.getLabel();
			} else {
				newItem = cell.getValue();
			}
			
			model.addElement(newItem);
			
			if(newItem.equals(value)) {
				model.setSelectedItem(newItem);
			}
		}
		
		result.setModel(model);
		
		page.add(result);
	}

}
