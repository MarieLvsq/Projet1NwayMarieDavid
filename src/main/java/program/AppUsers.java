package program;

import binaire.StagiaireBinaire;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppUsers extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille principale
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-font-family: 'serif'");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));
        
     // Ajout d'un dégradé en arrière-plan
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(27, 92, 102)), new Stop(1, Color.WHITE) };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        gridPane.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
        
//        // Ajout d'une image en fondu avec l'arrière-plan
//        Image logo = new Image(getClass().getResourceAsStream("/LogoProjet.png"));
//        ImageView imageView = new ImageView(logo);
//        imageView.setOpacity(0.5);
//        imageView.setFitWidth(200);
//        imageView.setPreserveRatio(true);
//        StackPane stackPane = new StackPane(imageView);
//        stackPane.setAlignment(Pos.TOP_RIGHT);
//        stackPane.setPadding(new Insets(10));
//        gridPane.add(stackPane, 1, 0);

        // Création du formulaire pour ajouter un stagiaire
        VBox formBox = new VBox();
        formBox.setSpacing(10);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(10));
        Label titleLabel = new Label("Ajouter un stagiaire");
        titleLabel.setFont(new Font("Arial", 16));
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        TextField departementField = new TextField();
        departementField.setPromptText("Département");
        TextField anneeField = new TextField();
        anneeField.setPromptText("Année d'entrée en formation");
        TextField promotionField = new TextField();
        promotionField.setPromptText("Promotion");
        formBox.getChildren().addAll(titleLabel, nomField, prenomField, departementField, anneeField, promotionField);

        // Ajout du formulaire à la grille
        gridPane.add(formBox, 0, 0);

        // Création du bottomPane avec les 3 boutons
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setSpacing(20);
        buttonBox.setPadding(new Insets(10));
        Button addButton = new Button("Ajouter");
        Button refreshButton = new Button("Rafraîchir");
        Button returnButton = new Button("Retour");
        buttonBox.getChildren().addAll(addButton, refreshButton, returnButton);
        
 // Ajouter le formulaire pour ajouter un stagiaire dans la GridPane à gauche
        
        // Créer une VBox pour le tableau de stagiaires
        VBox centerLeftBox = new VBox();
        centerLeftBox.setAlignment(Pos.CENTER);
        centerLeftBox.setSpacing(10);
        
        // Créer un bouton de recherche
        Button searchButton = new Button("Rechercher");
        searchButton.setOnAction(e -> {
            // Action de recherche à implémenter
        });
        
        // Ajouter le bouton de recherche en haut de la VBox
        centerLeftBox.getChildren().add(searchButton);
        
        // Créer le TableView pour afficher les stagiaires
        TableView<StagiaireBinaire> stagiaireTable = new TableView<>();
        stagiaireTable.setEditable(false);
        
        // Créer les colonnes du tableau
        TableColumn<StagiaireBinaire, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        
        TableColumn<StagiaireBinaire, String> prenomCol = new TableColumn<>("Prenom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        
        TableColumn<StagiaireBinaire, String> departementCol = new TableColumn<>("Département");
        departementCol.setCellValueFactory(new PropertyValueFactory<>("departement"));
        
        TableColumn<StagiaireBinaire, Integer> anneeEntreeCol = new TableColumn<>("Année");
        anneeEntreeCol.setCellValueFactory(new PropertyValueFactory<>("anneeEntree"));
        
        TableColumn<StagiaireBinaire, Integer> promotionCol = new TableColumn<>("Promotion");
        promotionCol.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        
        // Ajouter les colonnes au TableView
        stagiaireTable.getColumns().addAll(nomCol, prenomCol, departementCol, anneeEntreeCol, promotionCol);
        
        // Ajouter le TableView à la VBox
        centerLeftBox.getChildren().add(stagiaireTable);
        
        // Ajouter la VBox centrée dans la GridPane
        gridPane.add(centerLeftBox, 1, 0);
    
        // Ajout du bottomPane à la grille
        gridPane.add(buttonBox, 0, 1);

        // Création de la scène et affichage de la fenêtre
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Page Utilisateur");
        primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}
