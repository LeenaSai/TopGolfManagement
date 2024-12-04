package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import models.User;
import utils.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private DBConnect dbConnect = new DBConnect(); // Initialize DBConnect instance

    // This method will be triggered when the button is clicked in the FXML
    public void displayAllUsers(MouseEvent event) {
        ObservableList<User> users = dbConnect.fetchUsersFromDatabase(); // Use DBConnect to fetch users
        System.out.println("Number of users fetched: " + users.size());  // Check the number of users
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            // Bind the TableView columns to the User properties
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
            
            userTable.setItems(users);  // Set the users to the TableView
        }
    }



    // Method to update the first record using DBConnect
    @FXML
    public void updateFirstRecord() {
        String query = "UPDATE topgolf_users SET username = ? WHERE username = 'player1'";  // Update first user
        try (Connection connection = dbConnect.connect();  // Using DBConnect
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, "UpdatedUsername");  // New username
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert("Success", "Successfully updated the first record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the record.");
        }
    }

    // Method to delete the last user using DBConnect
    @FXML
    public void deleteLastRecord() {
        try {
            dbConnect.deleteLastUser();  // Using DBConnect to delete the last user
            showAlert("Success", "Successfully deleted the last record.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while deleting the record.");
        }
    }

    // Method to display remaining users after deletion
    @FXML
    public void displayRemainingRecords() {
        ObservableList<User> users = dbConnect.fetchUsersFromDatabase(); // Fetch users
        System.out.println("Remaining Users in the Database:");
        System.out.println(String.format("%-20s %-20s %-10s", "Username", "Role", "Password"));

        for (User user : users) {
            System.out.println(String.format("%-20s %-20s %-10s", user.getUsername(), user.getRole(), user.getPassword()));
        }
    }

    // Method to display the snapshot of the user table (username and password)
    @FXML
    public void displayUserTable() {
        ObservableList<User> users = dbConnect.fetchUsersFromDatabase(); // Fetch users
        System.out.println("User Table Snapshot:");
        System.out.println(String.format("%-20s %-20s", "Username", "Password"));

        for (User user : users) {
            System.out.println(String.format("%-20s %-20s", user.getUsername(), user.getPassword()));
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
