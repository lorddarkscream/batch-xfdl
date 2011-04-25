
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3880026026104218593L;
	
	public JList formList;
	public JList taskList;
	public DefaultListModel formListModel;
	public DefaultListModel taskListModel;
	public JPanel actionContent;

	public MainWindow(MainWindowControl control) 
	{
		this.setLocationRelativeTo(null);
		
		//Border Layout for main window
		this.setLayout(new BorderLayout());
		
		this.setTitle("BatchXFLD");
		this.addWindowListener(control);
		
		//Panel for task selection pane
		JPanel taskPane = new JPanel(new BorderLayout());
		this.add(taskPane, BorderLayout.WEST);
		
		//Form Selection
		JPanel formSelectPanel = new JPanel(new BorderLayout());
		taskPane.add(formSelectPanel, BorderLayout.NORTH);
		JLabel forms = new JLabel();
		forms.setText("Forms:");
		formSelectPanel.add(forms, BorderLayout.NORTH);
		
		//list of available forms
		formListModel = new DefaultListModel();
		formListModel.addElement(MainWindowControl.FORM_CHOICE_DA1594);
		formListModel.addElement(MainWindowControl.FORM_CHOICE_DA2062);
		
		formList = new JList(formListModel);
		formList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		formList.setLayoutOrientation(JList.VERTICAL);
		formList.setName(MainWindowControl.COMMAND_SELECT_FORM);
		
		formList.addListSelectionListener(control);
		
		JScrollPane formListScroll = new JScrollPane(formList);
		formListScroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		formSelectPanel.add(formListScroll, BorderLayout.CENTER);
		formSelectPanel.add(Box.createRigidArea(new Dimension(15,0)), BorderLayout.EAST);
		
		//Task Selection
		JPanel formTasksPanel = new JPanel(new BorderLayout());
		taskPane.add(formTasksPanel, BorderLayout.CENTER);
		
		formTasksPanel.add(new JLabel("Tasks:"), BorderLayout.NORTH);
		
		taskListModel = new DefaultListModel();
		taskList = new JList(taskListModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setLayoutOrientation(JList.VERTICAL);
		taskList.setName(MainWindowControl.COMMAND_SELECT_TASK);
		taskList.addListSelectionListener(control);
		
		JScrollPane taskListScroll = new JScrollPane(taskList);
		taskListScroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		formTasksPanel.add(taskListScroll, BorderLayout.CENTER);
		
		formTasksPanel.add(Box.createRigidArea(new Dimension(15,0)), BorderLayout.EAST);
		
		this.add(Box.createRigidArea(new Dimension(15,0)), BorderLayout.EAST);
		this.pack();
	}
}
