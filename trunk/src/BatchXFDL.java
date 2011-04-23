
public class BatchXFDL {

	/**
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		 
			try {	
			
				/**
				DA2062 hr = new DA2062("/Users/andy/Documents/workspace/Batch_XFDL/XFDLFiles/HandReceipt.xfdl");
				hr.addPage();
				//hr.printFields();
				hr.saveFile("/Users/andy/Documents/workspace/Batch_XFDL/XFDLFiles/withThreePages.xfdl", false);
				*/
				
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
