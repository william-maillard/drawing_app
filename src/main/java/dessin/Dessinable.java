package dessin;

import java.awt.Color;
import java.awt.Graphics;

public interface Dessinable extends ParametresGraphiques{
	/**
	 * Dessine la forme courante dans un contexte graphique.
	 * @param g le contexte graphique
	 */
	public void seDessiner(Graphics g);
	public void setCouleur(Color c);
}
