package application;

import javafx.scene.control.CheckBox;

/**
 * TODO commenter la classe
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 *
 */
public class Algorithme extends Thread {
	
	private boolean partieExecution;
	private int[][] tabcalc;
	private Case[][] tabCase;
	private CheckBox[] tabSurvie;
	private CheckBox[] tabNaissance;
	
	static int tailleGrille = Main.getTailleGrille();
	
	/**
	 * TODO commenter l'état initial
	 * @param infoJeu
	 * @param plateau
	 * @param tabSurv
	 * @param tabNaiss
	 */
	public Algorithme(int[][] infoJeu, Case[][] plateau, CheckBox[] tabSurv, CheckBox[] tabNaiss) {
		partieExecution = true;
		tabcalc = infoJeu;
		tabCase = plateau;
		tabSurvie = tabSurv;
		tabNaissance = tabNaiss;
	}
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
								tabcalc[i][j] = 0;
							}
						} else {
							if(tabNaissance[voisins].isSelected()) {
								tabcalc[i][j] = 1;
							}
						}
					}
				}
			}
		}
	}

}