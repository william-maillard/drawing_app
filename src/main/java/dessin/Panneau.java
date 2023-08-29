package dessin;

import java.awt.FlowLayout;
import java.io.ObjectOutputStream;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JColorChooser;

public class Panneau extends JPanel implements ParametresGraphiques {

	private static final long serialVersionUID = 1L;
	private EnsembleFormeAlea ensembleF;

	/* -- Graphique -- */
	private JToolBar menu = new JToolBar();
	private JLabel etat = new JLabel("etat :");
	private JLabel positionSouris = new JLabel(" ");
	private JButton arcEnCiel, nouvelleForme, boutonCercle, boutonPoly, remplirForme;
	private JButton sauvegarde, chargement, clean;

	/* -- Dessin de forme a la souris -- */
	private Point pointClique = new Point(0, 0);
	private boolean dessineCercle = false, dessinePolygone = false;
	private Cercle dessinCercle = new Cercle(pointClique, 0);
	private Point[] listePoints = new Point[20];
	private int nbPointsClique = 0;

	/* -- remplissage d'une forme -- */
	private boolean coloriage = false;
	private Forme formeAcolorier;

	Panneau(JFrame fenetre) {
		super();
		this.setOpaque(true);
		this.setBackground(Color.BLACK);

		ensembleF = new EnsembleFormeAlea();

		// initialisation du tab contenant les points cliques
		for (int i = 0; i < listePoints.length; i++) {
			listePoints[i] = new Point(0, 0);
		}

		/* ------ Ajout du panneau a la fenetre ----- */
		fenetre.getContentPane().add(this, BorderLayout.CENTER);

		/* ----- Ajout d'une barre de menuint i = 0; dans la fenetre ----- */
		fenetre.getContentPane().add(menu, BorderLayout.NORTH);

		/* ----- Ajout des label a la fenetre ----- */
		JPanel infos = new JPanel();
		fenetre.getContentPane().add(infos, BorderLayout.SOUTH);
		infos.setLayout(new FlowLayout(0, 10, 5));
		infos.add(positionSouris);
		infos.add(etat);

		/* ----- Ajout et parametrage du bouton arcEnCiel ----- */
		// arcEnCiel = new MyJButton(null, new
		// ImageIcon("images/dessin/arc-en-ciel.png"));
		arcEnCiel = new MyJButton("Couleur", null);
		arcEnCiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ensembleF.changeCouleurs();
				repaint();
			}
		});

		/* ----- Ajout et parametrage du bouton nouvelleForme ----- */
		// nouvelleForme = new MyJButton(null, new
		// ImageIcon("images/dessin/ajouter-forme.png"));
		nouvelleForme = new MyJButton("AJouter forme", null);
		nouvelleForme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ensembleF.ajouterFormeAlea();
				repaint();
			}
		});

		/* ----- Ajout d'un bouton pour tracer un cercle ----- */
		boutonCercle = new MyJButton("Dessin Cercle", null);
		boutonCercle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessineCercle = true;
				if (dessinePolygone) {
					dessinePolygone = false;
					boutonPoly.setBackground(COULEUR_BOUTON);
					boutonPoly.repaint(getVisibleRect());
				} else if (coloriage) {
					coloriage = false;
					remplirForme.setBackground(COULEUR_BOUTON);
					remplirForme.repaint();
				}
				boutonCercle.setBackground(COULEUR_BOUTON_SELECTIONE);
				boutonCercle.repaint();
			}
		});
		/* ----- Ajout d'un bouton pour tracer un polygone ----- */
		boutonPoly = new MyJButton("Dessin Polygone", null);
		boutonPoly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessinePolygone = true;
				if (dessineCercle) {
					dessineCercle = false;
					boutonCercle.setBackground(COULEUR_BOUTON);
					boutonCercle.repaint();
				} else if (coloriage) {
					coloriage = false;
					remplirForme.setBackground(COULEUR_BOUTON);
					remplirForme.repaint();
				}
				boutonPoly.setBackground(COULEUR_BOUTON_SELECTIONE);
				boutonPoly.repaint();
			}
		});

		/* ----- Ajout d'un bouton pour remplir une forme ----- */
		remplirForme = new MyJButton("Remplir Forme", null);
		remplirForme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!coloriage) {
					coloriage = true;
					remplirForme.setBackground(COULEUR_BOUTON_SELECTIONE);
					if (dessineCercle) {
						dessineCercle = false;
						boutonCercle.setBackground(COULEUR_BOUTON);
					} else if (dessinePolygone) {
						dessinePolygone = false;
						boutonPoly.setBackground(COULEUR_BOUTON);
					}
				} else {
					coloriage = false;
					remplirForme.setBackground(COULEUR_BOUTON);
				}
				repaint();
			}
		});

		/* ----- Ajout d'un bouton pour sauvegarder les formes dessines ----- */
		sauvegarde = new MyJButton("Sauvegarde", null);
		sauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chemin = JOptionPane.showInputDialog(null, "donnez un chemin de fichier :");
				if (chemin != null) {
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin));
						oos.writeObject(ensembleF);
						oos.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						System.err.println("Erreur sauvegarde : file not found");
					} catch (IOException e1) {
						e1.printStackTrace();
						System.err.println("Erreur sauvegarde : IO exception");
					}
				}
			}
		});
		/* ----- Ajout d'un bouton pour charger un ensemble de formes ----- */
		chargement = new MyJButton("Chargement", null);
		chargement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chemin = JOptionPane.showInputDialog(null, "donnez un chemin de fichier :");
				if (chemin != null) {
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin));
						ensembleF = (EnsembleFormeAlea) ois.readObject();
						ois.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						System.err.println("Erreur sauvegarde : file not found");
					} catch (IOException e1) {
						e1.printStackTrace();
						System.err.println("Erreur sauvegarde : IO exception");
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					repaint();
				}
			}
		});
		/* ----- Ajout d'un bouton pour supprimer toutes les formes ----- */
		clean = new MyJButton("Tout effacer", null);
		clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ensembleF = new EnsembleFormeAlea();
				;
				repaint();
			}
		});

		/* ----- Ecouteur de la souris pour le panneau ----- */
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (dessineCercle) {
					pointClique.setX(Point.x2plan(e.getX()));
					pointClique.setY(Point.y2plan(e.getY()));
					dessinCercle.translater(pointClique.getX(), pointClique.getY());
				}
				;
			}

			public void mouseReleased(MouseEvent e) {
				if (dessineCercle) {
					// ajout du cercle a l'ensemble :
					if (dessinCercle.getRayon() != 0)
						ensembleF.ajouterForme(new Cercle(dessinCercle));
					// reinitialise les variables
					dessinCercle.translater(-pointClique.getX(), -pointClique.getY());
					dessinCercle.setRayon(0);
					repaint();
				}
			}

			public void mouseClicked(MouseEvent e) {
				if (dessinePolygone) {
					if (e.getButton() == MouseEvent.BUTTON1) {

						if (nbPointsClique < listePoints.length) {
							// On stocke le Point clique
							listePoints[nbPointsClique].setX(Point.x2plan(e.getX()));
							listePoints[nbPointsClique].setY(Point.y2plan(e.getY()));
							nbPointsClique++;
							repaint();
						}
					} else if (e.getButton() == MouseEvent.BUTTON3) {

						if (nbPointsClique >= 3) {
							ensembleF.ajouterForme((Forme) (new Polygone(listePoints, nbPointsClique)));
							repaint();
						}
						nbPointsClique = 0;
					}
				} else if (coloriage) {
					pointClique.setX(Point.x2plan(e.getX()));
					pointClique.setY(Point.y2plan(e.getY()));
					formeAcolorier = ensembleF.trouveForme2Filled(pointClique);
					if (formeAcolorier != null) {
						formeAcolorier.setCouleur(JColorChooser.showDialog(
								fenetre,
								"Choisissez votre couleur de remplissage",
								COULEUR_FORME));
						formeAcolorier.setFilled(true);
					}
					repaint();
				}
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (dessineCercle) {
					double rayon = Math.sqrt(
							Math.pow(pointClique.getXGraphique() - e.getX(), 2) +
									Math.pow(pointClique.getYGraphique() - e.getY(), 2));
					dessinCercle.setRayon(rayon);
					repaint();
				}
				positionSouris.setText("coord : ( " + (Point.x2plan(e.getX())) + ", " + (Point.y2plan(e.getY())) + ")");
				positionSouris.repaint();
			}

			public void mouseMoved(MouseEvent e) {
				positionSouris.setText("coord : ( " + (Point.x2plan(e.getX())) + ", " + (Point.y2plan(e.getY())) + ")");
				positionSouris.repaint();
				/* NB : si on appel pas repaint sur le composant, actualise la fenetre */
			}
		});

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		ensembleF.toutDessiner(g);
		if (dessineCercle && dessinCercle.getRayon() > 0)
			dessinCercle.seDessiner(g);
		else if (dessinePolygone && nbPointsClique > 0) {
			g.drawLine(
					(int) listePoints[0].getXGraphique(), (int) listePoints[0].getYGraphique(),
					(int) listePoints[nbPointsClique - 1].getXGraphique(),
					(int) listePoints[nbPointsClique - 1].getYGraphique());

			for (int i = 1; i < nbPointsClique; i++) {
				g.drawLine(
						(int) listePoints[i - 1].getXGraphique(), (int) listePoints[i - 1].getYGraphique(),
						(int) listePoints[i].getXGraphique(), (int) listePoints[i].getYGraphique());
			}
		} else if (coloriage && formeAcolorier != null) {
			// on actualise la forme venant d'etre colorie
			formeAcolorier.seDessiner(g);
			formeAcolorier = null;
		}
	}

	/**
	 * Pour les boutons de la JToolBar
	 */
	private class MyJButton extends JButton {
		private static final long serialVersionUID = 1L;

		MyJButton(String nom, ImageIcon img) {
			super(nom, img);
			if (img == null) {
				setBackground(COULEUR_BOUTON);
				setForeground(COULEUR_TEXTE);
			}
			setPreferredSize(new Dimension(60, 30));
			setFocusPainted(false);
			setBorderPainted(false);
			menu.add(this);
		}
	}
}