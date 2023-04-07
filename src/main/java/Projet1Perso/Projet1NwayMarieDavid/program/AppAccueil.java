package Projet1Perso.Projet1NwayMarieDavid.program;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class AppAccueil extends Application {

	private Stage primaryStage;
	private BorderPane root; // 

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.root = new BorderPane();

		Scene scene = buildScene();
		primaryStage.setTitle("Bienvenue sur notre application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene buildScene() {

		// Création du logo dans une Hbox

		HBox logoBox = new HBox();
		logoBox.setPadding(new Insets(10));
		logoBox.setPadding(new Insets(0, 10, 0, 0));

		Image logo = new Image("Logo-Isika.png");
		ImageView logoView = new ImageView(logo);
		logoView.setFitWidth(800);
		logoView.setFitHeight(400);
		//logoView.setPreserveRatio(true);
		logoView.setSmooth(true);
		logoView.setCache(true);
		logoView.setBlendMode(BlendMode.SCREEN); // blend the color 
		logoBox.getChildren().add(logoView);
		// ajouter BorderPane
		root.setTop(logoBox);
		// Return the scene

		// Création des labels
		Label title = new Label("Bienvenue sur notre application !");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		title.setTextFill(Color.WHITE);

		Label userLabel = new Label("Connexion Utilisateur");
		userLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		userLabel.setTextFill(Color.WHITE);

		Label adminLabel = new Label("Connexion Administrateur");
		adminLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		adminLabel.setTextFill(Color.WHITE);

		Label userErrorLabel = new Label("");
		userErrorLabel.setTextFill(Color.RED);

		Label adminErrorLabel = new Label("");
		adminErrorLabel.setTextFill(Color.RED);

		// Création des champs de saisie

		TextField userLoginField = new TextField();
		userLoginField.setPromptText("Nom d'utilisateur");

		PasswordField userPasswordField = new PasswordField();
		userPasswordField.setPromptText("Mot de passe");

		TextField adminLoginField = new TextField();
		adminLoginField.setPromptText("Nom d'utilisateur");

		PasswordField adminPasswordField = new PasswordField();
		adminPasswordField.setPromptText("Mot de passe");

		// Création des boutons
		Button userLoginButton = new Button("Connexion");
		Button adminLoginButton = new Button("Connexion");

		// Ajout des actions pour les boutons
		userLoginButton.setOnAction(event -> {
			// Vérifier les informations de connexion et afficher un message d'erreur si
			// nécessaire
			userErrorLabel.setText("Informations de connexion invalides.");
		});

		adminLoginButton.setOnAction(event -> {
			// Vérifier les informations de connexion et afficher un message d'erreur si
			// nécessaire
			adminErrorLabel.setText("Informations de connexion invalides.");
		});

		// Création de la partie "Users"

		VBox usersBox = new VBox(10);
		usersBox.setAlignment(Pos.BOTTOM_CENTER);
		usersBox.setPadding(new Insets(50));
		Label usersLabel = new Label("Users");
		usersLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		TextField userField = new TextField();
		userField.setPromptText("Username");
		Button usersButton = new Button("Login");
		usersButton.setOnAction(e -> {

			// Ajouter l'action à réaliser lors de la connexion "Users"
		});
		usersBox.getChildren().addAll(usersLabel, userField, usersButton);

		// Création de la partie "Admin"
		VBox adminBox = new VBox(10);
		adminBox.setAlignment(Pos.BOTTOM_CENTER);
		adminBox.setPadding(new Insets(30));
		Label adminLabel1 = new Label("Admin");
		adminLabel1.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		TextField adminField = new TextField();
		adminField.setPromptText("Username");
		PasswordField adminPassField = new PasswordField();
		adminPassField.setPromptText("Password");
		Button adminButton = new Button("Login");
		adminButton.setOnAction(e -> {

			// Ajouter l'action à réaliser lors de la connexion "Admin"
		});
		adminBox.getChildren().addAll(adminLabel1, adminField, adminPassField, adminButton);
		
		// Ajout des parties "Users" et "Admin" à une HBox
		HBox loginBox = new HBox(100);
		HBox.setHgrow(usersBox, Priority.ALWAYS); 
		
		HBox innerBox = new HBox(100);
		innerBox.getChildren().addAll(usersBox, adminBox);
		innerBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(innerBox, Priority.NEVER);
				
		HBox.setMargin(adminBox, new Insets(0, 0, 0, 120));
		loginBox.getChildren().addAll(innerBox);
		root.setCenter(loginBox);
		// loginBox.setAlignment(Pos.BOTTOM_CENTER);

	
		// Ajout de la HBox à la scène
//	((Group) scene.getRoot()).getChildren().addAll(loginBox);

		// Création de la scène avec un dégradé de couleur bleu canard vers blanc
		
		Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
		//	scene.setFill(lg);
		Scene scene = new Scene(root, 800, 600);
		scene.getRoot().setStyle("-fx-font-family: 'Arial'");

		// Affichage de la scène
		primaryStage.setScene(scene);
		primaryStage.show();
		return scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}