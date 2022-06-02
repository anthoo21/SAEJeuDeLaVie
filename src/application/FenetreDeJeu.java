package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

import application.Main;


public class FenetreDeJeu extends Main {

	private static boolean execution;

	public static Timeline refresh;
	public static int[][] infoJeu;

	static int tailleGrille = Main.getTailleGrille();

	public static void display() {


		Random random = new Random();
		Stage primaryStage = new Stage();
		VBox racine = new VBox();
		int tailleFenetre = 900;
		int nbrCellule;
		String nbrCelluleString;
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
		infoJeu = new int[tailleGrille][tailleGrille];

		for(int i = 0; i<tailleGrille; i++) {
			for(int j = 0; j<tailleGrille; j++) {
				nombreCase[i][j] = new Case(i, j);
				jeu.add(nombreCase[i][j], i, j);
			}
		}

		Algorithme calculs = new Algorithme(infoJeu, nombreCase, Main.tabSurvieTest, Main.tabNaissanceTest);
		calculs.start();

		refresh = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent arg0) {


				for(int i = 0; i < tailleGrille; i++) {
					for(int j = 0; j < tailleGrille; j++) {
						if(infoJeu[i][j] == 1 && !nombreCase[i][j].isOccupee()) {
							nombreCase[i][j].naissance();
						} 
						if(infoJeu[i][j] == 0 && nombreCase[i][j].isOccupee()) {
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



		//			primaryStage.heightProperty().addListener(e -> {
		//				primaryStage.setWidth(primaryStage.getHeight() + 100);
		//			});
		//			
		//			primaryStage.widthProperty().addListener(e -> {
		//				primaryStage.setHeight(primaryStage.getWidth() + 100);
		//			});

		HBox ligneBas = new HBox(35);
		Button accueil = new Button("Acceuil");
		Button effacer = new Button("Effacer");
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		effacer.setOnAction(event ->{
			for(int i = 0; i < tailleGrille; i++) {
				for(int j = 0; j < tailleGrille; j++) {
					infoJeu[i][j] = 0;
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
					infoJeu[i][j] = 0;
					nombreCase[i][j].deces();
				}
			}
			for(int i = 0; i < tailleGrille; i++) {
				for(int j = 0; j < tailleGrille; j++) {
					choix = genererRandom(0,2);
					if (choix == 0) {
						infoJeu[i][j] = 0;
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
				Scene structureScene = new Scene(all, 230, 420);
				structureScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				

				Stage newWindow = new Stage();

				int milieu = tailleGrille/2;
				Label choix = new Label("Choisissez une structure :");

				Button grenouille = new Button("Grenouille");
				grenouille.setOnAction(e -> {
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					galaxie.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							textGalaxie.setText("Min : 11x11");
							textGalaxie.setTextFill(Color.DARKRED);
						}
					});
				} 
				galaxie.setOnAction(e -> {
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					horloge.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							textHorloge.setText("Min : 14x14");
							textHorloge.setTextFill(Color.DARKRED);
						}
					});
				} 
				horloge.setOnAction(e -> {

					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					pentadecathlon.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							textPenta.setText("Min : 17x17");
							textPenta.setTextFill(Color.DARKRED);
						}
					});
				} 
				pentadecathlon.setOnAction(e -> {
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					for(int i = 0; i < tailleGrille; i++) {
						for(int j = 0; j < tailleGrille; j++) {
							if (i == milieu && j == milieu) {
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
					canonplaneur.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							textCanon.setText("Min : 38x38");
							textCanon.setTextFill(Color.DARKRED);
						}
					});
				}
				C.getChildren().addAll(textCanon, canonplaneur);
				Button formestable = new Button("Formes stables");
				
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
				

				all.getChildren().addAll(choix, grenouille, phare, etoile, galaxie, horloge,
						clignotant, pentadecathlon, vaisseau, planeur, canonplaneur,
						formestable, textGalaxie, textHorloge, textPenta, textCanon, C, H , P, G);

				all.setAlignment(Pos.CENTER);
				newWindow.setTitle("Structure");
				newWindow.setScene(structureScene);
				newWindow.show();
			}

		});
		
		Slider vitesse = new Slider();
		vitesse.setMin(1);
		vitesse.setMax(459);
		vitesse.setPrefSize(300, 50);
		vitesse.setValue(400);
		vitesse.valueProperty().addListener(e->{
			refresh.stop();

			KeyFrame key = new KeyFrame(Duration.millis(500-vitesse.getValue()), new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0) {



					//rafraichissement:
					//						System.out.println("refresh");
					for(int i = 0 ; i < tailleGrille ; i++){
						for(int j = 0 ; j < tailleGrille ; j++){
							if(infoJeu[i][j] == 1 && !nombreCase[i][j].isOccupee()){
								nombreCase[i][j].naissance();
							}
							if(infoJeu[i][j] == 0 && nombreCase[i][j].isOccupee()){
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


		ligneBas.setPadding(new Insets(26, 0, 0, 25));

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
		
		/* assigniation des CSS */
		accueil.getStyleClass().add("btn-game");
		effacer.getStyleClass().add("btn-game");
		start.getStyleClass().add("btn-game");
		stop.getStyleClass().add("btn-game");
		aleatoire.getStyleClass().add("btn-game");
		structure.getStyleClass().add("btn-game");
		vitesse.getStyleClass().add("bar-speed");

		ligneBas.getChildren().addAll(accueil, effacer, start, stop, aleatoire, structure, vitesse);

		racine.getChildren().addAll(jeu, bottomBar, ligneBas);

		primaryStage.setTitle("Jeu de la vie");
		primaryStage.setScene(scene2);
		primaryStage.show();






	}

	public static void main(String[] args) {
		launch(args);
	}

	public static boolean getExecution() {
		return execution;
	}

	static int genererRandom(int borneInf, int borneSup){
		Random random = new Random();
		int nb;
		nb = borneInf+random.nextInt(borneSup-borneInf);
		return nb;
	}
}