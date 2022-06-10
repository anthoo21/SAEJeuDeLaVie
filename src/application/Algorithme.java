/*
 * Algorithme.java
 * BUT INFO1 2021-2022                                               10/06/2022                        
 */
package application;

import javafx.scene.control.CheckBox;

/**
 * Algorithme de calcul du jeu la vie
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 */

public class Algorithme extends Thread {

	/**
	 * Si oui -> partie en cours
	 */
	private boolean partieExecution;

	/**
	 * Tableau contenant toutes les cases de la grille
	 */
	private int[][] tabCalc;

	/**
	 * Permet de se situer dans la grille de case
	 */
	private Case[][] tabCase;

	/**
	 * CheckBox qui gèrent la survie des cellules
	 */
	private CheckBox[] tabSurvie;

	/**
	 * CheckBox qui gèrent la naissance des cellules
	 */
	private CheckBox[] tabNaissance;

	/**
	 * Récupère la taille de la grille
	 */
	static int tailleGrille = Main.getTailleGrille();

	/**
	 * Permet de lancer l'algorithme dans {@link application.FenetreDeJeu}
	 * @param grille
	 * @param plateau cases de la grille
	 * @param tabSurv survie des cellules
	 * @param tabNaiss naissance des cellules
	 */
	public Algorithme(int[][] grille, Case[][] plateau, CheckBox[] tabSurv, CheckBox[] tabNaiss) {
		partieExecution = true;
		tabCalc = grille;
		tabCase = plateau;
		tabSurvie = tabSurv;
		tabNaissance = tabNaiss;
	}

	/**
	 * Algorithme de calcul du jeu de la vie
	 */
	public synchronized void run() {
		while(partieExecution) {
			try {
				this.wait();
			} catch (InterruptedException erreur) {
				System.out.println("Interruption");
				partieExecution = false;
				return;
			}

			if(FenetreDeJeu.getExecution()) {

				for(int i = 0; i < tailleGrille; i++) {
					for(int j = 0; j < tailleGrille; j++) {
						int voisins = 0;

						/* test des 8 cases autour d'une cellule */

						if(tabCase[(i-1+tailleGrille)%tailleGrille][(j-1+tailleGrille)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[i][(j-1+tailleGrille)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[(i+1)%tailleGrille][(j-1+tailleGrille)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[(i+1)%tailleGrille][j].isOccupee()) {
							voisins++;
						}

						if(tabCase[(i+1)%tailleGrille][(j+1)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[i][(j+1)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[(i-1+tailleGrille)%tailleGrille][(j+1)%tailleGrille].isOccupee()) {
							voisins++;
						}

						if(tabCase[(i-1+tailleGrille)%tailleGrille][j].isOccupee()) {
							voisins++;
						}

						if(tabCase[i][j].isOccupee()) {
							if(!tabSurvie[voisins].isSelected()) {
								tabCalc[i][j] = 0;
							}
						} else {
							if(tabNaissance[voisins].isSelected()) {
								tabCalc[i][j] = 1;
							}
						}
					}
				}
			}
		}
	}

}