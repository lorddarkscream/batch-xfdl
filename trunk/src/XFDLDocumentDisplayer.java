import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 */

/**
 * @author andy
 *
 */
public class XFDLDocumentDisplayer {
	
	private ArrayList<XFDLPage> pages;
	
	public static final char REFERENCE_SEPERATOR = '.';
	
	public XFDLDocumentDisplayer() {
		pages = new ArrayList<XFDLPage>();
	}
	
	public void addPage(XFDLPage page) {
		pages.add(page);
	}
	
	public XFDLPage getPage(int pageNum) {
		return pages.get(pageNum);
	}
	
	public XFDLPage getPage(String sid) {
		for(int x=0; x<pages.size(); x++) {
			if(pages.get(x).getPageSID().equals(sid)) {
				return pages.get(x);
			}
		}
		throw new IllegalArgumentException("Requested SID cannot be found");
	}
	
	public int getPageCount() {
		return pages.size();
	}

	/**
	 * Returns a vector full of XFDLCells from a page in the document. Groups 
	 * are referenced with the Page.GroupSID format.
	 * @param groupName
	 * @return A vector full of XFDLCells representing the group
	 */
	public Vector<XFDLCell> getGroup(String groupName) {
		if(groupName.indexOf(REFERENCE_SEPERATOR) > 0) {
			return getPage(groupName.substring(0, 
					groupName.indexOf(REFERENCE_SEPERATOR)))
			.getGroup(groupName.substring(groupName.indexOf(REFERENCE_SEPERATOR) + 1));
		} else {
			throw new IllegalArgumentException("Group name \"" + groupName + "\" cannot be resolved.");
		}
		
		
	}

}
