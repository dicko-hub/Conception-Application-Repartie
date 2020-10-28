package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.Date;

public class Login extends Application {
    private String Pseudo;
    private String Login;
    private String Pwd;
    User Client;
    private Date dateMsg= Date.from(Instant.now());
    private String nomMsg =" ";
    private String texteMsg= " ";
    public boolean connecte = false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("authentication Form JavaFX Application");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControlsLogin(gridPane, primaryStage);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 500, 250);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();

    }
    /*
    * Definis juste les dimension des champs pour l'authentification
     */
    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    /*
     * Definis juste les dimension des champs pour le dialogue
     */
    private GridPane createAppPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    /*
    * Gerer en partie authentification en recuperent les elements et procede a la verrification
     */
    private void addUIControlsLogin(GridPane gridPane, Stage primaryStage) {
        // Add Header
        Label headerLabel = new Label("authentication Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Name Label
        Label nameLabel = new Label("Pseudo : ");
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);


        // Add Email Label
        Label loginLabel = new Label("Login : ");
        gridPane.add(loginLabel, 0, 2);

        // Add Email Text Field
        TextField loginField = new TextField();
        loginField.setPrefHeight(40);
        gridPane.add(loginField, 1, 2);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nameField.getText().isEmpty() && nameField.getLength() > 2) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Veuiller mettre un pseudo d'au moins 2 lettres");
                    return;
                }
                if (loginField.getText().isEmpty() && loginField.getLength() > 2) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Veuiller mettre un login d'au moins 2 lettres");
                    return;
                }
                if (passwordField.getText().isEmpty() && passwordField.getLength() > 2) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Veuiller mettre un password d'au moins 2 lettres");
                    return;
                }

                /*
                * Declenche la prise en main de la seconde fenetre
                 */
                secondFrame(nameField.getText(),loginField.getText(),passwordField.getText(),primaryStage);
            }
        });
    }

    public void secondFrame(String nameField,String loginField,String passwordField, Stage primaryStage ){
        Pseudo = nameField;
        Login = loginField;
        Pwd = passwordField;

        try {
            Client = new User(this,getPseudo(), getLogin(), getPwd());
            connecte=Client.isConnecte();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        do{
            // Create the registration form grid pane
            GridPane gridPanePage = createAppPane();
            // Add UI controls to the registration form grid pane
            addUIControlsPage(gridPanePage, primaryStage);
            // Create a scene with registration form grid pane as the root node
            Scene scenePage = new Scene(gridPanePage, 600, 400);
            // Set the scene in primary stage
            primaryStage.setScene(scenePage);

            primaryStage.show();
        }while(!nomMsg.equals("disconnect"));
    }
    /*
    * Controller la page de dialogue
     */
    private void addUIControlsPage(GridPane gridPane, Stage primaryStage) {
        // Add Header
        Label headerLabel = new Label("App MiniChat \n " +
                "Vous pouvez entrer un message et disconnect pour terminer\n " +
                "Pour un message cibler, ecrire 'nomPersonne : message'  \n " +
                "Envoyer 'get : nbre' pour les nbre derniers messages du chat");
        headerLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add date Label
        Label dateLabel = new Label("Date : ");
        gridPane.add(dateLabel, 0, 1);

        Label dateTextLabel = new Label(dateMsg.toString());
        gridPane.add(dateTextLabel, 1, 1);


        // Add Pseudo Label
        Label pseudoLabel = new Label("Pseudo : ");
        gridPane.add(pseudoLabel, 0, 2);

        // Add Pseudo Text label
        Label pseudoTextLabel = new Label(nomMsg);
        gridPane.add(pseudoTextLabel, 1, 2);

        // Add Message Label
        Label messageLabel = new Label("Message: ");
        gridPane.add(messageLabel, 0, 3);

        // Add Message Texte label
        Label messageTextLabel = new Label(texteMsg);
        gridPane.add(messageTextLabel, 1, 3);

        // Add Reponse Label
        Label reponseLabel = new Label("Reponse :  ");
        gridPane.add(reponseLabel, 0, 4);

        // Add Response Texte field
        TextField reponseField = new TextField();
        reponseField.setPrefHeight(40);
        gridPane.add(reponseField, 1, 4);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Client.checkSend(reponseField.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Receive(Date date, String nom,String message){
        dateMsg=date;
        nomMsg=nom;
        texteMsg=message;
    }
    public void ReceiveList(int max){
        dateMsg=Date.from(Instant.now());
        nomMsg="Serveur";
        texteMsg="L'historrique contient au max "+max+" messages";
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public String getPseudo(){
        return Pseudo;
    }
    public String getLogin() {
        return Login;
    }

    public String getPwd() {
        return Pwd;
    }
        public static void main(String[] args) {
        launch(args);
    }
}
