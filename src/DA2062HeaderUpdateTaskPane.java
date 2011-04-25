import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * @author andy
 * Class extends JPanel and provides the user with an interface 
 * to update all of the header data on a bulk collection of DA2062s.
 */
public class DA2062HeaderUpdateTaskPane extends JPanel {

	/**
	 * Serialization Version ID
	 */
	private static final long serialVersionUID = -6990573285368329174L;
	
	public JButton addButton;
	public JButton removeButton;
	public JList fileList;
	public DefaultListModel fileListModel;
	
	private DA2062HeaderUpdateController control;
	

	private DA2062HeaderUpdateTaskPane(DA2062HeaderUpdateController control) {
		
		this.control = control;
		
		this.setLayout(new BorderLayout());
		
		JPanel taskCondStandPanel = new JPanel();
		taskCondStandPanel.setLayout(new BoxLayout(taskCondStandPanel, BoxLayout.PAGE_AXIS));
		
		JLabel taskCondStand = new JLabel("<html><h1>Batch Update DA2062 Header Data</h1>" +
				"<p><u>Task:</u> Replace the header data on multiple DA2062 files " +
					"in a single operation</p>" +
				"<p><u>Condition:</u> Given a list of valid DA2062 files, " + 
					"replacement header data and a destination directory.</p>" +
				"<p><u>Standard:</u> All header data in specified files will be " +
					"updated and resulting files will be written to " +
					"destination <br>directory.  Invalid files will be skipped. " +
					"Result files will overwrite any file with the same name " +
					"in <br>destination directory.</p></html>");
		
		taskCondStandPanel.add(taskCondStand);
		taskCondStandPanel.add(Box.createRigidArea(new Dimension(0,10)));
		this.add(taskCondStandPanel, BorderLayout.NORTH);
		
		//*********************************************************************
		//File list section
		//*********************************************************************
		JPanel fileListPanel = new JPanel();
		fileListPanel.setLayout(new BorderLayout());
		fileListPanel.add(new JLabel("<html><u>Files:</u></html>"), 
				BorderLayout.NORTH);
		
		JPanel addRemovePanel = new JPanel();
		addRemovePanel.setLayout(new BoxLayout(addRemovePanel, 
				BoxLayout.PAGE_AXIS));
		addButton = new JButton("Add Files");
		addButton.addActionListener(this.control);
		
		removeButton = new JButton("Remove Files");
		removeButton.addActionListener(this.control);
		
		addRemovePanel.add(addButton);
		addRemovePanel.add(removeButton);
		
		fileListPanel.add(addRemovePanel, BorderLayout.WEST);
		
		//files list box
		fileListModel = new DefaultListModel();
		fileList = new JList(fileListModel);
		fileList.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		fileList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane fileListScrollPane = new JScrollPane(fileList);
		fileListScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		fileListScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		fileListPanel.add(fileListScrollPane, BorderLayout.CENTER);
		
		
		this.add(fileListPanel);
		
		//*********************************************************************
		//Destination Folder section
		//*********************************************************************
		
	}
	
	/**
	 * Creates a DA2062HeaderUpdateTaskPane and links it to the control object.
	 * @return Pane ready for insertion.
	 */
	public static DA2062HeaderUpdateTaskPane factory(){
		DA2062HeaderUpdateController control = 
			new DA2062HeaderUpdateController();
		
		DA2062HeaderUpdateTaskPane result = 
			new DA2062HeaderUpdateTaskPane(control);

		control.setView(result);
		
		return result;
	}

}
