import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;


public class BatchCreateControl implements ActionListener {

	private BatchCreatePane view;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		if(arg0.getSource().equals(view.sourceFormBrowse)) {
			setSourceForm();
		}

	}
	
	/**
	 * Opens a file selection window and allows the user to select an XFDL form
	 * resulting path is placed into view.sourceForm
	 */
	private void setSourceForm() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XFDLFileFilter());
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int retVal = fc.showDialog(view, "OK");
		if(retVal == JFileChooser.APPROVE_OPTION) {
			view.sourceForm.setText(fc.getSelectedFile().getPath());
		}
	}

	public void setView(BatchCreatePane view) {
		this.view = view;
	}

}
