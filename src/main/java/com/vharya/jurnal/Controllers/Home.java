package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {
    @FXML
    private Button buttonDetail;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private ListView<JurnalEntry> listviewJurnal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        GlobalSingleton.getInstance().setSelectedJurnalEntry(null);
        setButtonsEnabled(false);
    }

    private void loadTable() {
        try {
            GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
            ArrayList<JurnalEntry> jurnals = DBConfig.getAllJurnals(globalSingleton.getUserId());

            listviewJurnal.getItems().addAll(jurnals);
            listviewJurnal.setCellFactory(jurnalEntryListView -> new ListCell<JurnalEntry>() {
                @Override
                protected void updateItem(JurnalEntry jurnalEntry, boolean empty) {
                    super.updateItem(jurnalEntry, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(jurnalEntry.getCreatedDate());
                    }
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tidak bisa menampilkan daftar jurnal karena\n" + e.getMessage());
        }
    }

    private void setButtonsEnabled(boolean val) {
        buttonDetail.setDisable(!val);
        buttonEdit.setDisable(!val);
        buttonDelete.setDisable(!val);
    }

    @FXML
    private void onListItemPressed() {
        setButtonsEnabled(true);
    }

    @FXML
    private void onButtonDetailPressed() {
        JurnalEntry entry = listviewJurnal.getSelectionModel().getSelectedItem();
        GlobalSingleton.getInstance().setSelectedJurnalEntry(entry);

        App.changeRoot("detail");
    }

    @FXML
    private void onButtonCreatePressed() {
        GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
        globalSingleton.setSelectedJurnalEntry(null);

        App.changeRoot("form");
    }

    @FXML
    private void onButtonEditPressed() {
        JurnalEntry entry = listviewJurnal.getSelectionModel().getSelectedItem();
        GlobalSingleton.getInstance().setSelectedJurnalEntry(entry);

        App.changeRoot("form");
    }

    @FXML
    private void onButtonDeletePressed() {
        JurnalEntry entry = GlobalSingleton.getInstance().getJurnalEntry();
        int confirmDelete = JOptionPane.showConfirmDialog(
                null,
                "Apakah and yakin ingin menghapus jurnal " + entry.getCreatedDate() + "?"
        );

        if (confirmDelete == JOptionPane.YES_OPTION) {
            try {
                DBConfig.deleteJurnal(
                        entry.getId(),
                        GlobalSingleton.getInstance().getUserId()
                );
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    @FXML
    private void onButtonLogoutPressed() {
        GlobalSingleton.getInstance().clearData();
        App.changeRoot("login");
    }
}
