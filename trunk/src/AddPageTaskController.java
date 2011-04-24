import java.awt.event.ActionListener;

/**
 * 
 */

/**
 * @author andy
 * Creates a standard interface for a controller that adds a page
 * using the AddTaskPane class.
 */
public interface AddPageTaskController extends ActionListener {

	public static final int FORM_DA1594 = 0;
	public static final int FORM_DA2062 = 1;
	
	public String getFormName();
	public void setTaskPane(AddPageTaskPane taskPane);
}
