import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;


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
		if(arg0.getSource().equals(view.addButton)) {
			executeAddButton();
		} else if(arg0.getSource().equals(view.removeButton)) {
			executeRemoveButton();
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
