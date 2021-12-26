package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Component;
import javax.swing.JPanel;

public class panel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel content;
	
	public panel(int width, int height) {
				
		content=new JPanel();
						
		content.setLayout(new BorderLayout());
				
		content.add(new map());
						
		add(content);
				
	}
	
	public void resizeContent(int width, int height) {
		
		int sizePanel=((width > height ? height : width));
		
		content.setPreferredSize(new Dimension(sizePanel, sizePanel));
		
		updateUI();
		
	}
	
	public void resizeContent(Component c) {
		
		resizeContent(c.getWidth(), c.getHeight()-10);
		
	}
}
