import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;


/**
 * Pane for viewing of forms.
 * @author andy
 *
 */
public class FormViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2532030739604242930L;
	
	public JButton previousPage;
	public JButton nextPage;
	public JButton selectForm;
	public JPanel formPage;
	
	public JScrollPane scrollPane;

	private FormViewPane() {
		this.setLayout(new MigLayout("fill"));
		
		previousPage = new JButton("Previous Page");
		nextPage = new JButton("Next Page");
		selectForm = new JButton("Select Form");
		formPage = new JPanel();
		
		scrollPane = new JScrollPane();
		
		this.add(new JLabel("<html><h1>View Form</h1></html>"), "span 3, wrap");
		this.add(previousPage, "ax left");
		this.add(selectForm, "ax center, pushx");
		this.add(nextPage, "ax right, wrap, pushx");
		
		this.add(scrollPane, "span 3, push, grow");
	}
	
	public void setControl(FormViewControl control) {		
		previousPage.addActionListener(control);
		nextPage.addActionListener(control);
		selectForm.addActionListener(control);
	}
	
	/**
	 * Factory method to create new form View pane.  Creates instance of 
	 * Control and links the view and control.
	 * @return
	 */
	public static FormViewPane newFormViewPane() {
		FormViewPane result = new FormViewPane();
		
		FormViewControl control = new FormViewControl(result);
		result.setControl(control);
		
		return result;
	}
}
