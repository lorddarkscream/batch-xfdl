import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class DA2062TaskPane extends Panel {

	private static final long serialVersionUID = 148828131545164596L;
	private static DA2062TaskPane instance;
	
	public static String COMMAND_BROWSE = "Browse...";
	public static String COMMAND_EXECUTE = "Add Pages";
	
	public TextField sourceFile;
	public TextField outputFileName;
	public JSpinner numPages;
	public Button executeTask;
	
	private Button fileSelect;
	
	private DA2062TaskController control;
	
	private DA2062TaskPane() {
		
		control = new DA2062TaskController(this);
		
		this.setLayout(new BorderLayout());
		
		//Super Container Panel
		Panel superContainer = new Panel(new BorderLayout());
		this.add(superContainer, BorderLayout.NORTH);
				
		//Labels Panel
		Panel labels = new Panel(new GridLayout(3,1));
		labels.add(new Label("Source File:"));
		labels.add(new Label("Output File Name: (leave blank to overwrite)"));
		labels.add(new Label("Number of Additional Pages:"));
		superContainer.add(labels, BorderLayout.WEST);
		
		//Fields Container
		Panel fields = new Panel(new GridLayout(3,1));
		
		sourceFile = new TextField("", 30);
		fields.add(sourceFile);
		
		outputFileName = new TextField("", 30);
		fields.add(outputFileName);
		
		numPages = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		fields.add(numPages);
		
		superContainer.add(fields, BorderLayout.CENTER);
		
		//Container for the browse button
		Panel browse = new Panel(new GridLayout(3,1));
		fileSelect = new Button(COMMAND_BROWSE);
		fileSelect.setActionCommand(COMMAND_BROWSE);
		fileSelect.addActionListener(control);
		browse.add(fileSelect);
		superContainer.add(browse, BorderLayout.EAST);
		
		//Set up the execute button
		Panel executePanel = new Panel(new BorderLayout());
		executeTask = new Button(COMMAND_EXECUTE);
		executeTask.setActionCommand(COMMAND_EXECUTE);
		executeTask.addActionListener(control);
		executePanel.add(executeTask, BorderLayout.EAST);
		superContainer.add(executePanel, BorderLayout.SOUTH);
	}
	
	public static DA2062TaskPane GetInstance() {
		if(instance == null) {
			instance = new DA2062TaskPane();
		}
		return instance;
	}
}
