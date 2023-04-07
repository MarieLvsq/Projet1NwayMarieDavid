package Projet1Perso.Projet1NwayMarieDavid.scene;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

import Projet1Perso.Projet1NwayMarieDavid.binaire.StagiaireBinaire;

public class TableViewStagiaires extends Scene {

    // attributs
    private final BorderPane root;
    private final Button boutonModifier;
    private final Button boutonSupprimer;
    private final Button boutonImprimer;
    private final Button boutonRetour;

    // constructeur
    public TableViewStagiaires(Stage stage, List<StagiaireBinaire> listeDeStagiaires,StagiaireBinaire criteres, Boolean administrateur) {

        super(new BorderPane(), 1000, 600);
        root = (BorderPane) this.getRoot();
        root.setStyle(("-fx-font-family: 'Arial'"));
		// Création de la scène avec un dégradé de couleur bleu canard vers blanc

		Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPadding(new Insets(50, 50, 10, 50));

        boutonModifier = new Button("Modifier");
        boutonSupprimer = new Button("Supprimer");
        boutonImprimer = new Button("Imprimer");
        boutonRetour = new Button("Retour");
        HBox hb = new HBox();
        AnchorPane monTableau = new AnchorPane();
        Label titre = new Label("Liste des stagiaires");

        ObservableList<StagiaireBinaire> observableStagiaires = FXCollections.observableArrayList(listeDeStagiaires);

        // on instancie une Tableview que l'on bind à la liste observable
        TableView<StagiaireBinaire> tableView = new TableView<>(observableStagiaires);
        tableView.setEditable(true);

        // on instancie les colonnes de la tableview
        TableColumn<StagiaireBinaire, String> nomCol = new TableColumn<StagiaireBinaire, String>("Nom");

        // on donne le nom du stagiaire à la colonne
        nomCol.setCellValueFactory(new PropertyValueFactory<StagiaireBinaire, String>("nom")); // objet qui va construire la valeur à afficher dans la case

        // prenom
        TableColumn<StagiaireBinaire, String> prenomCol = new TableColumn<StagiaireBinaire, String>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<StagiaireBinaire, String>("prenom")); // objet qui va construire la valeur à afficher dans la case

        // departement
        TableColumn<StagiaireBinaire, String> departementCol = new TableColumn<StagiaireBinaire, String>("Département");
        departementCol.setCellValueFactory( new PropertyValueFactory<StagiaireBinaire, String>("departement"));

        // promo
        TableColumn<StagiaireBinaire, String> promoCol = new TableColumn<StagiaireBinaire, String>("Promotion");
        promoCol.setCellValueFactory(new PropertyValueFactory<StagiaireBinaire, String>("promo"));

        // année
        TableColumn<StagiaireBinaire, String> anneeCol = new TableColumn<StagiaireBinaire, String>("Année de formation");
        anneeCol.setCellValueFactory(new PropertyValueFactory<StagiaireBinaire, String>("anneeDeFormation"));

        // On ajoute les colonnes au tableview
        tableView.getColumns().addAll(nomCol, prenomCol, departementCol, promoCol, anneeCol);

        // on équilibre les colonnes du tableau pour qu'il prenne toute la place disponible
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // on ajoute le tableview à la liste des Nodes enfants de l'Anchorpane
        monTableau.getChildren().add(tableView);

        // on définit la taille de l'anchorpane et de ses ancres
        AnchorPane.setTopAnchor(tableView, 0.);
        AnchorPane.setLeftAnchor(tableView, 0.);
        AnchorPane.setRightAnchor(tableView, 0.);
        AnchorPane.setBottomAnchor(tableView, 0.);

        // dimension des boutons
        boutonModifier.setPrefSize(160, 10);
        boutonSupprimer.setPrefSize(160, 10);
        boutonImprimer.setPrefSize(160, 10);
        boutonRetour.setPrefSize(160, 10);

