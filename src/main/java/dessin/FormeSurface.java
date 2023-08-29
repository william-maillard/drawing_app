package dessin;

import java.awt.Color;
import java.io.Serializable;

public abstract class FormeSurface implements Forme, Comparable<Forme>, Serializable{

	private static final long serialVersionUID = 1234749005083047147L;
	private static int nbFormes = 0;
	private static int nbCercles = 0;
	private static int nbPolygones = 0;
	private static int nbPolygonesReguliers = 0;
	private static int nbQuadrilateres = 0;
	private static int nbRectangles = 0;
	private static int nbTriangles = 0;
	
	private final int NUM_FORME;
	private final int NUM_FORME_SPECIFIQUE;
	protected Color couleur = Dessinable.COULEUR_FORME;
	private boolean filled = false;
	
	/**
	 * Associe un numero de forme et un numero specifique a la
	 * forme courante.
	 */
	FormeSurface(){
		nbFormes++;
		NUM_FORME = nbFormes;
		
		if(this instanceof Cercle) {
			NUM_FORME_SPECIFIQUE = ++nbCercles;
		}
		else if(this instanceof PolygoneRegulier) {
			NUM_FORME_SPECIFIQUE = ++nbPolygonesReguliers;
		}
		else if(this instanceof Rectangle) {
			NUM_FORME_SPECIFIQUE = ++nbRectangles;
		}
		else if(this instanceof Quadrilatere) {
			NUM_FORME_SPECIFIQUE = ++nbQuadrilateres;
		}
		else if(this instanceof Triangle) {
			NUM_FORME_SPECIFIQUE = ++nbTriangles;
		}	
		else if(this instanceof Polygone) {
			NUM_FORME_SPECIFIQUE = ++nbPolygones;
		}
		else {
			NUM_FORME_SPECIFIQUE = -1;
		}
	}

	public boolean isFilled() {return filled;}
	public void setFilled(boolean b) { filled = b; }
	
	@Override
	public String toString() {
		return "Forme ("+ NUM_FORME +"), " + getClass().getSimpleName() + "(" + NUM_FORME_SPECIFIQUE + ")";
	}
	
	@Override
	public int compareTo(Forme f) {
		return (int) (surface() - f.surface());

	}
	
	@Override
	public boolean plusGrand(Forme f) {
		return surface() > f.surface();
	}
	
	@Override
	public void setCouleur(Color c) {
		couleur = c;
	}

}
