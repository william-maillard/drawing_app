package dessin;

import javax.swing.JFrame;
import java.awt.Color;

public class DessinFormes implements ParametresGraphiques{

	public static void main(String[] args) {
		// creation du top-level container
		JFrame frame = new JFrame("Dessin de formes geometriques");
		// creation d'un panneau
		Panneau panneau = new Panneau(frame);
		
		//parametrage de la fenetre
		frame.setSize(WIDTH_MAX, HEIGHT_MAX);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		// parametres du panneau
		panneau.setVisible(true);
	}
	

}
