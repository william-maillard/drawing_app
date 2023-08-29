package dessin;

import java.awt.Graphics;


public class Cercle extends FormeSurface implements Zoomable{

	private static final long serialVersionUID = 2845139289334141400L;
	private Point centre;
	private double rayon;

	public static class CentreNul extends PointNul {
		private static final long serialVersionUID = 1L;
		CentreNul(){ super();}
		CentreNul(Exception e) {super(e); }
	}
	public static class RayonNul extends PointNul {
		private static final long serialVersionUID = 1L;
		RayonNul(){ super(); }
		RayonNul(Exception e) {super(e); }
	}
	
	/**
	 * Creer un cercle dans le plan, de centre et rayon aleatoire.
	 */
	Cercle(){
		centre = new Point();
		rayon = Math.random() * 200 + 10;
		rayonValide();
	}
	
	/**
	 * Cree un cercle contenu dans le plan.<br />
	 * Le rayon est modifie si le cercle deborde.
	 * @param p : le centre
	 * @param rayon : le rayon
	 */
	Cercle(Point p, double rayon){
		centre = new Point(p);
		this.rayon = rayon;
		rayonValide();
	}
	
	Cercle(Cercle c){
		this.centre = new Point(c.centre);
		this.rayon = c.rayon;
	}
	
	/**
	 * Attribut un rayon valide si le rayon courrant ne l'est pas.
	 */
	private void rayonValide() {
		double x = centre.getX();
		double y = centre.getY();
		
		while(x+rayon > LIM_X  ||  x-rayon < -LIM_X  ||  y+rayon > LIM_Y  ||  y-rayon < -LIM_Y) {
			rayon = Math.random() * 200 + 10;
		}
	}
	
	public double getRayon() { return rayon; }
	/**
	 * Modifie le rayon du cercle courant, 
	 * que si la modification ne fais pas deborder le cercle du plan.
	 * @param rayon : nouvelle valeur
	 */
	public void setRayon(double rayon) {
		double x = centre.getX();
		double y = centre.getY();
		// On verifie que le cercle ne va pas depasser du plan
		if(x+rayon > LIM_X  ||  x-rayon < -LIM_X  ||  y+rayon > LIM_Y  ||  y-rayon < -LIM_Y) {}
		else {
			this.rayon = rayon;
		}
	}
	
	@Override
	public double perimetre() throws RayonNul, CentreNul{
		if(rayon == 0)
			throw new RayonNul();
		if(centre.getX() == 0  &&  centre.getY() == 0)
			throw new CentreNul();
		return 2 * Math.PI *rayon;
	}
	
	@Override
	public double surface() {
		return Math.PI * rayon * rayon;
	}
	
	@Override
	public boolean dedans(Point p) {
		if(p.distance(centre)  < rayon)
			return true;
		
		return false;
	
	}
	
	/**
	 * Trouve lequel des 2 cercles est le plus grand
	 * @param c un cercle
	 * @return <ul>
	 * 	        <li>true si le cercle courant est strictement plus grand que c/li>
	 * 			<li>false sinon</li>
	 * 		   </ul>
	 */
	public boolean plusGrand(Cercle c) {
		return rayon > c.rayon;
	}
	
	@Override
	public boolean translater(double dx, double dy) {
		double x = centre.getX() + dx;
		double y = centre.getY() + dy;
		
		// On verifie que le cercle ne va pas depasser du plan
		if(x+rayon > LIM_X  ||  x-rayon < -LIM_X  ||  y+rayon > LIM_Y  ||  y-rayon < -LIM_Y) {
			return false;
		}
		// on translate le centre
		return centre.translater(dx, dy);
	}
	
	
	@Override
	public boolean zoom(int zoomX) {
		double nouveauRayon = rayon;
		
		if(zoomX > 0)
			nouveauRayon *= zoomX;
		else
			nouveauRayon /= -zoomX;
		
		//verifier ici que nouveauRayon ne depace pas de la fenetre
		rayon = nouveauRayon;
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : centre " + centre + ", rayon " + (int)rayon + "\n";
	}
	
	
	@Override
	public void seDessiner(Graphics g) {
		g.setColor(couleur);
		
		if(isFilled()) {
			g.fillOval(
					centre.getXGraphique() - (int)rayon, 
					centre.getYGraphique() - (int)rayon,
					(int)(rayon*2), (int)(rayon*2)
					);
		}
		else {
			g.drawOval(
					centre.getXGraphique() - (int)rayon, 
					centre.getYGraphique() - (int)rayon,
					(int)(rayon*2), (int)(rayon*2)
					);
		}
		g.setColor(Dessinable.COULEUR_TEXTE);
		g.drawString(this.toString(), centre.getXGraphique(), centre.getYGraphique());
	}
}
