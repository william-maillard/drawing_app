package dessin;

import java.awt.Graphics;

public class  Polygone extends FormeSurface{

	private static final long serialVersionUID = -4117690597567245579L;
	private Point cotes[];
	
	public static class SommetNul extends PointNul{
		private static final long serialVersionUID = 1L;
		SommetNul(int i){
			super("Le point numero "+i+" est nul.");
		}
	};	
	
	/**
	 * Cree un polygone a n sommets aleatoires.
	 * @param n : nombre de sommets
	 */
	Polygone(int n) {
		int i;
		
		cotes = new Point[n];
		for(i=0; i<n; i++)
		{
			cotes[i] = new Point();
		}
	}
	
	/**
	 * Cree une copie du polygone p.
	 * @param p : un polygone
	 */
	Polygone(Polygone p) {
		int i, n= p.nbrCotes() ;
		cotes = new Point[n];

		// on copie tous les points de p avec une agregation forte
		for(i=0; i<n; i++) {
			cotes[i] = new Point(p.getPoint(i));
		}

	}
	
	/**
	 * Cree un polygone dont les sommets sont des copies des n 
	 * premiers points de la
	 * liste de points fournis.
	 * @param lp : la liste des Point des sommets
	 * @param n : le nombre de points a prendre dans la liste
	 */
	Polygone(Point[] lp, int n){
		this(n);
		
		for(int i=0; i<n; i++) {
			changePoint(i, lp[i]);
		}
	}

	/**
	 * @return le nombre de cote d'un polygone
	 */
	public int nbrCotes() {
		return cotes.length;
	}

	/**			System.out.println("dessin NON remplis");
	 * Permet d'obtenir une copie du n<sup>ieme</sup> sommet.
	 * @param n : le numero du sommet
	 * @return le Point du n<sup>ieme</sup> sommet
	 */
	public Point getPoint(int n) {

		if(n >= cotes.length)
			return null;
		
		// agregation forte = on renvoie une copie et pas le pointeur !!
		return new Point(cotes[n]); 
	}

	/**
	 * Change le point n du polygone courant
	 * @param n numero du point a changer
	 * @param p le nouveau point
	 * @return true si succes, false sinon
	 */
	public boolean changePoint(int n, Point p) {
		
		if(n < cotes.length) {
			cotes[n].setX(p.getX());
			cotes[n].setY(p.getY());
			return true;
		}
		return false;
	}

	/**
	 * Change le point n du polygone courant
	 * @param n numero du point a changer
	 * @param x absysse du point a changer
	 * @param y ordonee du point a changer
	 * @return vrai si les modifications ont ete apportees, faux sinon.
	 */
	public boolean changePoint(int n, double x, double y) {
		
		if(n < cotes.length)
		{
			cotes[n].setX(x);
			cotes[n].setY(y);
			return true;
		}
		return false;
	}

	@Override
	public double perimetre() throws SommetNul{
		int i, perimetre= 0, n = cotes.length - 1;

		for(i=0; i<n; i++) {
			if(cotes[i].getX() == 0  &&  cotes[i].getY() == 0)
				throw new SommetNul(i);
			perimetre += cotes[i].distance(cotes[i+1]);
		}
		if(cotes[i].getX() == 0  &&  cotes[i].getY() == 0)
			throw new SommetNul(i);
		perimetre += cotes[i].distance(cotes[0]);

		return perimetre;
	}

	@Override
	public double surface( ) {
		int n= cotes.length - 2;
		int i;
		double a, b, c, p; // pour stocker la longueur des cotes et du demi-perimetre
		double surface = 0;
		Point p0, p1, p2; // on aurrais pu s'en passer mais rend le code plus comprehensible
		
		System.out.println("Calcul de la surface d'un polygone.");
		
		if( !estConvexe() )
			return -1;
		
		/* On va decouper le polygone en rectangles successif avec comme sommet de reference p0
		 * (sommet de tous les triangles) et deux sommets successifs
		 * la somme des surfaces de ces triangles est la surface du polygone 
		 */
		p0= cotes[0];
		
		for(i= 1; i< n; i++) {
			// calcul de la surface du rectangle p0 pi p(i+1)
			p1 = cotes[i];
			p2 = cotes[i+1];
			
			a= p0.distance(p1);
			b= p1.distance(p2);			System.out.println("dessin NON remplis");
			c= p2.distance(p0);
	
			p = (a + b + c) / 2. ;
	
			surface += Math.sqrt(p * (p - a) * (p - b) * (p - c)) ;
		}
		
		return surface;
	}


