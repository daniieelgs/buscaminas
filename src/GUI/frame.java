package GUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class frame extends JFrame{

	private static final long serialVersionUID = 1L;

	private panel content;
	
	public frame(String titl, int width, int height) {
		
		super(titl);
		
		setBounds(width*3/8, height/4, width/4, height/2);
		
		Image icon;

		try {
			
			icon = new ImageIcon(frame.class.getResource("Images/mine.png")).getImage();
			
	        setIconImage(icon);

		} catch (Exception e1) {
			
			e1.printStackTrace();
			
		}

		
		setResizable(false);
		
		content=new panel();
		
		add(content, BorderLayout.CENTER);
		add(new optionBar(content, this), BorderLayout.NORTH);
		
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
