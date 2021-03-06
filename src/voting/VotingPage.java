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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class VotingPage extends Application{
    static final int port = 8888;
    static final String host = "localhost"; // Change this later to ip address
    static final String dbasePath = "../../Database/";
    static DataOutputStream dos;
    
    public void start(Stage votingStage){
        GridPane gridPane = new GridPane();        
        
        Label header = new Label("Candidates");
        header.setFont(Font.font("Verdana"));
        gridPane.add(header, 0, 0);
        
        final ToggleGroup group = new ToggleGroup();

        Label candidate1Label = new Label("Liberal Party of Canada");
        gridPane.add(candidate1Label, 0, 1);
        
        RadioButton candidate1 = new RadioButton("Justin Trudeau");
        candidate1.setUserData("Justin Trudeau");
        candidate1.setToggleGroup(group);
        gridPane.add(candidate1, 1, 1);

        Label candidate2Label = new Label("Conservative Party of Canada");
        gridPane.add(candidate2Label, 0, 2);
        
        RadioButton candidate2 = new RadioButton("Andrew Scheer");
        candidate2.setUserData("Andrew Scheer");
        candidate2.setToggleGroup(group);
        gridPane.add(candidate2, 1, 2);

        Label candidate3Label = new Label("New Democratic Party");
        gridPane.add(candidate3Label, 0, 3);
        
        RadioButton candidate3 = new RadioButton("Jagmeet Singh");
        candidate3.setUserData("Jagmeet Singh");
        candidate3.setToggleGroup(group);
        gridPane.add(candidate3, 1, 3);

        Label candidate4Label = new Label("Bloc Québécois");
        gridPane.add(candidate4Label, 0, 4);
        
        RadioButton candidate4 = new RadioButton("Mario Beaulieu");
        candidate4.setUserData("Mario Beaulieu");
        candidate4.setToggleGroup(group);
        gridPane.add(candidate4, 1, 4);
        
        Label candidate5Label = new Label("Green Party of Canada");
        gridPane.add(candidate5Label, 0, 5);
        
        RadioButton candidate5 = new RadioButton("Elizabeth May");
        candidate5.setUserData("Elizabeth May");
        candidate5.setToggleGroup(group);
        gridPane.add(candidate5, 1, 5);
        
        Label candidate6Label = new Label("People's Party of Canada");
        gridPane.add(candidate6Label, 0, 6);
        
        RadioButton candidate6 = new RadioButton("Maxime Bernier");
        candidate6.setUserData("Maxime Bernier");
        candidate6.setToggleGroup(group);
        gridPane.add(candidate6, 1, 6);
        
        Button castVote = new Button("Cast Vote");        
        ButtonType confirmVoteYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType confirmVoteNo = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);
        
        castVote.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println(group.getSelectedToggle().getUserData().toString());
                if (group.getSelectedToggle() != null) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Vote");
                    alert.setHeaderText(null);
                    alert.setContentText("Is this your final choice?");
                    Optional <ButtonType> action = alert.showAndWait();
                 
                    if(action.get() == ButtonType.OK){
                        String message = hash(group.getSelectedToggle().getUserData().toString());
                        try (Socket socket = connectToServer(port, host)) {
                            dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF(message);
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage postVotingStage = new Stage();
                        PostVotingPage postVoting = new PostVotingPage();
                        postVoting.start(postVotingStage);
                        postVotingStage.show();
                        votingStage.close();
                    }
                }
            }
        });
        gridPane.add(castVote, 1, 12);
        
        
        
        Scene scene = new Scene(gridPane, 1500, 1000);
        
        votingStage.setTitle("Sign Up");
        votingStage.setScene(scene);
        votingStage.show();
    }

    public static Socket connectToServer(int port, String host) {
        // Search the IP address and port number of the host and connect to it
        Socket socket;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            socket = null;
        }
        return socket;
    }

    public static String hash(String text) {
        String hex = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            hex = String.format("%064x", new BigInteger(1, digest));
      
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

