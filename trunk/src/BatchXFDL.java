import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class BatchXFDL {

	/**
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		 
			try {	
				
				FormRenderer r = new FormRenderer(
						"/Users/andy/Documents/workspace/Batch-XFDL/XFDLFiles/withThreePages.xml");
				r.renderForm(null);
				JFrame renderFrame = new JFrame();
				JScrollPane scroller = new JScrollPane(r.getForm().get(0));
				renderFrame.add(scroller);
				renderFrame.setVisible(true);
				renderFrame.pack();
				
			
				//Display Main Window
				MainWindowControl controller = new MainWindowControl();
				MainWindow view = new MainWindow(controller);
				
				controller.startView(view);
			}

			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

	}

}
