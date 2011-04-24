import java.awt.BorderLayout;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;



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
		
		this.setLayout(new BorderLayout());
		
		//Super Container Panel
		JPanel superContainer = new JPanel(new BorderLayout());
		this.add(superContainer, BorderLayout.NORTH);
		
		//Task Label
		superContainer.add(new JLabel("<html><b><u><font size=24>Add Pages to "
				+ control.getFormName() 
				+ "</font></u></b></html>"), 
				BorderLayout.NORTH);
				
		//Labels Panel
		JPanel labels = new JPanel(new GridLayout(3,1));
		labels.add(new JLabel("Source File:"));
		labels.add(new JLabel("Output File Name: (leave blank to overwrite)"));
		labels.add(new JLabel("Number of Additional Pages:"));
		superContainer.add(labels, BorderLayout.WEST);
		
		//Fields Container
		JPanel fields = new JPanel(new GridLayout(3,1));
		
		sourceFile = new JTextField("", 30);
		fields.add(sourceFile);
		
		outputFileName = new JTextField("", 30);
		fields.add(outputFileName);
		
		numPages = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		fields.add(numPages);
		
		superContainer.add(fields, BorderLayout.CENTER);
		
		//Container for the browse button
		JPanel browse = new JPanel(new GridLayout(3,1));
		fileSelect = new JButton(COMMAND_BROWSE);
		fileSelect.setActionCommand(COMMAND_BROWSE);
		fileSelect.addActionListener(control);
		browse.add(fileSelect);
		superContainer.add(browse, BorderLayout.EAST);
		
		//Set up the execute button
		JPanel executePanel = new JPanel(new BorderLayout());
		executeTask = new JButton(COMMAND_EXECUTE);
		executeTask.setActionCommand(COMMAND_EXECUTE);
		executeTask.addActionListener(control);
		executePanel.add(executeTask, BorderLayout.EAST);
		superContainer.add(executePanel, BorderLayout.SOUTH);
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
