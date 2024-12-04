package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    // Action handler for the login button
    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if username and password match admin or player credentials
        if ("admin".equals(username) && "hashedadminpass".equals(password)) {
            // Successful login as admin, load AdminView
            loadView("/views/AdminView.fxml", "Admin");
        } else if (username.equals(username) && password.equals(password)) {
            // Successful login as player, load PlayerView
        	showSuccess("Player login successful!");
        } else {
            // Invalid credentials
            showError("Invalid username or password.");
        }
    }

    private void showSuccess(String message) {
        // Create an Alert of type INFORMATION to show success
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Success");
        alert.setHeaderText(null); // No header for this alert
        alert.setContentText(message); // The message passed into the method
        
        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }


	// Method to load the appropriate view based on the role
    private void loadView(String fxmlPath, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println(role + " logged in successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading " + role + " view.");
        }
    }

    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
