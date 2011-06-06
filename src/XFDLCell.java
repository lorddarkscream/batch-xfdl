
public class XFDLCell {
	private final String value;
	private final String label;
	private final String group;
	private final String sid;
	
	public XFDLCell(String sid, String value, String label, String group) {
		this.sid = sid;
		this.value = value;
		this.label = label;
		this.group = group;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getLabel() {
		return this.label;
	}

	public String getGroup() {
		return group;
	}
	
	public String getSID() {
		return sid;
	}
}
