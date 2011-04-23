
public class BatchXFDL {

	/**
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		 
			try {	
			
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
