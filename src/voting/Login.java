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
import java.util.Optional;
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
public class Login extends Application {
    static final int port = 8888;
    static final String host = "localhost";
    static DataInputStream dis;
    static DataOutputStream dos;

    @Override
    public void start(Stage loginStage) {
        Label title = new Label("Maple Leaf Online Voting System");
        title.setFont(new Font(25));
        title.setTextFill(Color.RED);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);

        Label nameLabel = new Label("Name: ");
        Label passwordLabel = new Label("Password: ");
        TextField name = new TextField();
        PasswordField password =  new PasswordField();

        Button login = new Button();
        login.setText("Login");
        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = hash(name.getText()) + " " + hash(password.getText());
                String serverFeedback = "";
                try (Socket socket = connectToServer(port, host)) {
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(message);
                    dos.flush();
                    serverFeedback = dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (serverFeedback.equals("0")) {
                    Stage votingStage = new Stage();
                    VotingPage votingPage = new VotingPage();
                    votingPage.start(votingStage);
                    votingStage.show();
                    loginStage.close();
                } else if (serverFeedback.equals("1")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Incorrect Information!");
                    alert.setContentText("Username or Password is incorrect! Please try again.");
                    Optional<ButtonType> button = alert.showAndWait();
                }
            }
        });


        GridPane gridPane = new GridPane();


        gridPane.add(nameLabel, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(password, 0, 3);
        gridPane.add(login, 0, 5);

        Scene scene = new Scene(gridPane, 400, 300);
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
