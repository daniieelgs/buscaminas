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

public class counter extends JPanel{

	private static final long serialVersionUID = 1L;

	private JLabel nFlags, timer;
	private JButton reset;
	private ImageIcon happy, sad;
	
	public counter(map mapa) {
		
		setLayout(new BorderLayout());
		
		Font style=digitalFont(Font.PLAIN, 25);
		
		nFlags=new JLabel("000");
		nFlags.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		nFlags.setFont(style);
		nFlags.setForeground(Color.RED);
		
		timer=new JLabel("000");
		timer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		timer.setFont(style);
		timer.setForeground(Color.RED);
		
		BufferedImage icon=null;
		
		try {
			
			icon=ImageIO.read(counter.class.getResource("Images/happy.png"));
			happy=new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
			
			icon=ImageIO.read(counter.class.getResource("Images/sad.png"));
			sad=new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_DEFAULT));

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
		
		add(nFlags, BorderLayout.WEST);
		add(resetPanel, BorderLayout.CENTER);
		add(timer, BorderLayout.EAST);
	
		reset.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				
				reset.setIcon(sad);
				
			}
			
			public void mouseExited(MouseEvent e) {
				
				reset.setIcon(happy);
				
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				resetTimer();
				//TODO
				
			}
			
		});
		
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
		
		//TODO
		
	}
	
	public void resetTimer() {
		
		//TODO
		
	}
	
}
