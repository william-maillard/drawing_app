package dessin;

import java.awt.Graphics;
import java.io.Serializable;

import dessin.Forme.PerimNul;

import java.awt.Color;

public class EnsembleForme implements Serializable {

	private static final long serialVersionUID = 1L;
	private Forme tab[];
	private int indiceLibre = 0;
	
	EnsembleForme(int n) {
		tab = new Forme[n];
	}

	/**
	 * Retourne la i<sup>ieme</sup> forme de l'ensemble.
	 * @param i : 0 <= i < nb formes de l'ensemble
	 * @return la forme i si i est un indice correcte, null sinon.
	 */
	Forme getForme(int i) throws IndexOutOfBoundsException {
		if(i <0  ||  i <= tab.length)
			throw new IndexOutOfBoundsException();
		
		return tab[i];
	}
	
	/**
	 * Ajoute forme a l'ensemble courant
	 * Si l'ensemble est plein <strong>>remplace aleatoirement</strong> une forme de l'ensemble.
	 * @param la forme a ajouter
	 */
	public void ajouterForme(Forme forme) {
		int i = indiceLibre;

		// on cherche l'indice ou inserer la forme
		if(i== tab.length)
			i = (int) (Math.random() * tab.length);
		else
			indiceLibre++;	
		
		// on insere la forme
		tab[i]= forme; // agregation faible

			
	}
		

	/**
	 * Calcule la somme des perimetres de toutes les formes de l'ensemble
	 * @return somme des perimetres
	 */
	public double sommePerimetres() throws PerimNul{
		double somme=0;
		int i=0;

		try {
			while(i < indiceLibre) {
				somme += tab[i].perimetre();
				i++;
			}
		}catch(Forme.PointNul e) {
			throw new Polygone.PerimNul(e);
		}
		return somme;
	}

	/**
	 * Affiche toutes les formes de l'ensemble courant.
	 */
	public void affiche() {
		int i=0;

		System.out.println("{");
		while(i < indiceLibre ) {
			System.out.println(tab[i]);
			i++;
		}
		System.out.println("}");

	}
	

	// ex5
	/**
	 * Calcule la somme des surfaces de toutes les formes de l'ensemble
	 * @return la somme des surfaces
	 */
	public double sommeSurface(){
		int i;
		double somme = 0;
		
		for(i=0; i<indiceLibre; i++) {
			somme += tab[i].surface();
		}
		return somme;
	}

	
	/**
	 * Effectue un zoom sur tous les cercles present dans l'ensemble courant
	 * @param zoomX le niveau du zoom
	 */
	public void zoomZoomable(int zoomX){
		int i=0;
		
		while(i < indiceLibre) {
			
			if(tab[i] instanceof Cercle)
				((Cercle)tab[i]).zoom(zoomX);
		}
	}

	
	/**
	 * Dessine toutes les formes de l'ensemble courant
	 * @param g
	 */
	public void toutDessiner(Graphics g) {
		int i;
		for(i=0; i<indiceLibre; i++) {
			tab[i].seDessiner(g);
		}
	}
	
	
	/**
	 * Change l'attribut couleur de toutes les formes
	 * de l'ensemble courant.
	 */
	public void changeCouleurs() {
		int i;
		Color[] couleur =  {Color.BLUE, Color.CYAN, Color.RED, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.YELLOW};
		int j = (int) (Math.random()*couleur.length);//couleur alea de depart
		
		for(i=0; i<indiceLibre; i++)
		{
			tab[i].setCouleur(couleur[j%couleur.length]);
			j++;
		}
	}

	/**
	 * Cherche si le point p se situe dans une forme.
	 * @param p : un point
	 * @return<ul>
	 * 			<li>La premiere forme qui contient p</li>
	 * 			<li>null si aucune</li>
	 * 		</ul>
	 */
	public Forme trouveForme2Filled(Point p) {
		int i=0;
		
		while(i<indiceLibre) {
			if(tab[i].dedans(p)  &&  !tab[i].isFilled())
				return tab[i];
			i++;
		}
		return null;
	}

}


