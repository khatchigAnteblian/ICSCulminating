/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

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
public class WelcomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Maple Leaf Online Voting System");
        title.setFont(new Font(25));
        title.setTextFill(Color.RED);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);

        Label welcomeText = new Label();
        welcomeText.setText("Maple Leaf Online Voting enables voters to cast their vote privately and easily from "
                + "any location and on any device with Internet access (PC, tablet, smartphone, etc.), ensuring maximum election engagement by enabling remote "
                + "and disabled voters to participate on equal terms. Voter privacy, election integrity, end-to-end security, vote correctness and full verifiability "
                + "(individual and universal) are guaranteed via advanced cryptographic protocols. This enables election officials to assure citizens "
                + "that their votes remain cast-as-intended, recorded-as-cast and counted-as-recorded. In addition to the added accessibility and security, operational "
                + "efficiencies result from significantly reduced costs and the delivery of more timely and accurate results.");
        welcomeText.setTextAlignment(TextAlignment.JUSTIFY);
        welcomeText.setContentDisplay(ContentDisplay.TEXT_ONLY);
        welcomeText.setWrapText(true);
        welcomeText.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage loginStage = new Stage();
                Login login = new Login();
                login.start(loginStage);
                loginStage.show();
                primaryStage.close();
            }
        });

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage signUpStage = new Stage();
                SignUp signUp = new SignUp();
                signUp.start(signUpStage);
                signUpStage.show();
                primaryStage.close();
            }
        });

        VBox vBox = new VBox(20, title, welcomeText);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-color: #000000;");
        vBox.setSpacing(50);
        vBox.setPadding(new Insets(0, 50, 50, 50));
        vBox.getChildren().add(loginBtn);
        vBox.getChildren().add(signUpBtn);

        Scene scene = new Scene(vBox, 1500, 1000);
        primaryStage.setTitle("Welcome to Maple Leaf Online Voting System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
