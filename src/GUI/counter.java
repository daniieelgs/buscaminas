package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class counter extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;

	private JLabel flags, timer;
	private JButton reset;
	private ImageIcon happy, sad, died, win, sorpres;
	private int nFlags, nTimer;
	private Thread threadTimer, threadAnimation;
	final static int HAPPY=0, SAD=1, DIE=3, WIN=4, SORPRES=5;
	private int type, preType;
	
	public counter(map mapa, int size) {
		
		setPreferredSize(new Dimension(0, size));
		
		setLayout(new BorderLayout());
		
		Font style=digitalFont(Font.PLAIN, 25);
		
		flags=new JLabel("0 0 0");
		flags.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		flags.setFont(style);
		flags.setOpaque(true);
		flags.setForeground(Color.RED);
		flags.setBackground(Color.BLACK);
		
		timer=new JLabel("0 0 0");
		timer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		timer.setFont(style);
		timer.setOpaque(true);
		timer.setForeground(Color.RED);
		timer.setBackground(Color.BLACK);
		
		nFlags=0;
		nTimer=0;
		
		BufferedImage icon=null;
				
		try {
			
			icon=ImageIO.read(counter.class.getResource("Images/happy.png"));
			happy=new ImageIcon(icon.getScaledInstance(size, size, Image.SCALE_DEFAULT));
			
			icon=ImageIO.read(counter.class.getResource("Images/sad.png"));
			sad=new ImageIcon(icon.getScaledInstance(size, size, Image.SCALE_DEFAULT));

			icon=ImageIO.read(counter.class.getResource("Images/died.png"));
			died=new ImageIcon(icon.getScaledInstance(size, size, Image.SCALE_DEFAULT));
			
			icon=ImageIO.read(counter.class.getResource("Images/win.png"));
			win=new ImageIcon(icon.getScaledInstance(size, size, Image.SCALE_DEFAULT));
			
			icon=ImageIO.read(counter.class.getResource("Images/sorpres.png"));
			sorpres=new ImageIcon(icon.getScaledInstance(size, size, Image.SCALE_DEFAULT));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		reset=new JButton();
	
		reset.setOpaque(false);
		reset.setFocusPainted(false);
		reset.setBorderPainted(false); 
		reset.setContentAreaFilled(false); 
		reset.setPreferredSize(new Dimension(size, size));
		
		reset.setCursor(new Cursor(Cursor.HAND_CURSOR));		
		
		setButton(HAPPY);
				
		JPanel resetPanel=new JPanel(new GridBagLayout());
					
		GridBagConstraints c=new GridBagConstraints();
		
		c.gridx=0;
		c.gridy=0;
		c.ipadx=size;
		c.ipady=size;
		c.fill=GridBagConstraints.BOTH;
		
		resetPanel.add(reset);
	
		add(flags, BorderLayout.WEST);
		add(resetPanel, BorderLayout.CENTER);
		add(timer, BorderLayout.EAST);
			
		reset.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				
				if(type!=WIN) {
					
					preType=type;
					setButton(SAD);
				}
				
			}
			
			public void mouseExited(MouseEvent e) {
				
				if(type==DIE) setButton(DIE);
				else if(type!=WIN) setButton(preType);
				
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if(threadAnimation!=null) threadAnimation.interrupt();
				
				stopTimer();
				resetTimer();
								
				nFlags=0;
				flags.setText("");
				
				mapa.reset();
				
				preType=type;
								
			}
			
		});
		
	}
	
	public counter(map mapa) {
		this(mapa, 30);
	}
	
	public void setButton(int t) {
		
		type=t;
		
		if(type==HAPPY) reset.setIcon(happy);
		else if(type==SAD) reset.setIcon(sad);
		else if(type==WIN) reset.setIcon(win);
		else if(type==DIE) reset.setIcon(died);
		else if(type==SORPRES) reset.setIcon(sorpres);
		
	}
	
	public int getButton() {
		
		return type;
		
	}
	
	public void setFlags(int nFlags) {
		this.nFlags=nFlags;
		flags.setText(formatTo(nFlags));
	}
	
	public int getFlags() {
		return nFlags;
	}
	
	private String formatTo(int n) {
		
		String s=String.valueOf(n);
		
		String s2="";
		
		for(int i=0; i<s.length(); i++) {
			
			s2+=s.charAt(i) + ((i+1)==s.length() ? "" : " ");
			
		}
		
		s=s2;
		
		while(s.length()<5) {
			
			s="0 " + s;
			
		}
		
		return s;
		
	}
	
	public void win() {
		
		JOptionPane.showMessageDialog(null, "VICTORIA", "Busca Minas", JOptionPane.INFORMATION_MESSAGE, win);
		
		threadAnimation=new Thread(new Runnable(){

			public void run() {

				int DELAY=50;
				
				while(nTimer>0) {
					
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					
					nTimer--;
					
					timer.setText(formatTo(nTimer));
					
					updateUI();
					
				}
				
				flags.setText(formatTo(0));
				
				DELAY=400;
				
				char[] c=flags.getText().toCharArray();
				
				char[] c2="W I N".toCharArray();
				
				for(int i=0; i<c2.length; i++) {
					
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					c[i]=c2[i];
					
					flags.setText(String.valueOf(c));
					
				}
				
				c=timer.getText().toCharArray();
				
				for(int i=0; i<c2.length; i++) {
					
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					c[i]=c2[i];
					
					timer.setText(String.valueOf(c));
					
				}
				
			}
		});
		
		threadAnimation.start();
		
	}
	
	private Font digitalFont(int type, float size) {
		
		Font font=null;
		
		 try {
			 
			 InputStream is;
			 
			 switch(type) {
				 case Font.BOLD:
			         is= getClass().getResourceAsStream("Fonts/DS-DIGIB.TTF");
					 break;
					 
				 case Font.ITALIC:
			         is= getClass().getResourceAsStream("Fonts/DS-DIGII.TTF");
					 break;
					 
				 case Font.BOLD | Font.ITALIC:
			         is= getClass().getResourceAsStream("Fonts/DS-DS-DIGIT.TTF");
					 break;
					 
				 case Font.PLAIN:
				 default:
			        is= getClass().getResourceAsStream("Fonts/DS-DIGI.TTF");
					break;
			 }

			 font = Font.createFont(Font.TRUETYPE_FONT, is);
			 
	        } catch (Exception ex) {
	        	
	            font = new Font("Arial", Font.PLAIN, 14);      
	            
	        }
		 
		return font.deriveFont(size);
		
	}
	
	public void startTimer() {
		
		threadTimer=new Thread(this);
		
		threadTimer.start();
				
	}
	
	public void resetTimer() {
		
		nTimer=0;
		timer.setText(formatTo(0));
		
		updateUI();
		
	}
	
	public void stopTimer() {
		
		threadTimer.interrupt();
		
	}
	
	public boolean isCounting() {
		
		return threadTimer==null ? false: !threadTimer.getState().equals(Thread.State.TERMINATED);
		
	}
	
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			
			try {
				Thread.sleep(1000);
				
				nTimer++;
				timer.setText(formatTo(nTimer));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			
		}
				
	}
	
}
