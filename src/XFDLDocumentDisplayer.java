import java.util.ArrayList;

/**
 * 
 */

/**
 * @author andy
 *
 */
public class XFDLDocumentDisplayer {
	
	public ArrayList<XFDLPage> pages;
	
	public XFDLDocumentDisplayer() {
		pages = new ArrayList<XFDLPage>();
	}
	
	public void addPage(XFDLPage page) {
		pages.add(page);
	}
	
	public XFDLPage getPage(int pageNum) {
		return pages.get(pageNum);
	}
	
	public int getPageCount() {
		return pages.size();
	}

}
