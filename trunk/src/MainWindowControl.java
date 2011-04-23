import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.List;


public class MainWindowControl implements ItemListener, WindowListener {

	public static final String FORM_CHOICE_DA2062 = "DA Form 2062";
	
	public static final String COMMAND_SELECT_FORM = "SELECT_FORM";
	
	public static final String TASK_DA2062_ADDPAGE = "Add Page";
	
	private MainWindow view;
	
	
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
	
		//React to a change in the form select
		if(arg0.getSource() == view.formList 
				&& arg0.getStateChange() == ItemEvent.SELECTED) {
			
			//Add all possible DA2062 actions
			if(((List)arg0.getSource()).getSelectedItem() == FORM_CHOICE_DA2062) {
				view.taskList.removeAll();
				view.taskList.add(TASK_DA2062_ADDPAGE);
			}
		} else if (arg0.getSource() == view.taskList 
				&& arg0.getStateChange() == ItemEvent.SELECTED) {
			
			//Display appropriate task information
			if(((List)arg0.getSource()).getSelectedItem() == TASK_DA2062_ADDPAGE) {
				view.add(DA2062TaskPane.GetInstance(), BorderLayout.CENTER);
				view.pack();
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
