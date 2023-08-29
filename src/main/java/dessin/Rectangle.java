package dessin;

public class Rectangle extends Quadrilatere implements Zoomable{

	private static final long serialVersionUID = 496627842636364385L;

	/**
	 * Construit un rectangle a partir de deux points.
	 * @param p1 : point haut gauche
	 * @param p2 : point bas droit
	 */
	Rectangle(Point p1, Point p2) {
		super(); // initialisation du tableau

		 /*On place les points dans la tab : NO-NE-SE-SO*/
		 changePoint(0, p1);
		 changePoint(1, p2.getX(), p1.getY());
		 changePoint(2, p2);
		 changePoint(3, p1.getX(), p2.getY());
	}

	/**
	 * Calcule la hauteur du rectangle courant
	 * @return hauteur
	 */
	public double hauteur() {
		return getPoint(0).distance(getPoint(3));
	}

	/**
	 * Calcule la largeur du rectangle courant
	 * @return largeur
	 */
	public double largeur() {
		return getPoint(0).distance(getPoint(1));
	}

	@Override
	public double surface() {
		System.out.println("Calcul de la surface d'un rectangle.");
		return hauteur() * largeur();
	}

	/**
	 * Effectue un zoom sur un rectangle dont les cotes sont
	 * dans l'odre suivant : NO-NE-SE-SO
	 * @param zoom le niveau du zoom
	 */
	public boolean zoom(int zoom) {
		Point p0 = getPoint(0), p1 = getPoint(1), p2 = getPoint(2), p3 = getPoint(3);
		double dist = largeur() * (double)zoom / 2.; // la distance a ajouter/retirer aux coord des sommets
		
		if(p0.translater(-dist, dist))
			if(p1.translater(dist, dist))
				if(p2.translater(dist, -dist))
					if(p3.translater(-dist, -dist)) {

					    changePoint(0, p0);
					    changePoint(1, p1);
					    changePoint(2, p2);
					    changePoint(3, p3);
							
					    return true;
					}
		return false;
	}
	
}