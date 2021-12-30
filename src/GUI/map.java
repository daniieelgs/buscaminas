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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class map extends JPanel{

	private static final long serialVersionUID = 1L;

	static final int[] EASY_MODE= {8, 10}, MEDIUM_MODE= {16, 40}, EXPERT_MODE= {32, 99};
	
	private box[][] boxes;
	private box[] mines;
	
	private counter count;
	private int dimension, nMines, maxBoxesOpened, numBoxesOpened;
	private boolean started, playing;
	
	public map(int dimension, int nMines) {
	
		setLayout(new GridLayout(dimension, dimension));
				
		this.dimension=dimension;
		this.nMines=nMines;
		
		mines=new box[nMines];
		
		playing=true;
		
		maxBoxesOpened=dimension*dimension-nMines;
		
		boxes=new box[dimension][dimension];
						
		createBoxes();
		
		generateMines();
				
	}
	
	public map(int[] level) {
		this(level[0], level[1]);
	}
	
	public map() {		
		this(EASY_MODE);
	}
	
	public void createBoxes() {
		
		for(int y=0; y<dimension; y++) {
			
			for(int x=0; x<dimension; x++) {
				
				box b=new box(x, y);
												
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
			
			mines[i]=boxes[y][x];
			
			System.out.println(x + " - " + y);
			
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
		count.setButton(counter.HAPPY);
		
	}
	
	public void reset() {
		
		started=false;
		playing=true;
		
		removeAll();
		
		createBoxes();
		
		generateMines();
		
		if(count!=null) {
			count.setFlags(nMines);
			count.setButton(counter.HAPPY);
		}
		
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
		
		private int typeButtonCount;
		
		public box(int x, int y) {
			
			setLayout(new BorderLayout());
			
			this.x=x;
			this.y=y;
			
			iconLabel=new JLabel("", SwingConstants.CENTER);
			
			iconLabel.setFont(new Font("Verdana", Font.BOLD, 20));
			
			icon=NOT_ICON;
			
			number=0;
						
			color=new Color(189, 189, 189);
			
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			
			setBackground(color);
			
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			add(iconLabel, BorderLayout.CENTER);
			
			addMouseListener(new MouseAdapter() {
				
				public void mouseEntered(MouseEvent e) {
					
					if(icon!=OPENED) setBackground(color.darker());
					
				}
				
				public void mousePressed(MouseEvent e) {
					
					if(count!=null && playing) {
						
						typeButtonCount=count.getButton();
						count.setButton(counter.SORPRES);
						
					}
					
				}
				
				public void mouseReleased(MouseEvent e) {
					
					if(playing) {
						
						setBackground(color);
						
						if(count!=null) count.setButton(typeButtonCount);
						
						if(e.getButton()==MouseEvent.BUTTON3) rightClick();						
						else leftClick();
						
					}
											
				}
				
				public void mouseExited(MouseEvent e) {
					
					setBackground(color);
					
				}
								
			});
			
		}
		
		private void rightClick() { //CLICK DERECHO
			
			BufferedImage bufferIcon=null;
			
			try {
			
				switch(icon) {
				
				case NOT_ICON:
					
					if(count!=null && count.getFlags()>0) {
					
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
					
					if(count!=null) count.setFlags(count.getFlags()+1);
					
					break;
					
				case DOUBT:
					
					iconLabel.setIcon(null);
					
					icon=NOT_ICON;
					
					break;
				
				}
				
			}catch(IOException ex) {
				ex.printStackTrace();
			}
									
		}
		
		private void leftClick() {
				
			if(!started) {
				count.startTimer();
				started=true;
			}
			
			if(icon!=OPENED) open();
			
			if(numBoxesOpened==maxBoxesOpened) {
			
				if(count!=null) {
					count.stopTimer();
					count.setButton(counter.WIN);
					count.win();
				}

			}
			
			if(mine) {
				
				if(count!=null) {
					count.stopTimer();
					count.setButton(counter.DIE);
				}
							
				setBackground(Color.RED);
				color=Color.RED;
				
				openMines();
				
				playing=false;
				
			}else open();
		
			
		}
		
		private void openMines() {

			ExecutorService pool=Executors.newFixedThreadPool(mines.length);
			
			for(int i=0; i<mines.length; i++) {
				
				final int box=i;
				
				pool.execute(new Runnable() {
					
					public void run() {
						mines[box].open();
					}
					
				});
				
			}
			
		}
		
		public void open() {
					
			setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			
			if(mine && icon!=OPENED) {
					
				icon=OPENED;
				
				iconLabel.setIcon(null);
				
				BufferedImage bufferIcon=null;
				
				try {
					bufferIcon=ImageIO.read(map.class.getResource("Images/mine.png"));
					iconLabel.setIcon(new ImageIcon(bufferIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			}else if(icon!=OPENED){
									
				if(icon==FLAG && count!=null) count.setFlags(count.getFlags()+1);
						
				icon=OPENED;
				
				numBoxesOpened++;
				
				iconLabel.setIcon(null);
				
				iconLabel.setText("" + number);
										
				switch(number) {
					
				case 0:
					iconLabel.setText("");
					
					for(int j=x-1; j<x+2; j++) {
						
						if((y-1)>=0 && j>=0 && j<dimension && boxes[y-1][j].getNumber()!=-1 && boxes[y-1][j].getIcon()!=OPENED) boxes[y-1][j].open();
						
						if((y+1)<dimension && j>=0 && j<dimension && boxes[y+1][j].getNumber()!=-1 && boxes[y+1][j].getIcon()!=OPENED) boxes[y+1][j].open();
						
					}
					
					if((x-1)>=0 && boxes[y][x-1].getNumber()!=-1 && boxes[y][x-1].getIcon()!=OPENED) boxes[y][x-1].open();
					
					if((x+1)<dimension && boxes[y][x+1].getNumber()!=-1 && boxes[y][x+1].getIcon()!=OPENED) boxes[y][x+1].open();
					
					break;
				
				case 1:
					iconLabel.setForeground(Color.blue.brighter());
					break;
					
				case 2:
					iconLabel.setForeground(Color.GREEN.darker().darker());
					break;
					
				case 3:
					iconLabel.setForeground(Color.RED.darker());
					break;
					
				case 4:
					iconLabel.setForeground(Color.BLUE.darker().darker());
					break;
					
				case 5:
					iconLabel.setForeground(Color.red.darker().darker());
					break;

				case 6:
					iconLabel.setForeground(Color.CYAN.darker());
					break;
					
				case 7:
					iconLabel.setForeground(Color.BLACK);
					break;
				
				case 8:
					iconLabel.setForeground(Color.WHITE.brighter());
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
