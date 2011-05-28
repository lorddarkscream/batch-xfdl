
public class Cell {
	private String label;
	private String value;
	private String sid;
	
	public Cell(String label, String value, String sid) {
		this.label = label;
		this.value = value;
		this.sid = sid;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setSID(String sid) {
		this.sid = sid;
	}
	
	public String getSID() {
		return sid;
	}
}
