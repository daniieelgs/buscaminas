package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class optionBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JRadioButton beginner, medium, expert;
	private ButtonGroup levels;
	private panel content;
	private static JFrame frame;
	private static counter count;
	
	public optionBar(panel content, JFrame frame) {
				
		setLayout(new BorderLayout());
		
		this.content=content;
		
		count=content.getCount();
		
		optionBar.frame=frame;
		
		JMenuBar bar=new JMenuBar();
		
		JMenu game=new JMenu("Juego");

		JMenuItem new_game=new JMenuItem("Nuevo Juego");
		JMenuItem stop=new JMenuItem("Pausar");
				
		new_game.setAccelerator(KeyStroke.getKeyStroke("F2"));
		stop.setAccelerator(KeyStroke.getKeyStroke("P"));
		
		levels=new ButtonGroup();
		
		beginner=new JRadioButton("Principiante", true);
		medium=new JRadioButton("Intermedio");
		expert=new JRadioButton("Experto");
		
		levels.add(beginner);
		levels.add(medium);
		levels.add(expert);
		
		levelAction event=new levelAction();
		
		beginner.addActionListener(event);
		medium.addActionListener(event);
		expert.addActionListener(event);
		
		game.add(new_game);
		game.add(stop);
		
		game.add(new JSeparator());
		
		game.add(beginner);
		game.add(medium);
		game.add(expert);
				
		bar.add(game);
		
		add(bar, BorderLayout.CENTER);
				
		stop.addActionListener(new stopAction());
		
		new_game.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				count.reset();
				
			}
		});
		
	}
	
	private class levelAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int width=Toolkit.getDefaultToolkit().getScreenSize().width;
			int height=Toolkit.getDefaultToolkit().getScreenSize().height;
			
			if((JRadioButton)e.getSource()==beginner) {
				content.setLevelMap(map.EASY_MODE);
				frame.setBounds(width*3/8, height/4, width/4, height/2);
			}
			else if((JRadioButton)e.getSource()==medium) {
				content.setLevelMap(map.MEDIUM_MODE);
				frame.setBounds(width*11/32, height*3/16, width*5/16, height*5/8);
			}
			else if((JRadioButton)e.getSource()==expert) {
				content.setLevelMap(map.EXPERT_MODE);
				frame.setBounds(width/2-(width/4*2)/2, 0, width/4*2, (height/2)*2);
			}
			
		}
		
	}
	
	private static class stopAction implements ActionListener{
		
		private static boolean stop;
		private JDialog stopDialog;
		
		public void actionPerformed(ActionEvent e) {
			
			if(count.isCounting() || stop) {
				
				stop=!stop;
				
				if(stop) {
					
					count.stopTimer();
					
					stopFrame();
					
				}else {
					
					stopDialog.dispose();
					count.startTimer();
					
				}
				
			}
			
		}
		
		void stopFrame() {
			
			stopDialog=new JDialog();
			
			ActionListener event=this;
			
			stopDialog.add(new JPanel(new BorderLayout()) {
	
				private static final long serialVersionUID = 1L;

				{
					setBackground(Color.black);
					
					setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
					
					JLabel l=new JLabel("Pausa", JLabel.CENTER);
					l.setForeground(Color.WHITE);
					l.setFont(new Font("Verdana", Font.BOLD, 25));
					l.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
					
					add(l, BorderLayout.NORTH);
					
					JPanel p=new JPanel();
					
					p.setOpaque(false);
					
					JButton play=new JButton("Reanudar");
					
					play.setFocusPainted(false);
					play.setBorderPainted(false); 
					play.setBackground(Color.WHITE);
					play.setCursor(new Cursor(Cursor.HAND_CURSOR));
					play.addActionListener(event);
					
					p.add(play);
					
					add(p, BorderLayout.CENTER);
					
					
				}
				
			});
			
			Point p=frame.getLocation();
			Dimension d=frame.getSize();
			
			stopDialog.setBounds(p.x+40, p.y+40, d.width-80, d.height-80);
			
			stopDialog.setUndecorated(true);
			
			stopDialog.setModal(true);
			stopDialog.setVisible(true);
		}
		
	}
	
}
