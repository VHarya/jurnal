package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;

import java.sql.*;

import java.util.ArrayList;

public class DBConfig {
    private final static String DATABASE_URL = "jdbc:sqlite:jurnal_database.db";

    private static Connection getConnection() throws SQLException {
        String sqlCreateJurnals = "CREATE TABLE IF NOT EXIST jurnals (" +
                "id_jurnal INTEGER AUTO INCREMENT PRIMARY KEY NOT NULL, " +
                "content TEXT NOT NULL, " +
                "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        String sqlCreateUsers = "CREATE TABLE IF NOT EXIST users (" +
                "id_user INTEGER AUTO INCREMENT PRIMARY KEY NOT NULL, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL" +
                ");";

        Connection connection = DriverManager.getConnection(DATABASE_URL);
        Statement statement = connection.createStatement();
        statement.execute(sqlCreateJurnals);
        statement.execute(sqlCreateUsers);

        return connection;
    }

    public static Integer login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return res.getInt("id_user");
        }
        return null;
    }

    public static ArrayList<JurnalEntry> getAllJurnals(int userID) throws SQLException {
        String sql = "SELECT * FROM jurnals WHERE user_id=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);
        ResultSet res = statement.executeQuery();

        ArrayList<JurnalEntry> jurnals = new ArrayList<>();
        while (res.next()) {
            jurnals.add(new JurnalEntry(
                    res.getInt("id_jurnal"),
                    res.getString("isi"),
                    res.getTimestamp("tgl_buat"),
                    res.getTimestamp("tgl_edit")
            ));
        }

        return jurnals;
    }

    public static void insertJurnal(String content, int userID) throws SQLException {
        String sql = "INSERT INTO jurnals VALUES (null, ?, null, null, ?)";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, content);
        statement.setInt(2, userID);

        statement.execute();
    }

    public static void updateJurnal(String content, int id, int userID) throws SQLException {
        String sql = "UPDATE jurnals SET content=? WHERE id=? AND user_id=?";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, content);
        statement.setInt(2, id);
        statement.setInt(3, userID);

        statement.execute();
    }

    public static void deleteJurnal(int id, int userID) throws SQLException {
        String sql = "DELETE FROM jurnal WHERE id=? AND user_id=?";

        Connection connection = DBConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        statement.setInt(2, userID);

        statement.execute();
    }
}
