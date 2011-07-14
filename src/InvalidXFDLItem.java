import java.awt.Container;


public class InvalidXFDLItem implements XFDLItem {

	private final String sid = "INVALID ITEM";
	
	public InvalidXFDLItem() {
		super();
	}
	
	
	@Override
	public String getSID() {
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		//Do nothing the item shouldn't be on the page.
	}


	@Override
	public void addWithoutLocation(Container destination, String MigOptions) {
		//Do nothing the item shouldn't be on the page.
	}

}
