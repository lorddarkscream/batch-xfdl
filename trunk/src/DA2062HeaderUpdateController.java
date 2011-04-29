import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;


/**
 * 
 */

/**
 * @author andy
 *
 */
public class DA2062HeaderUpdateController implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2295024385729347780L;
	
	private DA2062HeaderUpdateTaskPane view;
	
	public DA2062HeaderUpdateController() {
		
	}

	/**
	 * Sets the view to be controlled.
	 * @param view DA2062HeaderUpdateTaskPane object to be controlled.
	 */
	public void setView(DA2062HeaderUpdateTaskPane view) {
		this.view = view;
	}

	
	/**
	 * Reacts to actions performed by the view.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//add and remove buttons for file selection
		if(arg0.getSource().equals(view.addButton)) {
			executeAddButton();
		} else if(arg0.getSource().equals(view.removeButton)) {
			executeRemoveButton();
		}
		
		//browse button for destination directory
		else if(arg0.getSource().equals(view.destBrowseButton)) {
			executeDesBrowsButton();
		}
		
		//Turn header text fields on and off
		else if(arg0.getSource().equals(view.toCheck)) {
			view.toText.setEnabled(view.toCheck.isSelected());
		} else if(arg0.getSource().equals(view.fromCheck)) {
			view.fromText.setEnabled(view.fromCheck.isSelected());
		} else if(arg0.getSource().equals(view.recptnrCheck)) {
			view.recptnrText.setEnabled(view.recptnrCheck.isSelected());
		} else if(arg0.getSource().equals(view.enditmsCheck)) {
			view.enditmsText.setEnabled(view.enditmsCheck.isSelected());
		} else if(arg0.getSource().equals(view.itemdesCheck)) {
			view.itemdesText.setEnabled(view.itemdesCheck.isSelected());
		} else if(arg0.getSource().equals(view.pubnrCheck)) {
			view.pubnrText.setEnabled(view.pubnrCheck.isSelected());
		} else if(arg0.getSource().equals(view.pubdateCheck)) {
			view.pubdateText.setEnabled(view.pubdateCheck.isSelected());
		} else if(arg0.getSource().equals(view.quantityCheck)) {
			view.quantityText.setEnabled(view.quantityCheck.isSelected());
		}
		
		//update button has been pushed
		else if(arg0.getSource().equals(view.updateButton)) {
			executeUpdateButton();
		}
		
	}
	
	private void executeUpdateButton() {
		try{
			//set the cursor for busy
			view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			File destDirectory;
			
			//validate that destination folder exists
			if(!view.destFolder.getText().equals(null)) {
				destDirectory = new File(view.destFolder.getText());

				if(!destDirectory.exists()) {
					throw new FileNotFoundException(
						"Destination directory could not be found.");
				}
			} else {
				throw new FileNotFoundException(
					"Destination directory could not be found.");
			}

			//for each file in the source box create a DA2062 object
			for(int x=0; x<view.fileListModel.getSize(); x++) {
				
				//validate that our file still exists
				File f = new File(view.fileListModel.get(x).toString());
				if(f.exists()) {
					DA2062 currentFile;
					
					try {
						currentFile = new DA2062(f.getPath());
					} catch (InvalidFormException ife) {
						//skip file
						break;
					}
					
					//update all selected fields
					if(view.toCheck.isSelected()) {
						currentFile.setTo(view.toText.getText());
					}
					
					if(view.fromCheck.isSelected()) {
						currentFile.setFrom(view.fromText.getText());
					}
					
					if(view.recptnrCheck.isSelected()) {
						currentFile.setReceiptNumber(view.recptnrText.getText());
					}
					
					if(view.enditmsCheck.isSelected()) {
						currentFile.setEndItem(view.enditmsText.getText());
					}
					
					if(view.itemdesCheck.isSelected()) {
						currentFile.setItemDescription(view.itemdesText.getText());
					}
					
					if(view.pubnrCheck.isSelected()) {
						currentFile.setPublicationNumber(view.pubnrText.getText());
					}
					
					if(view.pubdateCheck.isSelected()) {
						currentFile.setPublicationDate(view.pubdateText.getText());
					}
					
					if(view.pubdateCheck.isSelected()) {
						currentFile.setPublicationDate(view.pubdateText.getText());
					}
					
					if(view.quantityCheck.isSelected()) {
						currentFile.setQuantity(view.quantityText.getText());
					}
										

					//save file to destination path + original file name
					currentFile.saveFile(
							destDirectory + File.separator + f.getName(), 
							false);
				}
			}
		
		
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(view, 
					"Destination directory could not be found " +
						"or is not write accessable.", 
					"Invalid Destination Directory", 
				JOptionPane.ERROR_MESSAGE);
		} catch (IOException err) {
			JOptionPane.showMessageDialog(view, 
					"Error reading or writing the file from disk.", 
					"I/O Exception", 
					JOptionPane.ERROR_MESSAGE);
		} catch (ParserConfigurationException err) {
			JOptionPane.showMessageDialog(view, 
					"Error parsing XML data", 
					"Parser Exception", 
					JOptionPane.ERROR_MESSAGE);
		} catch (SAXException err) {
			JOptionPane.showMessageDialog(view, "Error processing XML.", 
					"SAX Exception", 
					JOptionPane.ERROR_MESSAGE);
		} catch (XPathExpressionException err) {
			JOptionPane.showMessageDialog(view, 
					"Error searching document.", 
					"XPath Exception", 
					JOptionPane.ERROR_MESSAGE);
		} catch (TransformerException err) {
			JOptionPane.showMessageDialog(view, 
					"Error transforming XML.", 
					"Transformation Exception", 
					JOptionPane.ERROR_MESSAGE);
		} catch (InvalidFieldException err) {
			JOptionPane.showMessageDialog(view, 
					"Unable to find a specified field while " +
					"searching the document.  This may be an invalid DA2062", 
					"Invalid Field Exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			//clear cursor busy in finally block
			view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	/**
	 * Creates a JFileChooser and places the resulting folder selection
	 * into the destination folder view.
	 */
	private void executeDesBrowsButton() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XFDLFileFilter());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int retVal = fc.showDialog(view, "OK");
		if(retVal == JFileChooser.APPROVE_OPTION) {
			view.destFolder.setText(fc.getSelectedFile().getPath());
		}
	}

	private void executeAddButton()	 {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XFDLFileFilter());
		fc.setMultiSelectionEnabled(true);
		
		int retVal = fc.showOpenDialog(view);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			File files[] = fc.getSelectedFiles();
			for(int x=0; x<fc.getSelectedFiles().length; x++) {
				if(!view.fileListModel.contains(files[x])) {
					view.fileListModel.addElement(files[x]);
				}
			}
		}
	}
	
	private void executeRemoveButton() {
		int selected[] = view.fileList.getSelectedIndices();
		for(int x=selected.length-1; x>=0; x--) {
			view.fileListModel.remove(selected[x]);
		}
	}

}
