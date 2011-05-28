
import java.awt.Component;
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

	//Listing of all of the lines to be drawn on the form.
	private ArrayList<Rectangle> lines;
	
	public FormPanel() {
		lines = new ArrayList<Rectangle>();
		this.setLayout(null);
	}
	
	public void addLine(Rectangle line) {
		lines.add(line);
	}
	
	/**
	 * Overrides JPanel paintComponenet and draws lines on the page as well
	 * as fills ComboBoxes, and Lists with appropriate values.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		 
		//Draw lines on the component
		for(int x = 0; x<lines.size(); x++) {
			g.fillRect(lines.get(x).x, 
					lines.get(x).y, 
					lines.get(x).width, 
					lines.get(x).height);
		}
		
	}
	
	/**
	 * Overrides the base class implementation of add to set the panels width 
	 * and height as the components are added in order to keep the size large 
	 * enough for all components.
	 */
	public Component add(Component component) {
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		if(component.getX() + component.getWidth() > width) {
			width = component.getX() + component.getWidth();
		}
		if(component.getY() + component.getHeight() > height) {
			height = component.getY() + component.getHeight();
		}
		
		this.setSize(width, height);
		this.setPreferredSize(this.getSize());
		
		return super.add(component);
	}

}
