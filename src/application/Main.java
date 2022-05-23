package application;
	
import javafx.application.Application;
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
		Label rules = new Label ("Règles");
		Label size = new Label ("Taille de la grille");
		Label leftBar = new Label ("10x10");
		Label rightBar = new Label ("200x200");
		Label rulesDie = new Label ("Une cellule meurt si elle est entourée de : ");
		Label rulesLive = new Label ("Une cellule reste en vie si elle est entourée de : ");
		Separator vertical = new Separator(); // barre vertical
		Button play = new Button("JOUER");
		Button more = new Button("Plus d'informations");
		Slider sizeNumber = new Slider(); // barre de vitesse
		
		/* check box */
		CheckBox oneDie = new CheckBox("1");
		CheckBox twoDie = new CheckBox("2");
		CheckBox threeDie = new CheckBox("3");
		CheckBox fourDie = new CheckBox("4");
		CheckBox fiveDie = new CheckBox("5");
		CheckBox sixDie = new CheckBox("6");
		CheckBox sevenDie = new CheckBox("7");
		CheckBox eightDie = new CheckBox("8");
		CheckBox nineDie = new CheckBox("9");
		CheckBox oneLive = new CheckBox("1"); 
		CheckBox twoLive = new CheckBox("2");
		CheckBox threeLive = new CheckBox("3");
		CheckBox fourLive = new CheckBox("4");
		CheckBox fiveLive = new CheckBox("5");
		CheckBox sixLive = new CheckBox("6");
		CheckBox sevenLive = new CheckBox("7");
		CheckBox eightLive = new CheckBox("8");
		CheckBox nineLive = new CheckBox("9");
		
		/* assigniation des CSS */
		title.getStyleClass().add("title");
		right.getStyleClass().add("right");
		play.getStyleClass().add("play");
		more.getStyleClass().add("more");
		rules.getStyleClass().add("rules");
		size.getStyleClass().add("title-rules");
		rulesDie.getStyleClass().add("title-rules");
		rulesLive.getStyleClass().add("title-rules");
		
		
		root.setAlignment(Pos.TOP_CENTER);
		//big.setSpacing(200);
		//play.setLayoutX(0);
		//play.setLayoutY(0);
		
		firstCheck.getChildren().addAll(oneDie,twoDie,threeDie,fourDie,fiveDie,sixDie,sevenDie,eightDie,nineDie);
		secondCheck.getChildren().addAll(oneLive,twoLive,threeLive,fourLive,fiveLive,sixLive,sevenLive,eightLive,nineLive);
		bar.getChildren().addAll(leftBar,sizeNumber,rightBar);
		right.getChildren().addAll(rules,size,bar,rulesLive,firstCheck,rulesDie,secondCheck);
		left.getChildren().addAll(play,more);
		big.getChildren().addAll(left,right);
		root.getChildren().addAll(title,vertical,big);
		
		// TODO action sur slider, checkbox pour le jeu ??
				
		/* boutton jouer */
		play.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
		    // TODO à completer pour passer à la fenetre de jeu
		}
		});
		
		/* boutton plus d'informations */
		more.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
		    getHostServices().showDocument("https://fr.wikipedia.org/wiki/Jeu_de_la_vie");
		}
		});
		
		Scene scene = new Scene(root,900,900);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}
	
	
	
	/**
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
