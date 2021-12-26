package GUI;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

public class frame extends JFrame{

	private static final long serialVersionUID = 1L;

	private panel content;
	
	public frame(String titl, int width, int height) {
		
		super(titl);
		
		setBounds(width*3/8, height/4, width/4, height/2);
		
		content=new panel(width/4, height/2);
		
		add(content, BorderLayout.CENTER);
	
		addWindowListener(new WindowAdapter() {
			
			public void windowOpened(WindowEvent e) {
				resizeContent();
			}
			
		});
		
		addWindowStateListener(new WindowStateListener() {
			
			public void windowStateChanged(WindowEvent e) {
				resizeContent();
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			
			public void componentResized(ComponentEvent e) {
				
				resizeContent();
				
			}
		});
		
	}
	
	private void resizeContent() {
		content.resizeContent(content);
	}
	
	
}
