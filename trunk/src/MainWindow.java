
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

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
		this.setSize(1200, 1000);
		
		//Border Layout for main window
		this.setLayout(new MigLayout());
		
		this.setTitle("Batch-XFLD");
		this.addWindowListener(control);
		
		
		//Form Selection
		this.add(new JLabel("Forms:"), "flowy, split 4, top");
		
		//list of available forms
		formListModel = new DefaultListModel();
		formListModel.addElement(MainWindowControl.FORM_CHOICE_GENERIC);
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
				
		this.add(formListScroll, "w 300, sg formAndTask");
		
		//Task Selection
		this.add(new JLabel("Tasks:"));
		
		taskListModel = new DefaultListModel();
		taskList = new JList(taskListModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setLayoutOrientation(JList.VERTICAL);
		taskList.setName(MainWindowControl.COMMAND_SELECT_TASK);
		taskList.addListSelectionListener(control);
		
		JScrollPane taskListScroll = new JScrollPane(taskList);
		taskListScroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.add(taskListScroll, "w 300, sg formAndTask");
		
		actionContent = new JPanel();
		this.add(actionContent);

	}
}
