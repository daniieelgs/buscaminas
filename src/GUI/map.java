package GUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.GridLayout;

public class map extends JPanel{

	private static final long serialVersionUID = 1L;

	static final int EASY_MODE=8;
	
	private box[][] boxes;
	
	public map(int dimension) {
	
		setLayout(new GridLayout(dimension, dimension));
				
		boxes=new box[dimension][dimension];
						
		for(int y=0; y<dimension; y++) {
			
			for(int x=0; x<dimension; x++) {
				
				box b=new box();
				
				b.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								
				boxes[y][x]=b;
				
				add(b);
				
			}
			
		}
				
	}
	
	public map() {
		
		this(EASY_MODE);
		
	}
		
	private class box extends JPanel{ //CASILLAS
		
		private static final long serialVersionUID = 1L;

		public box() {
			
			setBackground(Color.GRAY);
			
		}
		
	}

	
}
