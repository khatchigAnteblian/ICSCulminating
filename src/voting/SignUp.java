/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voting;

import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
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
public class SignUp extends Application {
    static int port = 8888;
    static String host = "localhost"; // Change later to ip address
    static DataOutputStream dos;

    @Override
    public void start(Stage signUpStage) {
        GridPane gridPane = new GridPane();

        Label headerLabel = new Label("Registration Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel,HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Button signIn = new Button();
        signIn.setText("Sign Up");

        Label nameLabel1 = new Label("First Name: ");
        gridPane.add(nameLabel1, 0,1);

        TextField nameField1 = new TextField();
        gridPane.add(nameField1, 1, 1);

        Label nameLabel3 = new Label("Surname: ");
        gridPane.add(nameLabel3, 0, 3);

        TextField nameField3 = new TextField();
        gridPane.add(nameField3, 1, 3);

        Label passwordLabel = new Label("Password: ");
        gridPane.add(passwordLabel, 0, 4);

        PasswordField password =  new PasswordField();
        gridPane.add(password, 1, 4);

        Label emailLabel = new Label("Email ID: ");
        gridPane.add(emailLabel, 0, 5);

        TextField emailField = new TextField();
        gridPane.add(emailField, 1, 5);

        Label genderLabel = new Label("Gender: ");
        gridPane.add(genderLabel, 0, 6);

        ChoiceBox<String> gender = new ChoiceBox<>();
        gender.setValue("Select One");
        gender.getItems().addAll("Male", "Female", "Other");
        gridPane.add(gender, 1, 6);

        Label addressLabel = new Label("Home Address: ");
        gridPane.add(addressLabel, 0, 7);

        TextField addressField = new TextField();
        gridPane.add(addressField, 1, 7);

        Label postalcodeLabel = new Label("Postal Code: ");
        gridPane.add(postalcodeLabel, 3, 7);

        TextField postalcodeField = new TextField();
        gridPane.add(postalcodeField, 4, 7);

        Label cityLabel = new Label("City: ");
        gridPane.add(cityLabel, 6, 7);

        TextField cityField = new TextField();
        gridPane.add(cityField, 7, 7);

        Label provinceTerritoryLabel = new Label("Province/Territory: ");
        gridPane.add(provinceTerritoryLabel, 0, 8);

        ChoiceBox<String> provinceTerritory = new ChoiceBox<>();
        provinceTerritory.setValue("Select One");
        provinceTerritory.getItems().addAll("Ontario", "Quebec", "British Columbia", "Nunavut", "Prince Eduard Island", "Newfoundland and Labrador", "Saskatchewan", "Manitoba", "Alberta", "Northwest Territories", "New Brunswick", "Nova Scota", "Yukon");
        gridPane.add(provinceTerritory, 1, 8);

        Label phoneLabel = new Label("Primary Phone Number: ");
        gridPane.add(phoneLabel, 0, 9);

        TextField phoneField = new TextField();
        gridPane.add(phoneField, 1, 9);

        //I will add the Date of Birth, phone number, and a few other things soon.
        Pattern p = Pattern.compile("");

        signIn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = "";

                if(nameField1.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your first name.");
                    return;
                } else {
                    message += hash(nameField1.getText()) + " ";
                }

                if(nameField3.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your surname.");
                    return;
                } else {
                    message += hash(nameField3.getText()) + " ";
                }

                if(password.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter a password.");
                    return;
                } else {
                    message += hash(password.getText()) + " ";
                }

                if(emailField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your email address.");
                    return;
                } else {
                    message += hash(emailField.getText()) + " ";
                }

                if(gender.getValue().equals("Select One")) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your gender.");
                    return;
                } else {
                    message += hash(gender.getValue()) + " ";
                }

                if(addressField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your address.");
                    return;
                } else {
                    message += hash(addressField.getText()) + " ";
                }

                if(postalcodeField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your postal code.");
                    return;
                } else {
                    message += hash(postalcodeField.getText()) + " ";
                }

                if(cityField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your city.");
                    return;
                } else {
                    message += hash(cityField.getText()) + " ";
                }

                if(provinceTerritory.getValue().equals("Select One")) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
                    "Form Error!", "Please enter your province/territory.");
                    return;
                } else {
                    message += hash(provinceTerritory.getValue()) + " ";
                }

                //Add the code you need here to add the new voter to the database
                try (Socket socket = connectToServer(port, host)) {
                    System.out.println("Connected to host: " + host);
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(message);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.add(signIn, 1, 10);

        Scene scene = new Scene(gridPane, 1500, 1000);

        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(scene);
        signUpStage.show();
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


    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
