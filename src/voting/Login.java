/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package voting;

import javafx.scene.paint.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class Login extends Application {

    @Override
    public void start(Stage loginStage) {
        Label title = new Label("Maple Leaf Online Voting System");
        title.setFont(new Font(25));
        title.setTextFill(Color.RED);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);

        Button login = new Button();
        login.setText("Login");
        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //Add the code you need here for verification of the user
                System.out.println("");
            }
        });


        GridPane gridPane = new GridPane();


        Label nameLabel = new Label("Name: ");
        Label passwordLabel = new Label("Password: ");
        TextField name = new TextField();
        PasswordField password =  new PasswordField();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(password, 0, 3);
        gridPane.add(login, 0, 5);


        // VBox vBox = new VBox(20, title, name, password, login);
        // vBox.setAlignment(Pos.TOP_CENTER);
        // vBox.setStyle("-fx-background-color: #000000;");
        // vBox.setSpacing(50);
        // vBox.setPadding(new Insets(0, 50, 50, 50));

        Scene scene = new Scene(gridPane, 400, 300);
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
