package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;

import java.sql.*;
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
}
