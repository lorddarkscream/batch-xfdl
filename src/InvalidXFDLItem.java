
public class InvalidXFDLItem implements XFDLItem {

	private final String sid = "INVALID ITEM";
	
	public InvalidXFDLItem() {
		
	}
	
	@Override
	public String getSID() {
		return sid;
	}

	@Override
	public void addToPage(FormPanel page) {
		//Do nothing the item shouldn't be on the page.
	}

}
