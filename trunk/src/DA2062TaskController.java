import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import java.awt.Component;
import java.awt.Cursor;



class DA2062TaskController implements java.awt.event.ActionListener  {

	private DA2062TaskPane view;
	
	public DA2062TaskController(DA2062TaskPane view) {
		this.view = view;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == DA2062TaskPane.COMMAND_BROWSE) {
			
			final JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new XFDLFileFilter());
			
			int retVal = fc.showOpenDialog(view);
			if(retVal == JFileChooser.APPROVE_OPTION) {
				view.sourceFile.setText(fc.getSelectedFile().getAbsolutePath());
			}
		} else if (arg0.getActionCommand() == DA2062TaskPane.COMMAND_EXECUTE) {
			
			try {
				
				//Set mouse cursor to busy
				view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				//open the file
				DA2062 form = new DA2062(view.sourceFile.getText());
				
				//call the add pages command the appropriate number of times
				SpinnerNumberModel snm = (SpinnerNumberModel)view.numPages.getModel();
				
				for(int x = 0; x<snm.getNumber().intValue(); x++) {
					form.addPage();
				}
				
				//save to the specified location
				//String destination;
				File f = new File(view.sourceFile.getText()); 
				form.saveFile(
						new File(f.getParent() + 
								File.separator +
								view.outputFileName.getText()).getPath(),
						false);
				JOptionPane.showMessageDialog(view,
						"Successfully added " + snm.getNumber().intValue() + " pages.", 
						"Operation Complete", 
						JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException err) {
				JOptionPane.showMessageDialog(view, "Error reading or writing the file from disk.", "I/O Exception", JOptionPane.ERROR_MESSAGE);
			} catch (ParserConfigurationException err) {
				JOptionPane.showMessageDialog(view, "Error parsing XML data", "Parser Exception", JOptionPane.ERROR_MESSAGE);
			} catch (SAXException err) {
				JOptionPane.showMessageDialog(view, "Error processing XML.", "SAX Exception", JOptionPane.ERROR_MESSAGE);
			} catch (XPathExpressionException err) {
				JOptionPane.showMessageDialog(view, "Error searching document.", "XPath Exception", JOptionPane.ERROR_MESSAGE);
			} catch (TransformerException err) {
				JOptionPane.showMessageDialog(view, "Error transforming XML.", "Transformation Exception", JOptionPane.ERROR_MESSAGE);
			} catch (InvalidFormException err) {
				JOptionPane.showMessageDialog(view, "This document does not appear to be a valid DA2062", "Invalid Document Exception", JOptionPane.ERROR_MESSAGE);
			} finally {
				view.setCursor(Cursor.getDefaultCursor());
			}
			
		}
		
	}

}
