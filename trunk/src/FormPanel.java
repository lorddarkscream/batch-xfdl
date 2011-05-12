import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author andy
 * 
 * Customized JPanel that maintains a list of lines to be drawn on the form
 *
 */
public class FormPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408728614801306159L;
	
	private ArrayList<Rectangle> lines;
	
	public FormPanel() {
		lines = new ArrayList<Rectangle>();
	}
	
	public void addLine(Rectangle line) {
		lines.add(line);
	}
	
	/**
	 * Overrides JPanel paintComponenet and draws lines on the form.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		 
		for(int x = 0; x<lines.size(); x++) {
			g.drawRect(lines.get(x).x, 
					lines.get(x).y, 
					lines.get(x).width, 
					lines.get(x).height);
			g.fillRect(lines.get(x).x, 
					lines.get(x).y, 
					lines.get(x).width, 
					lines.get(x).height);
		}
	}

}
