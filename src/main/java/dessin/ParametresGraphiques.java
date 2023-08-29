package dessin;

import java.awt.Color;

/**
 * Definie toutes les constantes necessaires 
 * a l'interface graphique.
 */
public interface ParametresGraphiques {
	// -- couleurs par defaut --
	Color COULEUR_FORME = Color.GREEN;
	Color COULEUR_TEXTE = Color.WHITE;
	Color COULEUR_BOUTON = Color.DARK_GRAY;
	// -- taille de la fenetre --
	int WIDTH_MAX = 1000, HEIGHT_MAX = 800;
	// -- dimension du plan --
	int X0 =WIDTH_MAX/2, Y0 =HEIGHT_MAX/2;
	int LIM_X = WIDTH_MAX/2, LIM_Y = HEIGHT_MAX/2;
	// -- autres
	int NB_FORMES_MAX = 30;
	Color COULEUR_BOUTON_SELECTIONE = Color.GREEN.darker().darker();
	
}
