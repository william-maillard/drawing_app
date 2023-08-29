package dessin;

import java.io.Serializable;
import java.lang.Math;

public class Point implements ParametresGraphiques, Serializable{

	private static final long serialVersionUID = -9185405761815112678L;
	// coordonnees a 2 dimensions
	private double x;
	private double y;
	// macros pour le signe de l'angle
	public static final int SAM = -1;
	public static final int ALIGNES = 0;
	public static final int SIAM = 1;

		
	// -- CONSTRUCTEURS --
	
	/**
	 * Cree un point avec des coordonnees aleatoires.
	 * <ul>
	 * 		<li>-LIM_X <= x <= LIM_X</li>
	 * 		<li>-LIM_Y <= y <= LIM_Y</li>
	 * </ul>
	 * @see ParametresGraphiques
	 */
	Point() {
		x = Math.random() * (2*LIM_X+1) - LIM_X;
		y = Math.random() * (2*LIM_Y+1) - LIM_Y;
	}
	
	/**
	 * Cree un point dont les coordonnes sont passees en parametre.
	 * <strong>Precondition :</strong> Les coordonnees doivent etre inclus
	 * dans le plan.<br />
	 * Sinon, la valeur limite lui est donnee.
	 * @see ParametresGraphiques
	 * @param x : coordonnee sur l'axe des <strong>abscysses</strong>
	 * @param y : coordonnee sur l'axe des <strong>ordonnees</strong>
	 */
	Point(double x, double y) {
		if(x < -LIM_X)
			this.x = -LIM_X;
		else if(x > LIM_X)
			this.x = LIM_X;
		else
			this.x = x;
		
		if(y < -LIM_Y)
			this.y = -LIM_Y;
		else if(y > LIM_Y)
			this.y = LIM_Y;
		else
			this.y = y;
	}
	
	/**
	 * Cree un point ayant les meme coordonnees que le point p.
	 * @param p un Point
	 */
	Point(Point p){
		this(p.x, p.y);
	}
	
	// -- CONVERSION COORD GRAPHIQUE/PLAN --
	/**
	 * Convertis une coordonne x de la fenetre graphique
	 * en coordonne du plan.
	 * @param x : la coordonne a convertir
	 * @return x dans le plan
	 */
	public static int x2plan(int x) { return x - X0; }
	/**
	 * Convertis une coordonne x du plan 
	 * en coordonne graphique.
	 * @param x : la coordonne a convertir
	 * @return  x de la fenetre graphique
	 */
	public static int x2graphique(double x) {return (int)x + X0;}
	
	/**
	 * Convertis une coordonne y de la fenetre graphique
	 * en coordonne du plan.
	 * @param y : la coordonne a convertir
	 * @return y dans le plan
	 */
	public static int y2plan(int y) { return Y0 - y; }
	/**
	 * Convertis une coordonne x du plan 
	 * en coordonne graphique.
	 * @param y : la coordonne a convertir
	 * @return  y de la fenetre graphique
	 */
	public static int y2graphique(double y) {return Y0 - (int)y;}
	
	
	// -- ACCESSEURS --

	/**
	 * @return l'abscysse du point
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return l'abscysse du point sur la fenetre graphique.
	 */
	public int getXGraphique() {
		return X0 + (int)x;
	}

	/**
	 * @return l'ordonnee du point.
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return l'ordonnee du point sur la fenetre graphique.
	 */
	public int getYGraphique() {
		return Y0 - (int)y;
	}

	// -- MUTATEURS --
	/**
	 * Modifie l'abscysse du point courant.<br />
	 * Si x depasse du plan la valeur limite
	 * lui sera attribue
	 * @param x la nouvelle abscysse
	 */
	public void setX(double x) {
		if(x < -LIM_X)
			this.x = -LIM_X;
		else if(x > LIM_X)
			this.x = LIM_X;
		else
			this.x = x;
	}
	/**
	 * Modifie l'ordonnee du point courant.<br />
	 * Si y depasse du plan la valeur 
	 * limite lui sera attribue
	 * @param y la nouvelle ordonnee
	 */
	public void setY(double y) {
		if(y < -LIM_Y)
			this.y = -LIM_Y;
		else if(y > LIM_Y)
			this.y = LIM_Y;
		else
			this.y = y;
	}
	

	// -- METHODES --

	/**
	 * @param p un point
	 * @return la distance entre le point courant et p
	 */
	public double distance(Point p){
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}

	/**
	 * @return la distance du point courant par rapport à l'origine
	 */
	public double distance(){
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y , 2));
	}

	@Override
	public String toString(){
		return "(" + (int)x + ", " + (int)y + ")" ;
	}
	
	/**
	 * Donne le signe de l'angle des vecteur ab ac, a l'angle courant
	 * @param b un point
	 * @param c un point
	 * @return SAM | SIAM | ALIGNE suivant le signe de l'angle
	 */
	public int signeAngle(Point b, Point c) {
		double angle;
		
		angle = (b.getX() - getX()) * (c.getY() -getY()) - (c.getX() - getX()) * (b.getY() - getY());
		
		if(angle == 0)
			return ALIGNES;
		if(angle > 0)
			return SIAM;
		
		return SAM;
	}
	
	/**
	 * Translate le point courant
	 * @param dx translation sur les x
	 * @param dy transalation sur les y
	 * @return <ul>
	 *            <li>true si la translation a ete effectuee</li>
	 *            <li>false si la translation a ete refusee</li>
	 *         </ul>
	 */
	public boolean translater(double dx, double dy) {
		double x = this.x + dx;
		double y = this.y + dy;
		if(x < -LIM_X  ||  x > LIM_X  ||  y < -LIM_Y  ||  y > LIM_Y)
			return false;
		
		this.x = x;
		this.y = y;
		return true;
	}
	
}
