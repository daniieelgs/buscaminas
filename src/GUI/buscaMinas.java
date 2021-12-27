package GUI;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class buscaMinas {

	public static void main(String[] args) {
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		
		frame f=new frame("Busca Minas v1.0B", width, height);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setVisible(true);
			
	}

}
