import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Label;
import java.awt.List;

public class MainWindow extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3880026026104218593L;
	
	public List formList;
	public List taskList;
	public Panel actionContent;

	public MainWindow(MainWindowControl control) 
	{
		this.setLocationRelativeTo(null);
		
		//Border Layout for main window
		this.setLayout(new BorderLayout());
		
		this.setTitle("BatchXFLD");
		this.addWindowListener(control);
		
		//Panel for task selection pane
		Panel taskPane = new Panel(new BorderLayout());
		this.add(taskPane, BorderLayout.WEST);
		
		//Form Selection
		Panel formSelectPanel = new Panel(new BorderLayout());
		taskPane.add(formSelectPanel, BorderLayout.NORTH);
		Label forms = new Label();
		forms.setText("Forms:");
		formSelectPanel.add(forms, BorderLayout.NORTH);
		
		//list of available forms
		formList = new List();
		formList.setName(MainWindowControl.COMMAND_SELECT_FORM);
		formList.add(MainWindowControl.FORM_CHOICE_DA2062);
		formList.addItemListener(control);
		
		formSelectPanel.add(formList, BorderLayout.CENTER);
		
		//Task Selection
		Panel formTasksPanel = new Panel(new BorderLayout());
		taskPane.add(formTasksPanel, BorderLayout.CENTER);
		
		formTasksPanel.add(new Label("Tasks:"), BorderLayout.NORTH);
		
		taskList = new List();
		taskList.addItemListener(control);
		formTasksPanel.add(taskList, BorderLayout.CENTER);
		
		this.pack();
	}
}
