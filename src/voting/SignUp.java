// package voting;

import java.util.ArrayList;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class SignUp extends Application {
    static int port = 8888;
    static String host = "localhost"; // Change later to ip address
    static DataOutputStream dos;
    TextField phoneField, addressField, password, confirmPassword, emailField, cityField, nameField1, nameField2, postalcodeField, birthdate;
    ChoiceBox<String> gender, provinceTerritory;
    GridPane gridPane = new GridPane();
    static ArrayList<String[]> usernamePassword;
    @Override
    public void start(Stage signUpStage) {
        
        
        Label headerLabel = new Label("Registration Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel,HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        
        Button signIn = new Button("Sign Up");
        
        Label nameLabel1 = new Label("First Name: ");
        gridPane.add(nameLabel1, 0,1);
        
        nameField1 = new TextField();
        gridPane.add(nameField1, 1, 1);
        
        Label nameLabel2 = new Label("Surname: ");
        gridPane.add(nameLabel2, 0, 3);
        
        nameField2 = new TextField();
        gridPane.add(nameField2, 1, 3);
        
        Label passwordLabel = new Label("Password: ");
        gridPane.add(passwordLabel, 0, 4);
        
        password =  new PasswordField();
        gridPane.add(password, 1, 4);
        
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        gridPane.add(confirmPasswordLabel, 0, 5);
        
        confirmPassword =  new PasswordField();
        gridPane.add(confirmPassword, 1, 5);
        
        Label emailLabel = new Label("Email ID: ");
        gridPane.add(emailLabel, 0, 6);

        emailField = new TextField();
        gridPane.add(emailField, 1, 6);
        
        Label genderLabel = new Label("Gender: ");
        gridPane.add(genderLabel, 0, 7);
        
        gender = new ChoiceBox<>();
        gender.setValue("Select One");
        gender.getItems().addAll("Male", "Female", "Other");
        gridPane.add(gender, 1, 7);
        
        Label addressLabel = new Label("Home Address: ");
        gridPane.add(addressLabel, 0, 8);
        
        addressField = new TextField();
        gridPane.add(addressField, 1, 8);
        
        Label postalcodeLabel = new Label("Postal Code: ");
        gridPane.add(postalcodeLabel, 3, 8);
        
        postalcodeField = new TextField();
        gridPane.add(postalcodeField, 4, 8);
        
        Label cityLabel = new Label("City: ");
        gridPane.add(cityLabel, 6, 8);
        
        cityField = new TextField();
        gridPane.add(cityField, 7, 8);
        
        Label provinceTerritoryLabel = new Label("Province/Territory: ");
        gridPane.add(provinceTerritoryLabel, 0, 9);
        
        provinceTerritory = new ChoiceBox<>();
        provinceTerritory.setValue("Select One");
        provinceTerritory.getItems().addAll("Ontario", "Quebec", "British Columbia", "Nunavut", "Prince Eduard Island", "Newfoundland and Labrador", "Saskatchewan", "Manitoba", "Alberta", "Northwest Territories", "New Brunswick", "Nova Scota", "Yukon");
        gridPane.add(provinceTerritory, 1, 9);
        
        Label phoneLabel = new Label("Primary Phone Number: ");
        gridPane.add(phoneLabel, 0, 10);
        
        phoneField = new TextField();
        gridPane.add(phoneField, 1, 10);
        
        Label birthdateLabel = new Label("Birth Date: (dd/mm/yyyy) ");
        gridPane.add(birthdateLabel, 0, 11);
        
        birthdate = new TextField();
        gridPane.add(birthdate, 1, 11);
        
        
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String message = "It works!";

                if(validateName1Input() && validateName2Input() && validatePasswordInput() && validateConfirmPasswordInput() && validatePhoneNumberInput() 
                        && validateEmailAddressInput() && validateGenderInput() && validateAddressInput() && validatePostalCodeInput() && validateCityInput() 
                        && validateProvinceTerritoryInput() && validateBirthDate()){
                    try (Socket socket = connectToServer(port, host)) {
                        dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(message);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thank You!");
                    alert.setHeaderText(null);
                    alert.setContentText("Thank you for signing up. You will now be directed to the voting page.");
                    Optional <ButtonType> action = alert.showAndWait();
                    Stage votingStage = new Stage();
                    VotingPage voting = new VotingPage();
                    voting.start(votingStage);
                    votingStage.show();
                    signUpStage.close();
                }
            }
        });
        gridPane.add(signIn, 1, 12);
        
        Scene scene = new Scene(gridPane, 1500, 1000);
        
        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(scene);
        signUpStage.show();
    }
    
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
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

    private boolean validatePhoneNumberInput(){
        Pattern p = Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
        Matcher m = p.matcher(phoneField.getText());
        if(m.find() && m.group().equals(phoneField.getText())){
            return true;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid phone number.");
            return false;
        }
    }
    
    private boolean validateEmailAddressInput(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9. ]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); 
        Matcher m = p.matcher(emailField.getText());
        if(m.find() && m.group().equals(emailField.getText())){
            return true;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid email address.");
            return false;
        }
    }
    
    private boolean validatePasswordInput(){
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})"); 
        Matcher m = p.matcher(password.getText());
        if(m.find() && m.group().equals(password.getText())){
            return true;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid password.");
            return false;
        }
    }
    
    private boolean validateConfirmPasswordInput(){
        if(confirmPassword.getText().equals(password.getText())){
            return true;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter the password you entered above.");
            return false;
        }
    }
    
    private boolean validateName1Input(){
        if(nameField1.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your first name.");
            return false;
        }
        else
            return true;
    }
       
    private boolean validateName2Input(){
        if(nameField2.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your surname.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validateGenderInput(){
        if(gender.getValue().equals("Select One")) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your gender.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validateAddressInput(){
        if(addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your address.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validatePostalCodeInput(){
        if(postalcodeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your postal code.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validateCityInput(){
        if(cityField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your city.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validateProvinceTerritoryInput(){
        if(provinceTerritory.getValue().equals("Select One")) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your province/territory.");
            return false;
        }
        else
            return true;
    }
    
    private boolean validateBirthDate(){
        Pattern p = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        Matcher m = p.matcher(birthdate.getText());
        if(m.matches()){
            m.reset();
            if(m.find()){
                String day = m.group(1);
                String month = m.group(2);
                int year = Integer.parseInt(m.group(3));

                if (day.equals("31") && (month.equals("4") || month .equals("6") || month.equals("9") ||
                    month.equals("11") || month.equals("04") || month .equals("06") ||
                    month.equals("09"))){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                    "Form Error!", "Please enter a valid birthdate in the required form.");
                    return false;

                } 
                else if (month.equals("2") || month.equals("02")){
                    if(year % 4 == 0){
                        if(day.equals("30") || day.equals("31")){
                            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                            "Form Error!", "Please enter a valid birthdate in the required form.");
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                            "Form Error!", "Please enter a valid birthdate in the required form.");
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }
                else{                
                    return true;                
                }
            }
            else{
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                "Form Error!", "Please enter a valid birthdate in the required form.");
                return false;
            }         
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid birthdate in the required form.");
            return false;
        }  
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
