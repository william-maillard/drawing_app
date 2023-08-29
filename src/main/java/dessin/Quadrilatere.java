package dessin;

public class Quadrilatere extends Polygone{

	private static final long serialVersionUID = 65650279655759228L;

	/**
	 * Cree un quadrilatere avec des sommets aleatoires.
	 */
	Quadrilatere(){
		super(4);
	}
	
	/**
	 * Cree un quadrilatere dont les sommets sont passe en parametre.
	 * @param p1 : 1<sup>ier</sup> sommet
	 * @param p2 : 2<sup>ieme</sup> sommet
	 * @param p3 : 3<sup>ieme</sup> sommet
	 * @param p4 : 4<sup>ieme</sup> sommet
	 */
	Quadrilatere(Point p1, Point p2, Point p3, Point p4){
		this();
		
		changePoint(1, p1);
		changePoint(2, p2);
		changePoint(3, p3);
		changePoint(4, p4);
	}

}