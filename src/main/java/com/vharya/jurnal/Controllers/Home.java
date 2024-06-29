package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Home {
    private final GlobalSingleton globalSingleton = GlobalSingleton.getInstance();

    @FXML
    private ListView<String> listviewJurnal;

    public Home() {
        try {
            ArrayList<JurnalEntry> jurnals = DBConfig.getAllJurnals(globalSingleton.getUserId());

            for (JurnalEntry jurnal : jurnals) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MMM/yyyy HH:mm");
                String formattedDate = dateFormat.format(jurnal.getCreatedDate());

                listviewJurnal.getItems().add(formattedDate);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi dengan Database gagal...");
        }
    }

    @FXML
    private void onMenuListPressed() {
        //
    }

    @FXML
    private void onMenuNewPressed() {
        //
    }

    @FXML
    private void onMenuEditPressed() {
        //
    }

    @FXML
    private void onMenuDeletePressed() {
        //
    }
}
