package com.vharya.jurnal;

import java.sql.*;

public class DatabaseManager {
    private final static String DATABASE_URL = "jdbc:mysql://localhost/jurnal";
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "";


    public User login(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery("SELECT * FROM users");
            if (res.next()) {
                if (!username.equals(res.getString("username")) || !password.equals(res.getString("password"))) {
                    throw new Exception("Username or password is incorrect!");
                }

                int userId = res.getInt("id");
                String userName = res.getString("username");

                return new User(userId, userName);
            }

            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

}
