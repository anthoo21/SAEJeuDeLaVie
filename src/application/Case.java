/*
 * FenetreDeJeu.java
 * BUT INFO1 2021-2022                                               10/06/2022                        
 */
package application;


import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Permet de cr�er les cases du jeu en fonction de la taille de la grille
 * et de colorier les cases
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 */

public class Case extends Parent{

	/**
	 * Cordonn�e sur X d'une case
	 */
	int coordX;

	/**
	 * Cordonn�e sur Y d'une case
	 */
	int coordY;

	/**
	 * R�cup�re la taille de la grille
	 */
	static int tailleGrille = Main.getTailleGrille();

	/**
	 * Permet de savoir si une case est occup�e ou non
	 */
	private boolean occupee;

	/**
	 * cellule de la grille
	 */
	private Rectangle cellule;

	/**
	 * taille d'une cellule
	 */
	static int tailleCell;

	/**
	 * Permet de cr�er la grille contenant des cases en fonction de la taille
	 * de la grille. Permet aussi de placer des cellules et de les supprimer.
	 * @param x cordonn�e sur x d'une case
	 * @param y cordonn�e sur y d'une case
	 */
	public Case(int x, int y) {
		coordX = x;
		coordY = y;

		tailleCell = (900/tailleGrille)-1;


		Rectangle fond = new Rectangle(0,0,tailleCell,tailleCell);
		fond.setFill(Color.WHITE);	
		this.getChildren().add(fond);

		occupee = false;
		cellule = new Rectangle(0,0,tailleCell,tailleCell);
		cellule.setFill(Color.BLACK);
		this.getChildren().add(cellule);
		cellule.setVisible(false);



		this.setOnMouseClicked(e -> {
			if(!FenetreDeJeu.getExecution()) {
				if(occupee) {
					this.deces();
				} else {
					this.naissance();
				}
			}
		});



	}

	/**
	 * Fait na�tre une cellule
	 */
	public void naissance() {
		cellule.setVisible(true);
		occupee = true;
		FenetreDeJeu.grille[getX()][getY()] = 1;
	}

	/**
	 * Fait mourir une cellule
	 */
	public void deces() {
		cellule.setVisible(false);
		occupee = false;
		FenetreDeJeu.grille[getX()][getY()] = 0;
	}

	/**
	 * Permet de savoir si une case est occup�e ou non.
	 * @return true si case occup�e
	 * 	       false sinon
	 */
	public boolean isOccupee() {
		return occupee;
	}
	
	/**
	 * Permet de r�cup�rer la cordonn�e sur x d'une case quelconque
	 * @return cordonn�e sur x d'une case
	 */
	public int getX() {
		return coordX;
	}
	
	/**
	 * Permet de r�cup�rer la cordonn�e sur y d'une case quelconque
	 * @return cordonn�e sur y d'une case
	 */
	public int getY() {
		return coordY;
	}

}