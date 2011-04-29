import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

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
	
	public JButton destBrowseButton;
	public JTextField destFolder;
	
	public JCheckBox toCheck;
	public JCheckBox fromCheck;
	public JCheckBox recptnrCheck;
	public JCheckBox enditmsCheck;
	public JCheckBox itemdesCheck;
	public JCheckBox pubnrCheck;
	public JCheckBox pubdateCheck;
	public JCheckBox quantityCheck;
	
	public JTextField toText;
	public JTextField fromText;
	public JTextField recptnrText;
	public JTextField enditmsText;
	public JTextField itemdesText;
	public JTextField pubnrText;
	public JTextField pubdateText;
	public JTextField quantityText;
	
	public JButton updateButton;
	
	
	private DA2062HeaderUpdateController control;
	

	private DA2062HeaderUpdateTaskPane(DA2062HeaderUpdateController control) {
		
		this.control = control;
		
		this.setLayout(new MigLayout());
		
		JLabel taskCondStand = new JLabel("<html><h1>Batch Update DA2062 Header Data</h1>" +
				"<p><u>Task:</u> Replace the header data on multiple DA2062 files " +
					"in a single operation.</p>" +
				"<p><u>Condition:</u> Given a list of valid DA2062 files, " + 
					"replacement header data and a destination directory.</p>" +
				"<p><u>Standard:</u> " +
				"<ol>" +
				"<li>Header data specified with a check in the fields below " +
				"will be replaced in specified files.</li>" +
				"<li>Resulting files will be written to destination " +
				"directory with their original filename.</li>" +
				"<li>Resulting files will overwrite any file with the same name " +
					"in destination directory.</li>" +
				"<li>Invalid files will be skipped. </li>" +
				"</ol></p></html>");
		
		this.add(taskCondStand, "wrap, span 3");
		
		//*********************************************************************
		//File list section
		//*********************************************************************
		this.add(new JLabel("<html><u>Files:</u></html>"), "wrap");
		
		addButton = new JButton("Add Files...");
		addButton.addActionListener(this.control);
		
		removeButton = new JButton("Remove Files");
		removeButton.addActionListener(this.control);

		
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
		
			
		this.add(addButton, "flowy, split 2, aligny top, sg leftbuttons");
		this.add(removeButton, "aligny top, sg leftbuttons");
		this.add(fileListScrollPane, " spanx 2, wrap, grow");
		
	
		
		//*********************************************************************
		//Destination Folder section
		//*********************************************************************
		this.add(new JLabel("<html><u>Destination Folder:</u></html>"),
				"wrap");
		
		destBrowseButton = new JButton("Browse...");
		destBrowseButton.addActionListener(control);
		this.add(destBrowseButton, "sg leftbuttons");
		
		destFolder = new JTextField();
		this.add(destFolder, "span 2, wrap, grow");
		
	
		
		//*********************************************************************
		//Header Properties section
		//*********************************************************************
		this.add(new JLabel("<html><u>Properties:</u></html>"), "wrap");
		
		
		//Create the to fields
		this.add(new JLabel("To: "), "ax right");
		toCheck = new JCheckBox();
		toCheck.addActionListener(control);
		this.add(toCheck);
		toText = new JTextField();
		toText.setEnabled(false);
		this.add(toText, "wrap, grow");

		
		//From fields
		this.add(new JLabel("From:"), "ax right");
		fromCheck = new JCheckBox();
		fromCheck.addActionListener(control);
		this.add(fromCheck);
		fromText = new JTextField();
		fromText.setEnabled(false);
		this.add(fromText, "wrap, grow");
		
		//Receipt Number fields
		this.add(new JLabel("Receipt Number:"), "ax right");
		recptnrCheck = new JCheckBox();
		recptnrCheck.addActionListener(control);
		this.add(recptnrCheck);
		recptnrText = new JTextField();
		recptnrText.setEnabled(false);
		this.add(recptnrText, "wrap, grow");
		
		//End Item Stock Number
		this.add(new JLabel("End Item Stock Number: "), "ax right");
		enditmsCheck = new JCheckBox();
		enditmsCheck.addActionListener(control);
		this.add(enditmsCheck);
		enditmsText = new JTextField();
		enditmsText.setEnabled(false);
		this.add(enditmsText, "wrap, grow");
		
		//item description
		this.add(new JLabel("Item Description: "), "ax right");
		itemdesCheck = new JCheckBox();
		itemdesCheck.addActionListener(control);
		this.add(itemdesCheck);	
		itemdesText = new JTextField();
		itemdesText.setEnabled(false);
		this.add(itemdesText, "wrap, grow");
		
		//publication number
		this.add(new JLabel("Publication Number: "), "ax right");
		pubnrCheck = new JCheckBox();
		pubnrCheck.addActionListener(control);
		this.add(pubnrCheck);
		pubnrText = new JTextField();
		pubnrText.setEnabled(false);
		this.add(pubnrText, "wrap, grow");
		
		
		this.add(new JLabel("Publication Date:"), "ax right");
		pubdateCheck = new JCheckBox();
		pubdateCheck.addActionListener(control);
		this.add(pubdateCheck);		
		pubdateText = new JTextField();
		pubdateText.setEnabled(false);
		this.add(pubdateText, "wrap, grow");
		
		this.add(new JLabel("Quantity:"), "ax right");		
		quantityCheck = new JCheckBox();
		quantityCheck.addActionListener(control);
		this.add(quantityCheck);		
		quantityText = new JTextField();
		quantityText.setEnabled(false);
		this.add(quantityText, "wrap, grow"); 
		
		//Update button
		updateButton = new JButton("Update");
		updateButton.addActionListener(control);
		this.add(updateButton, "ax right, span 3");
		
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
