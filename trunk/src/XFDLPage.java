import java.util.ArrayList;

/**
 * Model of an XFDL page, holds components to be rendered onto a FormPanel for 
 * display. Also allows for individual items to be recalled by SID
 * 
 * @author andy
 *
 */
public class XFDLPage {

	private String sid;
	private ArrayList<XFDLItem> items;
	
	
	public XFDLPage(String sid) {
		this.sid = sid;
		this.items = new ArrayList<XFDLItem>();
	}
	
	public void addItem(XFDLItem item) {
		items.add(item);
	}
	
	public String getPageSID() {
		return sid;
	}
	
	/**
	 * Provides a FormPanel component that will properly render the page for
	 * display.
	 * 
	 * @return Instance of a FormPanel.
	 */
	public FormPanel displayPage() {
		FormPanel result = new FormPanel();
		
		for(int x=0; x<items.size(); x++) {
			items.get(x).addToPage(result);
		}
		
		return result;
	}
	
	
}
