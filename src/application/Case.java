package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Case extends Parent{

	int coordX;
	int coordY;
	
	static int tailleGrille = Main.getTailleGrille();
	
	private boolean occupee;
	private Rectangle cellule;
	
	

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
	
	public void naissance() {
		cellule.setVisible(true);
		occupee = true;
		FenetreDeJeu.infoJeu[getX()][getY()] = 1;
	}
	
	public void deces() {
		cellule.setVisible(false);
		occupee = false;
		FenetreDeJeu.infoJeu[getX()][getY()] = 0;
	}

	public boolean isOccupee() {
		return occupee;
	}
	
	public int getX() {
		return coordX;
	}
	
	public int getY() {
		return coordY;
	}
	
}