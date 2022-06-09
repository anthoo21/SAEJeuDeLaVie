package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * @author Froment Jean-Francois
 * @author Enjalbert Anthony
 *
 */
public class Main extends Application {

	/**
	 * TODO commenter le role (attribut ou r�le associatif)
	 */
	public static CheckBox[] tabSurvieTest;
	
	/**
	 * TODO commenter le role (attribut ou r�le associatif)
	 */
	public static CheckBox[] tabNaissanceTest;

	/**
	 * TODO commenter le role (attribut ou r�le associatif)
	 */
	public static int tailleGrille;


	@Override
	public void start(Stage primaryStage) {


		/* box */
		VBox root = new VBox(20); 
		VBox right = new VBox(20); 
		VBox left = new VBox(20);
		HBox big = new HBox(20);
		HBox bar = new HBox(20); 
		HBox firstCheck = new HBox(20);
		HBox secondCheck = new HBox(20);




		/* composants autres que box */
		Label title = new Label ("JEU DE LA VIE");
		Label rules = new Label ("R�gles");
		Label size = new Label ("Taille de la grille");
		Label rulesDie = new Label ("Une cellule na�t si elle est   entour�e de : "); // espace pour IHM
		Label rulesLive = new Label ("Une cellule reste en vie si elle est entour�e de : ");
		Separator vertical = new Separator(); // barre vertical
		Button play = new Button("JOUER");
		Button more = new Button("Plus d'informations");
		Label texteNumber = new Label();
		Slider sizeNumber = new Slider();
		sizeNumber.setMin(10);
		sizeNumber.setMax(100);

		sizeNumber.setShowTickLabels(true);
		sizeNumber.setShowTickMarks(true);
		sizeNumber.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tailleGrille = (int) sizeNumber.getValue();
				texteNumber.setText(Integer.toString(tailleGrille) + "x" + Integer.toString(tailleGrille));
				
			}
			
		});
		sizeNumber.setValue(20);

		/* check box */

		CheckBox[] tabSurvie = new CheckBox[9];
		for (int i = 0; i < 9; i++) {
			tabSurvie[i] = new CheckBox(String.valueOf(i));
			firstCheck.getChildren().add(tabSurvie[i]);
		}
		tabSurvie[2].setSelected(true);
		tabSurvie[3].setSelected(true);

		tabSurvieTest = tabSurvie;

		CheckBox[] tabNaissance = new CheckBox[9];
		for (int i = 0; i < 9; i++) {
			tabNaissance[i] = new CheckBox(String.valueOf(i));
			secondCheck.getChildren().add(tabNaissance[i]);
		}
		tabNaissance[3].setSelected(true);
		tabNaissanceTest = tabNaissance;

		/* assigniation des CSS */
		title.getStyleClass().add("title");
		right.getStyleClass().add("right");
		play.getStyleClass().add("play");
		more.getStyleClass().add("more");
		rules.getStyleClass().add("rules");
		size.getStyleClass().add("title-rules");
		rulesDie.getStyleClass().add("title-rules");
		rulesLive.getStyleClass().add("title-rules");
		sizeNumber.getStyleClass().add("bar");
		firstCheck.getStyleClass().add("check-box");
		secondCheck.getStyleClass().add("check-box");
		texteNumber.getStyleClass().add("number");
             
		rulesLive.setWrapText(true); //  d�bloque le retour � la ligne
		rulesDie.setWrapText(true);
		
		/* placement */
		root.setAlignment(Pos.TOP_CENTER); 
		play.setTranslateY(250);
		play.setTranslateX(50);
		more.setTranslateY(260);
		more.setTranslateX(90); 
//		right.setTranslateY(100);
		right.setTranslateX(110);
		right.setSpacing(50);

		bar.getChildren().addAll(sizeNumber,texteNumber);
		right.getChildren().addAll(rules,size,bar,rulesLive,firstCheck,rulesDie,secondCheck);
		left.getChildren().addAll(play,more);
		big.getChildren().addAll(left,right);
		root.getChildren().addAll(title,vertical,big);

		/* boutton plus d'informations */
		more.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getHostServices().showDocument("https://fr.wikipedia.org/wiki/Jeu_de_la_vie");
			}
		});

		tabSurvie.toString();
		Scene scene = new Scene(root,900,900);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show(); 

		play.setOnAction(e -> {
			FenetreDeJeu.display();
			primaryStage.close();
		});
	}



	/**
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * TODO commenter le r�le (SRP) de cette m�thode 
	 * @return taille de la grille
	 */
	public static int getTailleGrille() {
		return tailleGrille;
	}

}