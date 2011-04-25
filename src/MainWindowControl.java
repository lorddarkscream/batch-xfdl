import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainWindowControl implements ItemListener, WindowListener, ListSelectionListener  {

	public static final String FORM_CHOICE_DA2062 = "DA Form 2062";
	public static final String FORM_CHOICE_DA1594 = "DA Form 1594";
	
	public static final String COMMAND_SELECT_FORM = "SELECT_FORM";
	public static final String COMMAND_SELECT_TASK = "SELECT_TASK";
	
	public static final String TASK_DA1594_ADDPAGE = "Add Page";
	
	public static final String TASK_DA2062_ADDPAGE = "Add Page";
	public static final String TASK_DA2062_UPDATE_HEADERS = "Update Header Data";
	
	
	private MainWindow view;
	private JPanel currentTaskPanel;
	
	private String currentForm;
	
	/**
	 * Starts the main window.
	 * @param view Object for the main window
	 */
	public void startView(MainWindow view) {
		this.view = view;
		view.setVisible(true);
	}
	
	
	/**
	 * Responds to changes in the lists
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
	

	}
	
	/**
	 * Reacts to changes in the task form and task lists.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Clear the current task panel
		if(currentTaskPanel != null) {
			view.remove(currentTaskPanel);

		}
		
		//React to a change in the form select
		if(e.getSource() == view.formList && !e.getValueIsAdjusting()) {

			//Add all possible DA2062 actions
			if(((JList)e.getSource()).getSelectedValue().equals(FORM_CHOICE_DA1594)) {
				if(!view.taskListModel.isEmpty()) {
					view.taskListModel.removeAllElements();
					view.taskList.clearSelection();
				}
				view.taskListModel.addElement(TASK_DA2062_ADDPAGE);
				currentForm = FORM_CHOICE_DA1594;
			} else if (((JList)e.getSource()).getSelectedValue().equals(FORM_CHOICE_DA2062)) {
				if(!view.taskListModel.isEmpty()) {
					view.taskListModel.removeAllElements();
					view.taskList.clearSelection();
				}
				view.taskListModel.addElement(TASK_DA2062_ADDPAGE);
				view.taskListModel.addElement(TASK_DA2062_UPDATE_HEADERS);
				currentForm = FORM_CHOICE_DA2062;
			}
			
		
			
			view.pack();
		}
			
		//React to a change in the selected task.
		else if (e.getSource() == view.taskList 
				&& !view.taskList.isSelectionEmpty()
				&& !e.getValueIsAdjusting()) {

			
			try {
			//Display appropriate task information
			if(currentForm == FORM_CHOICE_DA1594) {
				if(((JList)e.getSource()).getSelectedValue()
						.equals(TASK_DA1594_ADDPAGE)) {
					currentTaskPanel = 
						AddPageTaskPane.addPagePaneFactory(
								AddPageTaskController.FORM_DA1594);
					view.add(currentTaskPanel, 
							BorderLayout.CENTER);

					view.pack();
				}
			}
			if(currentForm == FORM_CHOICE_DA2062) {
				if (((JList)e.getSource()).getSelectedValue()
						.equals(TASK_DA2062_ADDPAGE)) {
					currentTaskPanel = AddPageTaskPane.addPagePaneFactory(
							AddPageTaskController.FORM_DA2062); 
					view.add(currentTaskPanel, 
							BorderLayout.CENTER);

					view.pack();
				} else if (((JList)e.getSource()).getSelectedValue()
						.equals(TASK_DA2062_UPDATE_HEADERS)) {
					currentTaskPanel = DA2062HeaderUpdateTaskPane.factory();
					view.add(currentTaskPanel, BorderLayout.CENTER);
					view.pack();
				}
			}
			} catch (Exception excp) {
				JOptionPane.showMessageDialog(view, 
						"Error Loading Task Pane.", 
						"Internal Error.", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/***
	 * Closes the main window and application.
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		view.setVisible(false);
		view.dispose();
		System.exit(0);
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}

