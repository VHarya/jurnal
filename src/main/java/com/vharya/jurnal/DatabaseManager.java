package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;
import com.vharya.jurnal.Models.User;

import javax.swing.*;
import java.sql.*;

public class DatabaseManager {
    private final static String DATABASE_URL = "jdbc:mysql://localhost/jurnal";
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "";

    public User login(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                JOptionPane.showMessageDialog(null, "Berhasil Login");
                return new User(
                        res.getInt("id"),
                        res.getString("username")
                );
            }

            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    public JurnalEntry getJurnals(int userID) {
        try {
            String sql = "SELECT * FROM jurnals WHERE id_user=?";

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                connection.close();
                JOptionPane.showMessageDialog(null, "Berhasil Login");
                return new JurnalEntry(
                        res.getInt("id"),
                        res.getString("content"),
                        res.getTimestamp("createdDate"),
                        res.getTimestamp("updateDate")
                );
            } else {
                connection.close();
                throw new Exception("Username or password is incorrect!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
