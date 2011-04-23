import java.io.File;

import javax.swing.filechooser.FileFilter;


public class XFDLFileFilter extends FileFilter {

	@Override
	public boolean accept(File arg0) {
		return arg0.isDirectory() || arg0.getName().toLowerCase().endsWith(".xfdl");
	}

	@Override
	public String getDescription() {
		return "XFDL Files";
	}

}
