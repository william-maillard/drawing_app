package dessin;

public class EnsembleFormeAlea extends EnsembleForme implements ParametresGraphiques {

	private static final long serialVersionUID = 4855402829972075061L;

	/**
	 * Initialise un ensemble vide de 'NB_FORMES_MAX'.
	 * 
	 * @see PaerametresGraphiques
	 */
	EnsembleFormeAlea() {
		super(NB_FORMES_MAX);
	}

	/**
	 * Initialise un ensemble de forme contenant nb_formes aleatoires
	 * 
	 * @param nb_forme le nombre de formes de l'ensemble
	 */
	EnsembleFormeAlea(int nb_forme) {
		super(nb_forme); // init le tableau
		int i;

		for (i = 0; i < nb_forme; i++) {
			ajouterFormeAlea();
		}

	}

	/**
	 * Ajoute une forme choisie aleatoirement parmis
	 * <ul>
	 * <li>Polygone</li>
	 * <li>Quadrilatere</li>
	 * <li>Rectangle</li>
	 * <li>Triangle</li>
	 * <li>Cercle</li>
	 * <li>Polygone regulier</li>
	 * </ul>
	 */
	public void ajouterFormeAlea() {
		int num_forme = (int) (Math.random() * 6);

		switch (num_forme) {
			case 0:
				ajouterForme(new Polygone((int) (Math.random() * 10 + 5)));
				break;
			case 1:
				ajouterForme(new Quadrilatere());
				break;
			case 2:
				ajouterForme(new Rectangle(new Point(), new Point()));
				break;
			case 3:
				ajouterForme(new Triangle());
				break;
			case 4:
				ajouterForme(new Cercle());
				break;
			default:
				Point centre = new Point(Math.random() * LIM_X / 4 - LIM_X / 4, Math.random() * LIM_Y / 4 - LIM_Y / 4);
				Point sommet = new Point(centre.getX() + Math.random() * LIM_X / 4,
						centre.getY() + Math.random() * LIM_Y / 4);
				sommet.translater((int) (Math.random() * 20 + 10), (int) (Math.random() * 20 + 10));
				ajouterForme(new PolygoneRegulier((int) (Math.random() * 10 + 5), new Point(), new Point()));
		}
	}

}
