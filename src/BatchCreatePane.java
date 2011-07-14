import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class BatchCreatePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5695455157257151258L;

	
	BatchCreateControl control;
	
	public JTextField sourceForm;
	public JTextField sourceData;
	public JTextField fileNameFormat;
	public JTextField destinationFolder;
	
	public JButton sourceFormBrowse;
	public JButton sourceFormUpdate;
	public JButton sourceDataBrowse;
	public JButton sourceDataUpdate;
	public JButton destinationFolderBrowse;
	
	private BatchCreatePane(BatchCreateControl control) {
		
		this.setControl(control);
		
		this.setLayout(new MigLayout());
		
		JLabel taskCondStandard = new JLabel("<html>" +
		"<h1>Batch Generate Form from Spreadsheet</h1><br>" +
		"<u>Task:</u> Generate completed copies of XFDL forms from based on " +
		"data enclosed in a spreadsheet.<br>" +
		"<u>Conditions:</u> User provides the source form, spreadsheet with " +
		"input data, output filename format, and output folder.  User " + 
		"associates spreadsheet columns with fields in XFDL form. <br>" +
		"<u>Standards:</u> Once user provides required information the " +
		"program will generate one file for each row of data in the " +
		"spreadsheet.</html>");
		
		this.add(taskCondStandard, "span 4, wrap");
		this.add(new JLabel("<html><h2><u>Input</u></h2></html>"), "span 4, wrap");
		
		this.add(new JLabel("Source Form:"));
		
		sourceForm = new JTextField(30);
		this.add(sourceForm, "sg paramatersInputs, grow, pushx");
		
		sourceFormBrowse = new JButton("Browse");
		sourceFormBrowse.addActionListener(control);
		this.add(sourceFormBrowse, "sg browseButtons");
		
		sourceFormUpdate = new JButton("Update");
		this.add(sourceFormUpdate, "sg browseButtons, wrap");
		
		this.add(new JLabel("Source Data:"));
		
		sourceData = new JTextField(30);
		this.add(sourceData, "sg parametersInputs, grow, pushx");
		
		sourceDataBrowse = new JButton("Browse");
		this.add(sourceDataBrowse, "sg browseButtons");
		
		sourceDataUpdate = new JButton("Update");
		this.add(sourceDataUpdate, "sg browseButtons, wrap");
		
		this.add(new JLabel("<html><h2><u>Output</u></h2></html>"), "span 4, wrap");
		
		this.add(new JLabel("Filename Format:"));
		
		fileNameFormat = new JTextField(30);
		this.add(fileNameFormat, "sg paramatersInputs, pushx, grow, wrap");
		
		this.add(new JLabel("Destination Folder:"));
		
		destinationFolder = new JTextField(30);
		this.add(destinationFolder, "sg parametersInputs, pushx, grow");
		
		destinationFolderBrowse = new JButton("Browse");
		this.add(destinationFolderBrowse, "sg browsebuttons, wrap");
	}
	
	/**
	 * Sets the Panel's controller
	 * @param control
	 */
	public void setControl(BatchCreateControl control) {
		this.control = control;
	}
	
	/**
	 * Factory method for BatchCreatePane.
	 * @return
	 */
	public static BatchCreatePane newBatchCreatePane() {
		
		BatchCreateControl control = new BatchCreateControl();
		BatchCreatePane result = new BatchCreatePane(control);
		
		control.setView(result);
		
		return result;
	}
}
