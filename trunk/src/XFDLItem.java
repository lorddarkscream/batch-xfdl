import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

/**
 * Generic interface for all XFDL Items that provides standard methods for 
 * Retrieving SID and a JComponent version of the item for display.
 * 
 * @author andy
 *
 */
public interface XFDLItem {

	public String getSID();
	
	/**
	 * Each item is required to know how to add itself to the page. This allows
	 * different components to be handled appropriately, because lines have to
	 * be added differently the JComponents.
	 * 
	 * @param page Page that can be displayed on a form.
	 */	
	public void addToPage(FormPanel page);
	
	
	/**
	 * Item adds a properly sized version of itself to the container (destination.add)
	 * without any reference to it's location information.   
	 */
	public void addWithoutLocation(Container destination, String UIOptions);
	
	
}