	@Override
	public String toString() {
		int i= 0, n= cotes.length;
		String s = super.toString() + " : ";

		while(i < n)
		{
			s += getPoint(i) + " ";
			i++;
		}
		return s;
	}
	
	
	/**
	 * Test si un polygone est convexe
	 * @return <ul>
	 * 				<li>true s'il est convexe,</li>
	 * 				<li>false sinon.</li>
	 * 			</ul>
	 */
	boolean estConvexe() {
		int n = cotes.length -1;
		int signe=0;
		int i;
		
		if(n < 3)
		
		/* On verifie que les angles pris par odre croissant 
		 * (p0 p1 p2), (p1 p2 p3), ..., (pn-1 pn p0)
		 * sont tous de meme signe
		 */
		
		// on traite le cas (pn-1 pn p0) a part
		signe = cotes[n].signeAngle(cotes[0], cotes[1]);
		if(signe == Point.ALIGNES)
			return false;
		
		n--; 
		for(i=1; i< n; i++)
		{
			if(cotes[i].signeAngle(cotes[i+1], cotes[i+2]) != signe)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	@Override
	public boolean dedans(Point p) {
		int i, signe;
		int n = cotes.length - 1;
		/*
		 * idee : si p est dans le polygone alors les angles 
		 * (p p0 p1), (p p1 p2), ... (p pn-1 pn)
		 * sont de meme signe
		 */
		
		signe = p.signeAngle(cotes[0], cotes[1]);
		
		n--;
		for(i=1; i<n; i++)
		{
			if(p.signeAngle(cotes[i], cotes[i+1]) != signe)
				return false;
		}
		n++;
		
		if(p.signeAngle(cotes[n], cotes[0]) != signe)
			return false;

		return true;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Polygone) )
			return false;
		// si pas de la meme class
		if(o.getClass().getSimpleName() !=  this.getClass().getSimpleName())
			return false;
					
		if(!estConvexe()  ||  !((Polygone)o).estConvexe())
			return false;
		
		return surface() == ((Polygone)o).surface();
	}
	
	
	@Override
	public boolean translater(double dx, double dy) {
		int i=0, n=cotes.length - 1, continuer = 1;
		// inserer conditions sur les limites du plan ici
		
		while(i<n && continuer==1)
		{
			if( !( cotes[i].translater(dx, dy) ) )
				continuer = 0;
			i++;
		}
		
		//si un point n'a pu etre translater, on annule la translation
		if(continuer == 0)
		{
			while(i >= 0) {
				cotes[i].translater(-dx, -dy);
			}
			return false;
		}		
		return true;
	}

	@Override
	public void seDessiner(Graphics g) {
		int i, n = cotes.length -1;
		
		g.setColor(couleur);
		if(isFilled()) {
			int[] x = new int[cotes.length];
			int[] y = new int[cotes.length];

			for(i=0; i<=n; i++) {
				x[i] = (int)cotes[i].getXGraphique();
				y[i] = (int)cotes[i].getYGraphique();
			}
			g.fillPolygon(x, y, n+1);
		}
		else {			
			for(i=0; i< n; i++) {
				g.drawLine(
						cotes[i].getXGraphique(), cotes[i].getYGraphique(), 
						cotes[i+1].getXGraphique(), cotes[i+1].getYGraphique()
						);
			}
			g.drawLine(
					cotes[i].getXGraphique(), cotes[i].getYGraphique(), 
				    cotes[0].getXGraphique(), cotes[0].getYGraphique()
				    );
		}
		
		g.setColor(Dessinable.COULEUR_TEXTE);
		g.drawString(this.toString(), cotes[0].getXGraphique(), cotes[0].getYGraphique());
	}
	
}