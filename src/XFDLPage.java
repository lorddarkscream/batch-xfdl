import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

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
	private HashMap<String, Vector<XFDLCell>> groups;
	
	private final XFDLDocumentDisplayer doc;
	
	
	public XFDLPage(String sid, XFDLDocumentDisplayer doc) {
		this.sid = sid;
		this.items = new ArrayList<XFDLItem>();
		this.doc = doc;
		
		groups = new HashMap<String, Vector<XFDLCell>>();
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

	//returns a Vector full of XFDLCells from this or another page. 
	public Vector<XFDLCell> getGroup(String groupName) {
		//if the group name contains an instance of REFERECNE_SEPERATOR
		//(.) then we need to pass up to the document to get the right page
		if(groupName.indexOf(XFDLDocumentDisplayer.REFERENCE_SEPERATOR) > 0) {
			return doc.getGroup(groupName);
		} else {
			//there is no page reference in the group name so the group must 
			//exist on this page.  Check if we have a group by the group name
			//if not create it because it exists but does not have any contents
			//yet
			if(!groups.containsKey(groupName)) {
				groups.put(groupName, new Vector<XFDLCell>());
			}
			return groups.get(groupName);
		}
	}

	
	public void addCell(XFDLCell cell) {
		try{
		getGroup(cell.getGroup()).add(cell);
		} catch (IllegalArgumentException err) {
			//Group cannot be found, can't add item to it.
		}
	}
	
	
}
