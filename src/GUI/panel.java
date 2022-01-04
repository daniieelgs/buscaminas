package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Component;

import javax.swing.JPanel;

public class panel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel content;
	private map mapa;
	private counter count;
	
	public panel() {
				
		content=new JPanel();
						
		content.setLayout(new BorderLayout());
						
		mapa=new map();
		count=new counter(mapa);
		
		mapa.setCounter(count);
		
		content.add(mapa, BorderLayout.CENTER);
		content.add(count, BorderLayout.NORTH);				
				
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
	
	public void setLevelMap(int[] level) {
				
		content.removeAll();
		
		mapa=new map(level);
		count=new counter(mapa);
		
		mapa.setCounter(count);
		
		content.add(count, BorderLayout.NORTH);
		content.add(mapa, BorderLayout.CENTER);
				
		updateUI();
				
	}

	public counter getCount() {
		return count;
	}

	public map getMap() {
		return mapa;
	}
}
