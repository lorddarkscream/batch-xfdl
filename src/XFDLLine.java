import java.awt.Container;
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

	@Override
	public void addWithoutLocation(Container destination, String UIOptions) {
		//In this case you can't draw a line without a start and end point
		//....so we do nothing, null function without nulls.		
	}

}
