package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 *
 */
public class Case extends Parent{

	int coordX;
	int coordY;
	
	static int tailleGrille = Main.getTailleGrille();
	
	private boolean occupee;
	private Rectangle cellule;
	
	

	/**
	 * TODO commenter l'état initial
	 * @param x
	 * @param y
	 */
	public Case(int x, int y) {
		coordX = x;
		coordY = y;

		boolean celluleVivante;
		boolean jeu = true;
		
		int calcul;
		
		calcul = (900/tailleGrille)-1;
	

		Rectangle fond = new Rectangle(0,0,calcul,calcul);
		fond.setFill(Color.WHITE);	
		this.getChildren().add(fond);

		occupee = false;
		cellule = new Rectangle(0,0,calcul,calcul);
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
	 * TODO commenter le rôle (SRP) de cette méthode 
	 */
	public void naissance() {
		cellule.setVisible(true);
		occupee = true;
		FenetreDeJeu.infoJeu[getX()][getY()] = 1;
	}
	
	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 */
	public void deces() {
		cellule.setVisible(false);
		occupee = false;
		FenetreDeJeu.infoJeu[getX()][getY()] = 0;
	}

	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 * @return occupee
	 */
	public boolean isOccupee() {
		return occupee;
	}
	
	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 * @return x
	 */
	public int getX() {
		return coordX;
	}
	
	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 * @return y
	 */
	public int getY() {
		return coordY;
	}
	
}
