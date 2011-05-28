import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

public class FormViewControl implements ActionListener {

	private FormViewPane view;
	XFDLDocumentDisplayer doc;
	private int currentPage;
	
	public FormViewControl(FormViewPane view) {
		this.view = view;
		
		view.nextPage.setEnabled(false);
		view.previousPage.setEnabled(false);
		
		currentPage = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try{
			if(arg0.getSource().equals(view.nextPage)) {
				nextPagePushed();
			}
			else if(arg0.getSource().equals(view.previousPage)) {
				previousPagePushed();
			}
			else if(arg0.getSource().equals(view.selectForm)) {

				selectFormPushed();
			}
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(view, 
					"Error reading XFDL file.", 
					"Internal Error.", 
					JOptionPane.ERROR_MESSAGE);
			
		} catch(XMLStreamException xmle) {
			JOptionPane.showMessageDialog(view, 
					"Error loading XFDL document.", 
					"Internal Error.", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Advances the view to the next page in the form.
	 * 
	 * Disables the Next Page button if the view is at the last page.
	 */
	private void nextPagePushed() {
		currentPage++;
		view.scrollPane.setViewportView(doc.getPage(currentPage).displayPage());
			
			if(doc.getPageCount() - 1 == currentPage) {
				view.nextPage.setEnabled(false);
			}
			
			view.previousPage.setEnabled(true);
	}
	
	/**
	 * Moves the view back to the previous page.
	 * 
	 * Disables the Previous Page button if the view is at the first page.
	 */
	private void previousPagePushed() {
		currentPage--;

		view.scrollPane.setViewportView(doc.getPage(currentPage).displayPage());

		if(currentPage == 0) {
			view.previousPage.setEnabled(false);
		}
		
		if(currentPage < doc.getPageCount() - 1) {
			view.nextPage.setEnabled(true);
		}
	}

	
	private void selectFormPushed() throws XMLStreamException, IOException {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XFDLFileFilter());
		
		int retVal = fc.showOpenDialog(view);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			FormRenderer render = new FormRenderer(fc.getSelectedFile().getAbsolutePath());
			
			doc = render.renderForm(this);
			
			view.scrollPane.setViewportView(doc.getPage(currentPage).displayPage());		

			if(doc.getPageCount() > 1) {
				view.nextPage.setEnabled(true);
			}
		}
	}

}
