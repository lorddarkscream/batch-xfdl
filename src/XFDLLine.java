import java.awt.Rectangle;


public class XFDLLine implements XFDLItem {

	private final String sid;
	private final Rectangle bounds;
	
	public XFDLLine(String sid, Rectangle bounds) {
		this.sid = sid;
		this.bounds = bounds;
	}
	
	@Override
	public String getSID() {
		// TODO Auto-generated method stub
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		page.addLine(bounds);
	}

}
