import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;



public class AddPageTaskPane extends JPanel {

	private static final long serialVersionUID = 148828131545164596L;
	
	public static String COMMAND_BROWSE = "Browse...";
	public static String COMMAND_EXECUTE = "Add Pages";
	
	public JTextField sourceFile;
	public JTextField outputFileName;
	public JSpinner numPages;
	public JButton executeTask;
	
	private JButton fileSelect;
	

	private AddPageTaskPane(AddPageTaskController control) {
		
		this.setLayout(new MigLayout());
		
		
		//Task Label
		this.add(new JLabel("<html><h1>Add Pages to "
					+ control.getFormName() 
					+ "</h1></html>"), 
				"wrap, span 3");
				
		//Labels Panel
		this.add(new JLabel("Source File: "));
		sourceFile = new JTextField("", 30);
		this.add(sourceFile, "grow");
		
		fileSelect = new JButton(COMMAND_BROWSE);
		fileSelect.setActionCommand(COMMAND_BROWSE);
		fileSelect.addActionListener(control);
		this.add(fileSelect, "wrap, sg buttons");

		
		this.add(new JLabel("Output File Name (optional): "));
		outputFileName = new JTextField("", 30);
		this.add(outputFileName, "wrap, span 2, grow");
		
		this.add(new JLabel("Number of Additional Pages: "));
		numPages = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		this.add(numPages, "wrap, span 2, grow");

		
		//Set up the execute button
		executeTask = new JButton(COMMAND_EXECUTE);
		executeTask.setActionCommand(COMMAND_EXECUTE);
		executeTask.addActionListener(control);
		this.add(executeTask, "right, span 3, sg buttons");
	}
	
	/**
	 * Retrieves an instance of the AddPageTaskPane
	 * @param ControlType Form defined in AddPageTaskController
	 * @return An AddPageTaskPane Object
	 * @throws Exception Thrown if the specified type is not 
	 */
	public static AddPageTaskPane addPagePaneFactory(int ControlType) throws Exception {
		AddPageTaskController control;
		AddPageTaskPane result; 
		//create Control
		switch(ControlType) {
		case AddPageTaskController.FORM_DA1594:
			control = DA1594AddPageController.factory();
			break;
		case AddPageTaskController.FORM_DA2062:
			control = DA2062AddPageController.factory();
			break;
		default:
			throw new Exception("Invalid Form Type Specified");
		}

		//create view

		result = new AddPageTaskPane(control);
		control.setTaskPane(result);
		return result;
	}
	
	
	
}
