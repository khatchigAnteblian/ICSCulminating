/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

/**
 *
 * @author Vedant
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class ContactUsPage extends Application{
    
    public void start(Stage contactUsStage){
        GridPane gridPane = new GridPane();
        
        Label contactUsLabel = new Label("Contact Us");
        contactUsLabel.setFont(Font.font(30));
        gridPane.add(contactUsLabel, 15, 0);
        
        Label developers = new Label("Developed By: Vedant Shah and Khatchig Anteblian");
        developers.setFont(Font.font(20));
        gridPane.add(developers, 0, 5);
        
        Label developersEmail_ID = new Label("Email ID: Vedant: vedant18@hotmail.com   Khatchig: khatchig99@mgial.com");
        developers.setFont(Font.font(20));
        gridPane.add(developersEmail_ID, 0, 10);
        
        Label developersPhoneNumber = new Label("Contact Number: Vedant: (647) 444-4444   Khatchig: (647) 555-5555");
        developersPhoneNumber.setFont(Font.font(20));
        gridPane.add(developersPhoneNumber, 0, 15);
        
        Scene scene = new Scene(gridPane, 1500, 1000);
        
        contactUsStage.setTitle("Contact Us");
        contactUsStage.setScene(scene);
        contactUsStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    
}

