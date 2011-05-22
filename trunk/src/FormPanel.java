
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<Component, String> components;
	
	public FormPanel() {
		lines = new ArrayList<Rectangle>();
		components = new HashMap<Component, String>();
		
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
	
	/**
	 * Adds a component with the specified SID
	 * @param component Component to be added
	 * @param sid SID of the component
	 * @return Component to be added.
	 */
	public Component add(Component component, String sid) {
		
		components.put(component, sid);
		
		return super.add(component);
	}


}
