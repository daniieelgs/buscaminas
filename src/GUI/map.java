package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class map extends JPanel{

	private static final long serialVersionUID = 1L;

	static final int[] EASY_MODE= {8, 10};
	
	private box[][] boxes;
	
	private counter count;
	private int dimension, nMines;
	
	public map(int dimension, int nMines) {
	
		setLayout(new GridLayout(dimension, dimension));
				
		this.dimension=dimension;
		this.nMines=nMines;
		
		boxes=new box[dimension][dimension];
						
		for(int y=0; y<dimension; y++) {
			
			for(int x=0; x<dimension; x++) {
				
				box b=new box();
				
				b.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								
				boxes[y][x]=b;
				
				add(b);
				
			}
			
		}
		
		generateMines();
				
	}
	
	public map() {
		
		this(EASY_MODE[0], EASY_MODE[1]);
		
	}
	
	public void generateMines() {
		
		for(int i=0; i<nMines; i++) {
			
			int x, y;
			
			do {
				
				x=(int) (Math.random()*dimension);
				y=(int) (Math.random()*dimension);
												
			}while(boxes[y][x].isMine());
			
			boxes[y][x].setMine(true);
			
			for(int j=x-1; j<x+2; j++) {
				
				if((y-1)>=0 && j>=0 && j<dimension && boxes[y-1][j].getNumber()!=-1) boxes[y-1][j].setNumber(boxes[y-1][j].getNumber()+1);
				
				if((y+1)<dimension && j>=0 && j<dimension && boxes[y+1][j].getNumber()!=-1) boxes[y+1][j].setNumber(boxes[y+1][j].getNumber()+1);
				
			}
			
			if((x-1)>=0 && boxes[y][x-1].getNumber()!=-1) boxes[y][x-1].setNumber(boxes[y][x-1].getNumber()+1);
			
			if((x+1)<dimension && boxes[y][x+1].getNumber()!=-1) boxes[y][x+1].setNumber(boxes[y][x+1].getNumber()+1);
			
		}
		
	}
	
	public void setCounter(counter count) {
		this.count=count;
	}
		
	private class box extends JPanel{ //CASILLAS
		
		private static final long serialVersionUID = 1L;

		private JLabel iconLabel;
		private int icon;
		private int number;
		private boolean mine;
		
		final int FLAG=0, DOUBT=1, OPENED=3, NOT_ICON=-1;
		
		private Color color;
		
		public box() {
			
			setLayout(new BorderLayout());
			
			iconLabel=new JLabel("", SwingConstants.CENTER);
			
			iconLabel.setFont(new Font("Verdana", Font.BOLD, 20));
			
			icon=NOT_ICON;
			
			number=0;
						
			color=Color.GRAY;
			
			setBackground(color);
			
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			add(iconLabel, BorderLayout.CENTER);
			
			addMouseListener(new MouseAdapter() {
				
				public void mouseEntered(MouseEvent e) {
					
					setBackground(Color.GRAY.darker());
					
				}
				
				public void mouseExited(MouseEvent e) {
					
					setBackground(color);
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
					if(e.getButton()==MouseEvent.BUTTON3) { //CLICK DERECHO
						
						BufferedImage bufferIcon=null;
						
						try {
						
							switch(icon) {
							
							case NOT_ICON:
								
								bufferIcon=ImageIO.read(map.class.getResource("Images/flag.png"));
								
								iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
								
								icon=FLAG;
																
								break;
								
							case FLAG:
								
								bufferIcon=ImageIO.read(map.class.getResource("Images/doubt.png"));
								
								iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
								
								icon=DOUBT;
								
								break;
								
							case DOUBT:
								
								iconLabel.setIcon(null);
								
								icon=NOT_ICON;
								
								break;
							
							}
							
						}catch(IOException ex) {
							ex.printStackTrace();
						}
												
					}else {
						
						iconLabel.setIcon(null);
						
						if(mine) {
							
							BufferedImage bufferIcon=null;
							
							try {
								bufferIcon=ImageIO.read(map.class.getResource("Images/mine.png"));
								iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
												
							
						}else {
															
							iconLabel.setText("" + number);
														
							switch(number) {
								
							case 0:
								color=Color.GRAY.darker();
								setBackground(color);
								iconLabel.setForeground(color);
								break;
							
							case 1:
								iconLabel.setForeground(Color.CYAN.darker());
								break;
								
							case 2:
								iconLabel.setForeground(Color.GREEN.brighter());
								break;
								
							case 3:
								iconLabel.setForeground(Color.RED.darker());
								break;
								
							case 4:
								iconLabel.setForeground(Color.BLUE.darker());
								break;
								
							default:
								iconLabel.setForeground(Color.WHITE.darker());
								break;
								
							}
								
						}
						
						icon=OPENED;
						
					}
					
				}
				
			});
			
		}
		
		public void setMine(boolean mine) {
			this.mine=mine;
			
			if(mine) number=-1;					
			else {
				
				//TODO
				
			}
						
		}
		
		public boolean isMine() {
			return mine;
		}
		
		public void setNumber(int number) {
			this.number=number;
		}
		
		public int getNumber() {
			return number;
		}
				
	}

	
}
