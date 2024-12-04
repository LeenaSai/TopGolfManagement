package utils;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;

public class DBConnect {

    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
    private static final String USER = "fp510";
    private static final String PASSWORD = "510"; 

    // Establish database connection
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Validate regular user login
    public boolean validateLogin(String username, String password) {
        try (Connection conn = connect()) {
            String query = "SELECT * FROM topgolf_users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate admin login (could be the same as regular login depending on the 'role')
    public boolean validateAdminLogin(String username, String password) {
        try (Connection conn = connect()) {
            String query = "SELECT * FROM topgolf_users WHERE username = ? AND password = ? AND role = 'admin'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user balance from the database
    public double getUserBalance(String username) {
        try (Connection conn = connect()) {
            String query = "SELECT balance FROM topgolf_users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Fetch all users from the database
    public ObservableList<User> fetchUsersFromDatabase() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (Connection conn = connect()) {
            String query = "SELECT * FROM topgolf_users";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Assuming the 'topgolf_users' table has id, username, password, role, and balance columns
                    users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    
    // Delete the last user added to the database
    public void deleteLastUser() {
        try (Connection conn = connect()) {
            String query = "DELETE FROM topgolf_users where username='staff1'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
