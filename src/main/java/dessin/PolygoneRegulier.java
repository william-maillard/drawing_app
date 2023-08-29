package dessin;

public class PolygoneRegulier extends Polygone implements Zoomable {

	private static final long serialVersionUID = -7715201298025051537L;
	private Point centre;

	PolygoneRegulier(int n, Point centre, Point sommet) {
		super(n);
		double dx, dy, r, a;
		int i;

		centre = new Point(centre); // argegation forte
		changePoint(0, sommet);

		// creation des n+1 sommets
		dx = sommet.getX() - centre.getX();
		dy = sommet.getY() - centre.getY();
		r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		a = Math.atan2(dy, dx);
		for (i = 1; i < n; i++) {
			// a chaque nouvel angle on augmente l'angle comme on trace un cercle
			changePoint(i,
					centre.getX() + r * Math.cos(a + i * 2 * Math.PI / n),
					centre.getY() + r * Math.sin(a + i * 2 * Math.PI / n));
		}
	}

	public boolean zoom(int zoom) {
		PolygoneRegulier poly;
		Point p = getPoint(0);
		int i, n = nbrCotes();

		// si a droite du centre par rapport a l'axe des abscysse
		if (p.getX() > centre.getX())
			p.translater(zoom, 0);
		else
			p.translater(-zoom, 0);

		// si en haut du centre par rapport a l'axe des ordonnees
		if (p.getY() > centre.getY())
			p.translater(0, zoom);
		else
			p.translater(0, -zoom);

		// on cree un poly zoome
		poly = new PolygoneRegulier(n, centre, p);

		// on change les points du polygone courant
		for (i = 0; i < n; i++) {
			changePoint(i, poly.getPoint(i));
		}
		return true;
	}

}
