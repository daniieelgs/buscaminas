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
	private int dimension, nMines, maxBoxesOpened, numBoxesOpened;
	
	public map(int dimension, int nMines) {
	
		setLayout(new GridLayout(dimension, dimension));
				
		this.dimension=dimension;
		this.nMines=nMines;
		
		maxBoxesOpened=dimension*dimension-nMines;
		
		boxes=new box[dimension][dimension];
						
		createBoxes();
		
		generateMines();
				
	}
	
	public map() {
		
		this(EASY_MODE[0], EASY_MODE[1]);
		
	}
	
	public void createBoxes() {
		
		for(int y=0; y<dimension; y++) {
			
			for(int x=0; x<dimension; x++) {
				
				box b=new box(x, y);
				
				b.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								
				boxes[y][x]=b;
				
				add(b);
				
			}
			
		}
		
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
		
		count.setFlags(nMines);
		count.startTimer();
	}
	
	public void reset() {
		
		removeAll();
		
		createBoxes();
		
		generateMines();
		
		if(count!=null) count.setFlags(nMines);
		
	}
		
	private class box extends JPanel{ //CASILLAS
		
		private static final long serialVersionUID = 1L;

		private JLabel iconLabel;
		private int icon;
		private int number;
		private boolean mine;
		
		final int FLAG=0, DOUBT=1, OPENED=3, NOT_ICON=-1;
		
		private Color color;
		
		private int x, y;
		
		public box(int x, int y) {
			
			setLayout(new BorderLayout());
			
			this.x=x;
			this.y=y;
			
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
					
					if(number!=0 || icon!=OPENED) setBackground(color.darker());
					
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
								
								if(count.getFlags()>0) {
								
									bufferIcon=ImageIO.read(map.class.getResource("Images/flag.png"));
									
									iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
									
									icon=FLAG;
																	
									count.setFlags(count.getFlags()-1);
									
								}
								
								break;
								
							case FLAG:
								
								bufferIcon=ImageIO.read(map.class.getResource("Images/doubt.png"));
								
								iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
								
								icon=DOUBT;
								
								count.setFlags(count.getFlags()+1);
								
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
						open();
						
						if(numBoxesOpened==maxBoxesOpened) {
							count.win();
							openAll();
						}
						
						if(mine) {							
							setBackground(Color.RED);
							color=Color.RED;
							count.stopTimer();
							count.die();
							openAll();
						}
					}
					
				}
				
			});
			
		}
		
		public void openAll() {
			
			for(int y=0; y<dimension; y++) {
				
				for(int x=0; x<dimension; x++) {
					
					boxes[y][x].open();
				}
				
			}
			
		}
		
		public void open() {

			iconLabel.setIcon(null);

			if(icon==FLAG) count.setFlags(count.getFlags()+1);
			
			icon=OPENED;
			
			numBoxesOpened++;
			
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
					
					for(int j=x-1; j<x+2; j++) {
						
						if((y-1)>=0 && j>=0 && j<dimension && boxes[y-1][j].getNumber()!=-1 && boxes[y-1][j].getIcon()!=OPENED) boxes[y-1][j].open();
						
						if((y+1)<dimension && j>=0 && j<dimension && boxes[y+1][j].getNumber()!=-1 && boxes[y+1][j].getIcon()!=OPENED) boxes[y+1][j].open();
						
					}
					
					if((x-1)>=0 && boxes[y][x-1].getNumber()!=-1 && boxes[y][x-1].getIcon()!=OPENED) boxes[y][x-1].open();
					
					if((x+1)<dimension && boxes[y][x+1].getNumber()!=-1 && boxes[y][x+1].getIcon()!=OPENED) boxes[y][x+1].open();
					
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
			
		}
		
		public void setMine(boolean mine) {
			this.mine=mine;
			
			if(mine) number=-1;					
			else number=0;
						
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
		
		public int getIcon() {
			return icon;
		}
				
	}

	
}
