package models;

import java.sql.*;

public class TransactionModel {
    private Connection conn;

    public TransactionModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false", "fp510", "510");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTransaction(int userId, double amount, String type) {
        try {
            String query = "INSERT INTO transactions (user_id, amount, transaction_type) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.setString(3, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTransactions(int userId) {
        try {
            String query = "SELECT * FROM transactions WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
