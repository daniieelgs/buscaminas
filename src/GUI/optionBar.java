package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

public class optionBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JRadioButton beginner, medium, expert, personalized;
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
		JMenuItem close=new JMenuItem("Cerrar");
				
		new_game.setAccelerator(KeyStroke.getKeyStroke("F2"));
		stop.setAccelerator(KeyStroke.getKeyStroke("P"));
		
		levels=new ButtonGroup();
		
		beginner=new JRadioButton("Principiante", true);
		medium=new JRadioButton("Intermedio");
		expert=new JRadioButton("Experto");
		personalized=new JRadioButton("Personalizado");
		
		levels.add(beginner);
		levels.add(medium);
		levels.add(expert);
		levels.add(personalized);
		
		levelAction event=new levelAction();
		
		beginner.addActionListener(event);
		medium.addActionListener(event);
		expert.addActionListener(event);
		personalized.addActionListener(event);
		
		game.add(new_game);
		game.add(stop);
		
		game.add(new JSeparator());
		
		game.add(beginner);
		game.add(medium);
		game.add(expert);
		game.add(personalized);
				
		game.add(new JSeparator());
		game.add(close);
		
		bar.add(game);
		
		add(bar, BorderLayout.CENTER);
				
		stop.addActionListener(new stopAction());
		
		new_game.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				count.reset();
								
			}
		});
		
		
		close.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
			
		});
		
	}
	
	private class levelAction implements ActionListener{

		
		private int numXY=8, numMines=10;
		
		public void actionPerformed(ActionEvent e) {
			
			int width=Toolkit.getDefaultToolkit().getScreenSize().width;
			int height=Toolkit.getDefaultToolkit().getScreenSize().height;
			
			//System.out.println(width + " - " + height);
			
			int ratioX=(((int)Math.round(( (double) width/4*2)/map.EXPERT_MODE[0])) + ((int)Math.round(( (double) width/4)/map.EASY_MODE[0])))/2;
			int ratioY=(((int)Math.round(( (double) (height/2)*2)/map.EXPERT_MODE[0])) + ((int)Math.round(( (double) height/2)/map.EASY_MODE[0])))/2;
									
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
			else if((JRadioButton)e.getSource()==personalized) {
				
				int[] level=personalLevel();
				
				content.setLevelMap(level);
				
				int xLevel=(width/2 - (level[0]*ratioX/2)) < width && (width/2 - (level[0]*ratioX/2)) > 0 ? width/2 - (level[0]*ratioX/2) : 0;
				int yLevel=(height/2 - (level[0]*ratioY/2)) < (height - 50) && (height/2 - (level[0]*ratioY/2)) > 0 ? height/2 - (level[0]*ratioY/2) : 0;
				int widthLevel=level[0]*ratioX < width ? level[0]*ratioX : width;
				int heightLevel=level[0]*ratioY < height-25 ? level[0]*ratioY : height-25;
				
				frame.setBounds(xLevel, yLevel, widthLevel, heightLevel);
								
			}
			
			count=content.getCount();
			
		}
		
		private int[] personalLevel() {
			
			JDialog levelDialog=new JDialog();

			Point p=frame.getLocation();
			Dimension d=frame.getSize();
			
			
			levelDialog.add(new JPanel(new BorderLayout()) {
				
				private static final long serialVersionUID = 1L;

				{
					
					JLabel title=new JLabel("Cualquier configuración");
					
					title.setForeground(Color.BLUE.brighter());
					
					title.setFont(new Font("Verdana", Font.BOLD, 20));
					
					add(title, BorderLayout.NORTH);
					
					JPanel config=new JPanel(new GridLayout(3, 2));
					
					Font f=new Font("Verana", Font.PLAIN, 15);
					SpinnerNumberModel modelXY=new SpinnerNumberModel(numXY, 5, 500, 1);
					
					
					JLabel label=new JLabel("La altura de la junta:", JLabel.RIGHT);
					label.setFont(f);
					label.setForeground(Color.BLUE.brighter());
					config.add(label);
										
					JSpinner x=new JSpinner(modelXY);
					config.add(x);
					
					label=new JLabel("El ancho de la junta:", JLabel.RIGHT);
					label.setFont(f);
					label.setForeground(Color.BLUE.brighter());
					config.add(label);
					
					JSpinner y=new JSpinner(modelXY);
					config.add(y);
					
					label=new JLabel("Número de bombas:", JLabel.RIGHT);
					label.setFont(f);
					label.setForeground(Color.BLUE.brighter());
					config.add(label);
					
					JSpinner bombs=new JSpinner(new SpinnerNumberModel(numMines, 5, 800, 1));
					config.add(bombs);
					
					add(config, BorderLayout.CENTER);
					
					JPanel button=new JPanel();
					
					JButton ok=new JButton("Aceptar");
					
					button.add(ok);
					
					add(button, BorderLayout.SOUTH);
					
					ok.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							
							numXY=(int)x.getValue();
							numMines=(int) bombs.getValue();
							
							levelDialog.dispose();
							
						}
					});
					
				}
				
			});
			
			levelDialog.setBounds(p.x+20, p.y+20, d.width-40, d.height-40);
			
			levelDialog.pack();
			
			levelDialog.setModal(true);
			levelDialog.setVisible(true);
			
			int[] values= {numXY, numMines};
			
			return values;
			
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
