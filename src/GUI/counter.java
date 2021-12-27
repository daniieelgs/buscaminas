package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
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
import javax.swing.JPanel;

public class counter extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;

	private JLabel flags, timer;
	private JButton reset;
	private ImageIcon happy, sad, died;
	private int nFlags, nTimer;
	private Thread threadTimer;
	private boolean die;
	
	public counter(map mapa) {
		
		setLayout(new BorderLayout());
		
		Font style=digitalFont(Font.PLAIN, 25);
		
		flags=new JLabel("000");
		flags.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		flags.setFont(style);
		flags.setForeground(Color.RED);
		
		timer=new JLabel("000");
		timer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		timer.setFont(style);
		timer.setForeground(Color.RED);
		
		nFlags=0;
		nTimer=0;
		
		BufferedImage icon=null;
		
		try {
			
			icon=ImageIO.read(counter.class.getResource("Images/happy.png"));
			happy=new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
			
			icon=ImageIO.read(counter.class.getResource("Images/sad.png"));
			sad=new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_DEFAULT));

			icon=ImageIO.read(counter.class.getResource("Images/died.png"));
			died=new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		reset=new JButton(happy);
		
		reset.setOpaque(false);
		reset.setFocusPainted(false);
		reset.setBorderPainted(false); 
		reset.setContentAreaFilled(false); 
		reset.setIcon(happy);
		
		reset.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		reset.setCursor(new Cursor(Cursor.HAND_CURSOR));		
		
		JPanel resetPanel=new JPanel();
		
		resetPanel.add(reset);
		
		add(flags, BorderLayout.WEST);
		add(resetPanel, BorderLayout.CENTER);
		add(timer, BorderLayout.EAST);
	
		reset.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				
				reset.setIcon(sad);
				
			}
			
			public void mouseExited(MouseEvent e) {
				
				if(die) reset.setIcon(died);
				else reset.setIcon(happy);
				
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				resetTimer();
				die=false;
				
				nFlags=0;
				flags.setText("");
				
				mapa.reset();
				
			}
			
		});
		
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
		
		while(s.length()<3) {
			
			s="0" + s;
			
		}
		
		return s;
		
	}
	
	public void die() {
		
		die=true;
		reset.setIcon(died);
		
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
		timer.setText("000");
		
		updateUI();
		
	}
	
	public void stopTimer() {
		
		threadTimer.interrupt();
		
	}
	
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			
			try {
				Thread.sleep(1000);
				
				nTimer++;
				timer.setText(formatTo(nTimer));
			} catch (InterruptedException e) {
				//e.printStackTrace();
				Thread.currentThread().interrupt();
			}

			
		}
				
	}
	
}