        //Pour faire apparitre les boutons supprimer ou modifier en fonction du compte
        boutonModifier.setVisible(administrateur);
        boutonSupprimer.setVisible(administrateur);

        // couleurs et style
        tableView.setStyle("-fx-font-family: 'Arial'");
        monTableau.setStyle("-fx-font-family: 'Arial'");

        // on ajoute les boutons à la HBox
        hb.getChildren().addAll(boutonModifier, boutonSupprimer, boutonImprimer, boutonRetour);

        // on dimensionne la HBox
        hb.setPrefSize(300, 60);

        // on centre les boutons de la HBox et on les espace
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(80);

        // on place l'anchorpane, la HBox et le label dans le borderpane + espacements entre les composants + centrage du titre
        root.setCenter(monTableau);
        root.setBottom(hb);
        root.setTop(titre);
        root.setStyle("-fx-font-family: 'Arial'");

        BorderPane.setMargin(monTableau,(new Insets(5, 10, 20, 10)));
        BorderPane.setAlignment(titre, Pos.BASELINE_CENTER);

        //Actions des boutons
        actionBtnSupprimer(tableView, stage, criteres);
        //actionBtnImprimer(listeDeStagiaires);
        actionBtnModifer(tableView, stage, criteres);
        actionBtnRetour(stage, administrateur);

    }

    private void actionBtnSupprimer(TableView<StagiaireBinaire> tableView, Stage stage, StagiaireBinaire criteres) {
        boutonSupprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // creation du stagiaire a supprimer depuis la liste
                Stage stagePopup = new Stage();

                StagiaireBinaire stagiaireASupprimer = tableView.getSelectionModel().getSelectedItem();
                if (stagiaireASupprimer != null) {

                    stagePopup.setResizable(false);
                    stagePopup.setTitle("ANNUAIRE");
                    try {
                        stagePopup.setScene(new ExtraScene(stagePopup, stage, stagiaireASupprimer, criteres));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    stagePopup.getIcons().add(new Image("LogoIsika.jpg"));
                    stagePopup.show();
                } else {
                    // TODO: voir s'il est possible d'afficher le texte pour une durée donnée, ajout couleur
                    Label attention = new Label("Veuillez selectionner un stagiaire");
                    attention.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                    root.setTop(attention);
                }
            }
        });
       
    }

//    private void actionBtnImprimer(List<StagiaireBinaire> listeDeStagiaires) {
//        boutonImprimer.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//               ImpressionPDF pdf = new ImpressionPDF();
//                Stage stage = (Stage) boutonImprimer.getScene().getWindow();
//
//                try {
//                    pdf.createPDF(listeDeStagiaires);
//                    pdf.start(stage);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }

    private void actionBtnModifer(TableView<StagiaireBinaire> tableView, Stage stage, StagiaireBinaire criteres) {
        boutonModifier.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                StagiaireBinaire stagiaireAModifier = tableView.getSelectionModel().getSelectedItem();
                if (stagiaireAModifier != null) {
                    SceneModif modificationScene = null;
                    try {
                        modificationScene = new SceneModif(stage, stagiaireAModifier, criteres);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    modificationScene.getStylesheets().add("style.css");
                    stage.setScene(modificationScene);
                } else {
                    // TODO: voir s'il est possible d'afficher le texte pour une durée donnée, ajout couleur
                    Label attention = new Label("Veuillez selectionner un stagiaire");
                    attention.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                    root.setTop(attention);
                }
            }

        });
    }

    private void actionBtnRetour(Stage stage, Boolean administrateur) {
        boutonRetour.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Accueil accueilUtilisateurScene = null;
                try {
                    accueilUtilisateurScene = new Accueil(stage, administrateur);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                accueilUtilisateurScene.getStylesheets().add("style.css");
                stage.setScene(accueilUtilisateurScene);
            }
        
        });
    }
            
    
  }
	




