/*
 * FenetreDeJeu.java
 * BUT INFO1 2021-2022                                               10/06/2022                        
 */
package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Random;

/**
 * Affiche la fenêtre permettant de jouer
 * Il est possible de modifier la vitesse, et de placer des structures
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 */


public abstract class FenetreDeJeu extends Main {

	/**
	 * Permet de savoir si le jeu est lancé ou non
	 */
	private static boolean execution;

	/**
	 * Permet de pouvoir gérer la vitesse du jeu
	 */
	public static Timeline refresh;

	public static int[][] grille;

	/**
	 * Récupère la taille de la grille
	 */
	static int tailleGrille = Main.getTailleGrille();

	public static void display() {


		Stage primaryStage = new Stage();
		VBox racine = new VBox();
		int tailleFenetre = 900;
		Scene scene2 = new Scene(racine, tailleFenetre, tailleFenetre + 80);
		scene2.getStylesheets().add(FenetreDeJeu.class.getResource("application.css").toExternalForm());
		scene2.setFill(Color.LIGHTGRAY);

		Line bottomBar = new Line(0, 0, 900, 0);

		bottomBar.setStrokeWidth(2);
		bottomBar.setStroke(Color.BLACK);
		bottomBar.setTranslateY(900);

		GridPane jeu = new GridPane();
		jeu.setVgap(1);
		jeu.setHgap(1);
		jeu.setPadding(new Insets(2,0,0,2));

		Case[][] nombreCase = new Case[tailleGrille][tailleGrille];
		grille = new int[tailleGrille][tailleGrille];

		for(int i = 0; i<tailleGrille; i++) {
			for(int j = 0; j<tailleGrille; j++) {
				nombreCase[i][j] = new Case(i, j);
				jeu.add(nombreCase[i][j], i, j);
			}
		}

		Algorithme calculs = new Algorithme(grille, nombreCase, Main.tabSurvieTab, Main.tabNaissanceTab);
		calculs.start();

		refresh = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent arg0) {

				for(int i = 0; i < tailleGrille; i++) {
					for(int j = 0; j < tailleGrille; j++) {
						if(grille[i][j] == 1 && !nombreCase[i][j].isOccupee()) {
							nombreCase[i][j].naissance();
						} 
						if(grille[i][j] == 0 && nombreCase[i][j].isOccupee()) {
							nombreCase[i][j].deces();
						}
					}
				}

				/* Permet de relancer les calculs */
				synchronized(calculs) {
					calculs.notify();
				}

			}
		}));
		refresh.setCycleCount(Timeline.INDEFINITE);
		refresh.play();

		HBox ligneBas = new HBox(35);
		Button accueil = new Button("Acceuil");
		Button effacer = new Button("Effacer");
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		effacer.setOnAction(event ->{
			for(int i = 0; i < tailleGrille; i++) {
				for(int j = 0; j < tailleGrille; j++) {
					grille[i][j] = 0;
					nombreCase[i][j].deces();
				}
			}
			execution = false;
			start.setDisable(false);
			stop.setDisable(true);

		});
		Button aleatoire = new Button("Aléatoire");

		aleatoire.setOnAction(event -> {
			int choix = 0;
			for(int i = 0; i < tailleGrille; i++) {
				for(int j = 0; j < tailleGrille; j++) {
					grille[i][j] = 0;
					nombreCase[i][j].deces();
				}
			}
			for(int i = 0; i < tailleGrille; i++) {
				for(int j = 0; j < tailleGrille; j++) {
					choix = genererRandom(0,2);
					if (choix == 0) {
						grille[i][j] = 0;
						nombreCase[i][j].naissance();
					}
				}
			}
			execution = false;
		});

		Button structure = new Button("Structure");
		structure.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				VBox all = new VBox(10);
				Scene structureScene = new Scene(all, 230, 600);

				Stage newWindow = new Stage();
				structureScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

				int milieu = tailleGrille/2;
				Label choix = new Label("Choisissez une structure :");
				newWindow.setY(50);


				Button grenouille = new Button("Grenouille");
				grenouille.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j].naissance();
								nombreCase[i+1][j].naissance();
								nombreCase[i][j-1].naissance();
								nombreCase[i+1][j-1].naissance();
								nombreCase[i+2][j].naissance();
								nombreCase[i-1][j-1].naissance();
							}
						}
					}
				});
				Button phare = new Button("Phare");
				phare.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j].naissance();
								nombreCase[i+1][j].naissance();
								nombreCase[i][j+1].naissance();
								nombreCase[i+1][j+1].naissance();
								nombreCase[i-1][j-1].naissance();
								nombreCase[i-2][j-2].naissance();
								nombreCase[i-2][j-1].naissance();
								nombreCase[i-1][j-2].naissance();
							}
						}
					}
				});
				Button etoile = new Button("Étoile");
				etoile.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j-2].naissance();
								nombreCase[i][j-3].naissance();	
								nombreCase[i][j-4].naissance();

								nombreCase[i][j+2].naissance();
								nombreCase[i][j+3].naissance();
								nombreCase[i][j+4].naissance();

								nombreCase[i+2][j].naissance();
								nombreCase[i+3][j].naissance();
								nombreCase[i+4][j].naissance();

								nombreCase[i-2][j].naissance();
								nombreCase[i-3][j].naissance();
								nombreCase[i-4][j].naissance();

							}
						}
					}
				});
				HBox G = new HBox(5);
				Label textGalaxie = new Label("");
				Button galaxie = new Button("Galaxie");
				if (tailleGrille < 11) {
					galaxie.setDisable(true);		
				} 
				galaxie.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j-3].naissance();
								nombreCase[i+1][j-3].naissance();
								nombreCase[i-1][j-3].naissance();
								nombreCase[i-2][j-3].naissance();
								nombreCase[i-3][j-3].naissance();
								nombreCase[i-4][j-3].naissance();
								nombreCase[i][j-4].naissance();
								nombreCase[i+1][j-4].naissance();
								nombreCase[i-1][j-4].naissance();
								nombreCase[i-2][j-4].naissance();
								nombreCase[i-3][j-4].naissance();
								nombreCase[i-4][j-4].naissance();

								nombreCase[i][j+3].naissance();
								nombreCase[i-1][j+3].naissance();
								nombreCase[i+1][j+3].naissance();
								nombreCase[i+2][j+3].naissance();
								nombreCase[i+3][j+3].naissance();
								nombreCase[i+4][j+3].naissance();
								nombreCase[i][j+4].naissance();
								nombreCase[i-1][j+4].naissance();
								nombreCase[i+1][j+4].naissance();
								nombreCase[i+2][j+4].naissance();
								nombreCase[i+3][j+4].naissance();
								nombreCase[i+4][j+4].naissance();

								nombreCase[i+3][j].naissance();
								nombreCase[i+3][j+1].naissance();
								nombreCase[i+3][j-1].naissance();
								nombreCase[i+3][j-2].naissance();
								nombreCase[i+3][j-3].naissance();
								nombreCase[i+3][j-4].naissance();
								nombreCase[i+4][j].naissance();
								nombreCase[i+4][j+1].naissance();
								nombreCase[i+4][j-1].naissance();
								nombreCase[i+4][j-2].naissance();
								nombreCase[i+4][j-3].naissance();
								nombreCase[i+4][j-4].naissance();

								nombreCase[i-3][j].naissance();
								nombreCase[i-3][j+1].naissance();
								nombreCase[i-3][j-1].naissance();
								nombreCase[i-3][j+2].naissance();
								nombreCase[i-3][j+3].naissance();
								nombreCase[i-3][j+4].naissance();
								nombreCase[i-4][j].naissance();
								nombreCase[i-4][j+1].naissance();
								nombreCase[i-4][j-1].naissance();
								nombreCase[i-4][j+2].naissance();
								nombreCase[i-4][j+3].naissance();
								nombreCase[i-4][j+4].naissance();

							}
						}
					}
				});
				G.getChildren().addAll(galaxie, textGalaxie);
				HBox H = new HBox(5);
				Label textHorloge = new Label("");
				Button horloge = new Button("Horloge");
				if (tailleGrille < 14) {
					horloge.setDisable(true);
				} 
				horloge.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j].naissance();
								nombreCase[i-1][j].naissance();
								nombreCase[i-2][j-1].naissance();

								nombreCase[i-3][j-1].naissance();
								nombreCase[i-3][j-2].naissance();
								nombreCase[i-3][j].naissance();
								nombreCase[i-3][j+1].naissance();

								nombreCase[i-2][j+2].naissance();
								nombreCase[i-1][j+2].naissance();
								nombreCase[i][j+2].naissance();
								nombreCase[i+1][j+2].naissance();

								nombreCase[i+2][j+1].naissance();
								nombreCase[i+2][j].naissance();
								nombreCase[i+2][j-1].naissance();
								nombreCase[i+2][j-2].naissance();

								nombreCase[i+1][j-3].naissance();
								nombreCase[i][j-3].naissance();
								nombreCase[i-1][j-3].naissance();
								nombreCase[i-2][j-3].naissance();

								nombreCase[i][j-5].naissance();
								nombreCase[i][j-6].naissance();
								nombreCase[i+1][j-5].naissance();
								nombreCase[i+1][j-6].naissance();

								nombreCase[i+5][j].naissance();
								nombreCase[i+4][j].naissance();
								nombreCase[i+5][j+1].naissance();
								nombreCase[i+4][j+1].naissance();

								nombreCase[i-2][j+4].naissance();
								nombreCase[i-2][j+5].naissance();
								nombreCase[i-1][j+4].naissance();
								nombreCase[i-1][j+5].naissance();

								nombreCase[i-5][j-1].naissance();
								nombreCase[i-6][j-1].naissance();
								nombreCase[i-5][j-2].naissance();
								nombreCase[i-6][j-2].naissance();
							}
						}
					}
				});
				H.getChildren().addAll(horloge, textHorloge);
				Button clignotant = new Button("Clignotant");
				clignotant.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j].naissance();
								nombreCase[i+1][j].naissance();
								nombreCase[i-1][j].naissance();

							}
						}
					}
				});
				HBox P = new HBox(5);
				Label textPenta = new Label("");
				Button pentadecathlon = new Button("Pentadecathlon");
				if (tailleGrille < 17) {
					pentadecathlon.setDisable(true);
				} 
				pentadecathlon.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j+4].naissance();
								nombreCase[i][j-4].naissance();
								nombreCase[i+1][j+4].naissance();
								nombreCase[i+1][j-4].naissance();
								nombreCase[i+5][j-1].naissance();
								nombreCase[i+5][j].naissance();
								nombreCase[i+5][j+1].naissance();
								nombreCase[i-4][j-1].naissance();
								nombreCase[i-4][j].naissance();
								nombreCase[i-4][j+1].naissance();
								nombreCase[i-3][j+2].naissance();
								nombreCase[i-2][j+3].naissance();
								nombreCase[i+4][j+2].naissance();
								nombreCase[i+3][j+3].naissance();
								nombreCase[i+4][j-2].naissance();
								nombreCase[i+3][j-3].naissance();
								nombreCase[i-3][j-2].naissance();
								nombreCase[i-2][j-3].naissance();

							}
						}
					}
				});
				P.getChildren().addAll(textPenta, pentadecathlon);
				Button vaisseau = new Button("Vaisseau");
				vaisseau.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i][j+2].naissance();
								nombreCase[i-1][j+2].naissance();
								nombreCase[i+1][j+2].naissance();
								nombreCase[i+2][j+2].naissance();
								nombreCase[i-2][j+1].naissance();
								nombreCase[i-2][j-1].naissance();
								nombreCase[i+2][j+1].naissance();
								nombreCase[i+2][j].naissance();
								nombreCase[i+1][j-1].naissance();
							}
						}
					}
				});
				Button planeur = new Button("Planeur");
				planeur.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i+1][j].naissance();
								nombreCase[i][j+1].naissance();
								nombreCase[i][j-1].naissance();
								nombreCase[i+1][j+1].naissance();
								nombreCase[i-1][j+1].naissance();
							}
						}
					}
				});
				HBox C = new HBox(5);
				Label textCanon = new Label("");
				Button canonplaneur = new Button("Canon à planeurs");
				if (tailleGrille < 38) {
					canonplaneur.setDisable(true);
				}
				canonplaneur.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								nombreCase[i-2][j].naissance();
								nombreCase[i-2][j+1].naissance();
								nombreCase[i-2][j+2].naissance();
								nombreCase[i-1][j+1].naissance();
								nombreCase[i-3][j-1].naissance();
								nombreCase[i-3][j+3].naissance();
								nombreCase[i-3][j+3].naissance();
								nombreCase[i-4][j+1].naissance();

								nombreCase[i-5][j-2].naissance();
								nombreCase[i-6][j-2].naissance();
								nombreCase[i-5][j+4].naissance();
								nombreCase[i-6][j+4].naissance();

								nombreCase[i-7][j-1].naissance();
								nombreCase[i-7][j+3].naissance();
								nombreCase[i-8][j].naissance();
								nombreCase[i-8][j+1].naissance();
								nombreCase[i-8][j+2].naissance();

								nombreCase[i-17][j].naissance();
								nombreCase[i-18][j].naissance();
								nombreCase[i-17][j+1].naissance();
								nombreCase[i-18][j+1].naissance();

								nombreCase[i+2][j].naissance();
								nombreCase[i+2][j-1].naissance();
								nombreCase[i+2][j-2].naissance();
								nombreCase[i+3][j].naissance();
								nombreCase[i+3][j-1].naissance();
								nombreCase[i+3][j-2].naissance();

								nombreCase[i+4][j+1].naissance();
								nombreCase[i+4][j-3].naissance();
								nombreCase[i+6][j-3].naissance();
								nombreCase[i+6][j-4].naissance();
								nombreCase[i+6][j+1].naissance();
								nombreCase[i+6][j+2].naissance();

								nombreCase[i+16][j-1].naissance();
								nombreCase[i+16][j-2].naissance();
								nombreCase[i+17][j-1].naissance();
								nombreCase[i+17][j-2].naissance();

							}
						}
					}
				});
				C.getChildren().addAll(textCanon, canonplaneur);
				Button formestable = new Button("Formes stables");  
				formestable.setOnAction(e -> {
					execution = false;
					start.setDisable(false);
					stop.setDisable(true);
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							grille[i][j] = 0;
							nombreCase[i][j].deces();
						}
					}
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (milieu == i && milieu == j) {
								int choix2;
								choix2 = genererRandom(0,4);
								if (choix2 == 0) {
									nombreCase[i][j].naissance();
									nombreCase[i+1][j].naissance();
									nombreCase[i][j+1].naissance();
									nombreCase[i+1][j+1].naissance();

								} else if (choix2 == 1) {
									nombreCase[i][j-1].naissance();
									nombreCase[i+1][j].naissance();
									nombreCase[i][j+1].naissance();
									nombreCase[i-1][j].naissance();

								} else if (choix2 == 2) {
									nombreCase[i][j-1].naissance();
									nombreCase[i+1][j].naissance();
									nombreCase[i][j+1].naissance();
									nombreCase[i-1][j].naissance();
									nombreCase[i-1][j-1].naissance();

								} else if (choix2 == 3) {
									nombreCase[i][j-1].naissance();
									nombreCase[i+1][j].naissance();
									nombreCase[i][j+1].naissance();
									nombreCase[i-1][j].naissance();
									nombreCase[i-1][j-1].naissance();
									nombreCase[i+1][j+1].naissance();
								}

							}
						}
					}

				});

				/* assigniation des css structures */
				choix.getStyleClass().add("title-str");
				grenouille.getStyleClass().add("btn-game-str");
				phare.getStyleClass().add("btn-game-str");
				etoile.getStyleClass().add("btn-game-str");
				galaxie.getStyleClass().add("btn-game-str");
				horloge.getStyleClass().add("btn-game-str");
				clignotant.getStyleClass().add("btn-game-str");
				pentadecathlon.getStyleClass().add("btn-game-str");
				vaisseau.getStyleClass().add("btn-game-str");
				planeur.getStyleClass().add("btn-game-str");
				canonplaneur.getStyleClass().add("btn-game-str");
				formestable.getStyleClass().add("btn-game-str");
				newWindow.setTitle("Structure");


				all.getChildren().addAll(choix, grenouille, phare, etoile, galaxie, horloge,
						clignotant, pentadecathlon, vaisseau, planeur, canonplaneur,
						formestable);
				all.setAlignment(Pos.CENTER);



				newWindow.setScene(structureScene);
				newWindow.show();
			}

		});
		accueil.setOnAction(event2 -> {
			primaryStage.close();
		});
		VBox ensembleVitesse = new VBox(10);
		Slider vitesse = new Slider();
		vitesse.setMin(1);
		vitesse.setMax(459);
		vitesse.setPrefSize(300, 50);
		vitesse.setValue(400);
		vitesse.valueProperty().addListener(e->{
			refresh.stop();

			KeyFrame key = new KeyFrame(Duration.millis(500-vitesse.getValue()), new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0) {

					for(int i = 0 ; i < tailleGrille ; i++){
						for(int j = 0 ; j < tailleGrille ; j++){
							if(grille[i][j] == 1 && !nombreCase[i][j].isOccupee()){
								nombreCase[i][j].naissance();
							}
							if(grille[i][j] == 0 && nombreCase[i][j].isOccupee()){
								nombreCase[i][j].deces();
							}
						}
					}
					synchronized(calculs){
						calculs.notify();
					}
				}
			});

			refresh.getKeyFrames().setAll(key);
			refresh.play();

		});

		Label vitesse2 = new Label("Vitesse");
		ensembleVitesse.getChildren().addAll(vitesse2, vitesse);
		ensembleVitesse.setAlignment(Pos.CENTER);
		ligneBas.setPadding(new Insets(25, 0, 0, 25));

		stop.setDisable(true);
		start.setOnAction(e -> {
			execution = true;
			start.setDisable(true);
			stop.setDisable(false);
		});

		stop.setOnAction(e -> {
			execution = false;
			start.setDisable(false);
			stop.setDisable(true);
		});

		ligneBas.getChildren().addAll(accueil, effacer, start, stop, aleatoire, structure, ensembleVitesse);

		racine.getChildren().addAll(jeu, bottomBar, ligneBas);
		/* assignation des CSS */
		accueil.getStyleClass().add("btn-game");
		effacer.getStyleClass().add("btn-game");
		start.getStyleClass().add("btn-game");
		stop.getStyleClass().add("btn-game");
		aleatoire.getStyleClass().add("btn-game");
		structure.getStyleClass().add("btn-game");
		vitesse.getStyleClass().add("bar-speed");
		vitesse2.getStyleClass().add("speed");

		primaryStage.setTitle("Jeu de la vie");
		primaryStage.setScene(scene2);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Permet de savoir l'état du jeu
	 * @return execution true si le jeu est lancé
	 *                   false si le jeu n'est pas lancé
	 */
	public static boolean getExecution() {
		return execution;
	}

	/**
	 * Permet de générer un nombre compris entre une borne inférieur
	 * donné et une borne supérieur donné
	 * @param borneInf borne inférieur
	 * @param borneSup borne supérieur
	 * @return nombre aléatoire compris entre borneInf et borneSup
	 */
	static int genererRandom(int borneInf, int borneSup){
		Random random = new Random();
		int nb;
		nb = borneInf+random.nextInt(borneSup-borneInf);
		return nb;
	}
}