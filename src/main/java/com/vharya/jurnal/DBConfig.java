package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;
import com.vharya.jurnal.Models.User;

import java.sql.*;

import java.util.ArrayList;

public class DBConfig {
    private final static String DATABASE_URL = "jdbc:sqlite:jurnal_database.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initDatabase() throws SQLException {
        String sqlCreateJurnals = "CREATE TABLE IF NOT EXISTS jurnals (" +
                "id_jurnal INTEGER PRIMARY KEY, " +
                "content TEXT NOT NULL, " +
                "created_date VARCHAR(255) NOT NULL," +
                "id_user INTEGER NOT NULL," +
                "FOREIGN KEY (id_user) REFERENCES users(id_user)" +
                ")";

        String sqlCreateUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id_user INTEGER PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255), " +
                "filepath VARCHAR(255)" +
                ")";

//        String sqlCreateJurnals = "DROP TABLE jurnals";
//        String sqlCreateUsers = "DROP TABLE users";

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sqlCreateJurnals);
        statement.execute(sqlCreateUsers);

        connection.close();
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet res = statement.executeQuery();

        ArrayList<User> users = new ArrayList<>();
        while (res.next()) {
            users.add(new User(
                    res.getInt("id_user"),
                    res.getString("username"),
                    res.getString("filepath"),
                    !res.getString("password").isEmpty() // password NOT empty = user protected
            ));
        }

        connection.close();
        return users;
    }

    public static User getUserData(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id_user=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        ResultSet res = statement.executeQuery();

        User user = null;
        if (res.next()) {
            user = new User(
                    res.getInt("id_user"),
                    res.getString("username"),
                    res.getString("filepath"),
                    !res.getString("password").isEmpty() // password NOT empty = user protected
            );
        }

        connection.close();
        return user;
    }

    public static Integer createUser(String username, String password, String filepath) throws SQLException {
        String sql = "INSERT INTO users VALUES (NULL, ?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, filepath);

        statement.execute();

        Statement statement2 = connection.createStatement();
        ResultSet result = statement2.executeQuery("SELECT last_insert_rowid()");
        if (result.next()) {
            int id = result.getInt(1);
            connection.close();

            return id;
        }

        connection.close();
        return null;
    }

    public static void editUserImage(int id, String filepath) throws SQLException {
        String sql = "UPDATE users SET filepath=? WHERE id_user=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, filepath);
        statement.setInt(2, id);

        statement.execute();
        connection.close();
    }

    public static void editUser(int id, String username, String filepath) throws SQLException {
        String sql = "UPDATE users SET username=?, filepath=? WHERE id_user=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, filepath);
        statement.setInt(3, id);

        statement.execute();
        connection.close();
    }

    public static boolean changePassword(int id, String oldPassword, String newPassword) throws SQLException {
        boolean isPasswordCorrect = checkPassword(id, oldPassword);
        if (!isPasswordCorrect) return false;

        String sql = "UPDATE users SET password=? WHERE id_user=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, newPassword);
        statement.setInt(2, id);

        statement.execute();
        connection.close();
        return true;
    }

    public static boolean checkPassword(int id, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE id_user=? AND password=?";

        System.out.println("login password: " + password);

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int resID = resultSet.getInt("id_user");
            connection.close();

            return (id == resID);
        }
        return false;
    }

    public static void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id_user=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.execute();
        connection.close();
    }

    public static ArrayList<JurnalEntry> getAllJurnals(int userID) throws SQLException {
        String sql = "SELECT * FROM jurnals WHERE id_user=? ORDER BY created_date DESC";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);
        ResultSet res = statement.executeQuery();

        ArrayList<JurnalEntry> jurnals = new ArrayList<>();
        while (res.next()) {
            jurnals.add(new JurnalEntry(
                    res.getInt("id_jurnal"),
                    res.getString("content"),
                    res.getString("created_date")
            ));
        }

        connection.close();
        return jurnals;
    }

    public static void createJurnal(String content, String createdDate, int userID) throws SQLException {
        String sql = "INSERT INTO jurnals VALUES (NULL, ?, ?, ?)";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, content);
        statement.setString(2, createdDate);
        statement.setInt(3, userID);

        statement.execute();
        connection.close();
    }

    public static void editJurnal(String content, int id, int userID) throws SQLException {
        String sql = "UPDATE jurnals SET content=? WHERE id_jurnal=? AND id_user=?";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, content);
        statement.setInt(2, id);
        statement.setInt(3, userID);

        statement.execute();
        connection.close();
    }

    public static void deleteJurnal(int id, int userID) throws SQLException {
        String sql = "DELETE FROM jurnals WHERE id_jurnal=? AND id_user=?";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        statement.setInt(2, userID);

        statement.execute();
        connection.close();
    }
}
