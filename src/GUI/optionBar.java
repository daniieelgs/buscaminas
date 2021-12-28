package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class optionBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JRadioButton beginner, medium, expert;
	private ButtonGroup levels;
	
	public optionBar(panel content) {
		
		setLayout(new BorderLayout());
		
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
			
			if((JRadioButton)e.getSource()==beginner) {
				
			}else if((JRadioButton)e.getSource()==medium) {
				
			}else if((JRadioButton)e.getSource()==expert) {
				
			}
			
		}
		
		
		
	}
	
}
