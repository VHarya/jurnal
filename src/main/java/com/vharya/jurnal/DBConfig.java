package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;

import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;

public class DBConfig {
    private final static String DATABASE_URL = "jdbc:mysql://localhost/jurnal";
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public static Integer login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return res.getInt("id");
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
                    res.getInt("id"),
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
