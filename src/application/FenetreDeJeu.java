package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

import application.Main;


/**
 * TODO commenter la classe
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 *
 */
public class FenetreDeJeu extends Main {

	private static boolean execution;

	/**
	 * TODO commenter le role (attribut ou rôle associatif)
	 */
	public static Timeline refresh;
	/**
	 * TODO commenter le role (attribut ou rôle associatif)
	 */
	public static int[][] infoJeu;
	
	static int tailleGrille = Main.getTailleGrille();

	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 */
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


		start.setOnAction(e -> {
			execution = true;
		});

		stop.setOnAction(e -> {
			execution = false;
		});

		ligneBas.getChildren().addAll(accueil, effacer, start, stop, aleatoire, structure, vitesse);

		racine.getChildren().addAll(jeu, bottomBar, ligneBas);
		primaryStage.setTitle("Jeu de la vie");
		primaryStage.setScene(scene2);
		primaryStage.show();

		/* assigniation des CSS */
		accueil.getStyleClass().add("btn-game");
		effacer.getStyleClass().add("btn-game");
		start.getStyleClass().add("btn-game");
		stop.getStyleClass().add("btn-game");
		aleatoire.getStyleClass().add("btn-game");
		structure.getStyleClass().add("btn-game");




	}

	public static void main(String[] args) {
		launch(args);
		
	}

	/**
	 * TODO commenter le rôle (SRP) de cette méthode 
	 * @return execution
	 */
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