package GUI;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class optionBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JRadioButton beginner, medium, expert;
	private ButtonGroup levels;
	private panel content;
	private JFrame frame;
	
	public optionBar(panel content, JFrame frame) {
		
		setLayout(new BorderLayout());
		
		this.content=content;
		
		this.frame=frame;
		
		JMenuBar bar=new JMenuBar();
		
		JMenu game=new JMenu("Juego");
		
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
		
		game.add(beginner);
		game.add(medium);
		game.add(expert);
		
		bar.add(game);
		
		add(bar, BorderLayout.CENTER);
		
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
	
}
