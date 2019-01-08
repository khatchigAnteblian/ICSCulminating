/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package voting;

/**
 *
 * @author Vedant
 */
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
    public class PostVotingPage extends Application{

        public void start(Stage postVotingStage){            
            GridPane gridPane = new GridPane();
            Label title = new Label("Thank You for Voting");
            title.setFont(new Font(25));
            title.setTextFill(Color.RED);
            title.setContentDisplay(ContentDisplay.TOP);
            title.setTextAlignment(TextAlignment.JUSTIFY);
            
            Scene scene = new Scene(gridPane, 1500, 1000);
            postVotingStage.setTitle("Thank You for Voting!");
            postVotingStage.setScene(scene);
            postVotingStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}