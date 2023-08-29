package dessin;

public interface Forme extends Dessinable{
	
	public static class PointNul extends Exception{
		private static final long serialVersionUID = -1518196354480848417L;
		PointNul(){ super(); }
		PointNul(String message){ super(message); }
		PointNul(Exception cause){ super(cause); }
	};
	public static class PerimNul extends Exception{
		private static final long serialVersionUID = 8780785965430911691L;
		PerimNul(Exception e){
			super(e);
		}
		
	};

	/**
	 * Calcule le perimetre du polygone courant
	 * @return le perimetre
	 */
	public double perimetre() throws PointNul;
	
	/**
	 * Calcule la surface de la forme courante.
	 * @return <ul>
	 * 				<li>la surface si la forme est convexe,</li>
	 * 				<li>-1 sinon.</li>
	 * 		   </ul>
	 */
	public double surface();
	
	/**
	 * Calcule si le point p appartient strictement a la forme courante
	 * @param p le point a tester
	 * @return <ul>
	 * 	        	<li>true si p est dans la forme</li>
	 * 				<li>false sinon</li>
	 * 		   </ul>
	 */
	public boolean dedans(Point p);
	
	/**
	 * Calcule si la forme courante est plus grande que f.
	 * @param f : une forme
	 * @return<ul>
	 * 	        	<li>true si la forme courante est plus grande que f</li>
	 * 				<li>false sinon</li>
	 * 		   </ul>
	 */
	public boolean plusGrand(Forme f);
	
	/**
	 * Translate la forme courante dans un plan a <strong>2 dimensions</strong>
	 * @param dx translation sur l'axe des <strong>abscysses</strong>.
	 * @param dy translation sur l'axe des y <strong>ordonnees</strong>.
	 * @return <ul>
	 *           <li>true si la forme est translate</li>
	 *           <li>false sinon</li>
	 *         </ul>
	 */
	public boolean translater(double dx, double dy);
	
	public boolean isFilled();
	public void setFilled(boolean b);
}
