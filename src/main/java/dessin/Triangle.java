package dessin;

public class Triangle extends Polygone{

	private static final long serialVersionUID = 8366252887967813035L;


	Triangle() {
		super(3);
	}
	
	Triangle(Point p1, Point p2, Point p3){
		this();
		
		changePoint(1, p1);
		changePoint(2, p2);
		changePoint(3, p3);
	}

	/**
	 * Interverti les sommets  n et m
	 * @param n numero de sommet
	 * @param m numero de sommet
	 */
	public void changePosSommet(int n, int m) {
		Point tmps = new Point(getPoint(n));


		if( n!= m  &&  (n <3  &&  n>-1)  &&  (m>-1 &&  m<3))
		{
			changePoint(n, getPoint(m));
			changePoint(m, tmps);
		}

	}


	public double surface( ) {
		double p;
		/* *** recuperation de la longueur des cotes *** */
		double a= getPoint(0).distance(getPoint(1));
		double b= getPoint(1).distance(getPoint(2));
		double c= getPoint(2).distance(getPoint(0));

		System.out.println("Calcul de la surface d'un triangle.");
		p = (a + b + c) / 2 ;

		return Math.sqrt(p * (p - a) * (p - b) * (p - c)) ;
	}

}
