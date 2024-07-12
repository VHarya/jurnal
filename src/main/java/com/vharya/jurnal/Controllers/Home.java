package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import com.vharya.jurnal.Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private final GlobalSingleton singleton = GlobalSingleton.getInstance();

    @FXML
    private ImageView imageProfile;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelContent;
    @FXML
    private ListView<JurnalEntry> listviewJurnal;
    @FXML
    private ScrollPane containerContent;
    @FXML
    private VBox containerEmpty;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUserProfile();
        loadTable();

        if (listviewJurnal.getItems().isEmpty()) {
            isListViewEmpty(true);
        } else {
            isListViewEmpty(false);
        }
    }

    private void initUserProfile() {
        User user;
        try {
            user = DBConfig.getUserData(singleton.getUserId());

            labelUsername.setText(user.getName());

            if (user.getFilepath() == null) return;
            if (user.getFilepath().isEmpty()) return;

            InputStream inputStream = new FileInputStream(user.getFilepath());
            Image image = new Image(inputStream);
            imageProfile.setImage(image);
        }
        catch (SQLException e) {
            App.changeRoot("user_list");

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Terjadi Kesalahan saat mengambil data user!",
                    JOptionPane.ERROR_MESSAGE
            );

            throw new RuntimeException(e);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTable() {
        try {
            ArrayList<JurnalEntry> jurnals = DBConfig.getAllJurnals(singleton.getUserId());

            listviewJurnal.getItems().clear();
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

            listviewJurnal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<JurnalEntry>() {
                @Override
                public void changed(ObservableValue<? extends JurnalEntry> observableValue, JurnalEntry oldEntry, JurnalEntry newEntry) {
                    singleton.setSelectedJurnalEntry(newEntry);
                    loadContent(newEntry);
                }
            });

            // If selected entry stored isn't null
            // then select the stored entry
            JurnalEntry selectedJurnalEntry = singleton.getSelectedJurnalEntry();
            if (selectedJurnalEntry != null) {
                for (JurnalEntry jurnalEntry : listviewJurnal.getItems()) {
                    if (jurnalEntry.getId() != selectedJurnalEntry.getId()) continue;

                    listviewJurnal.getSelectionModel().select(jurnalEntry);
                    loadContent(jurnalEntry);
                    break;
                }
                return;
            }

            listviewJurnal.getSelectionModel().selectFirst(); // else select first
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tidak bisa menampilkan daftar jurnal karena\n" + e.getMessage());
        }
    }

    private void loadContent(JurnalEntry entry) {
        labelDate.setText(entry.getCreatedDate());
        labelContent.setText(entry.getContent());
    }

    private void isListViewEmpty(boolean isEmpty) {
        containerEmpty.setVisible(isEmpty);
        containerContent.setVisible(!isEmpty);
    }

    @FXML
    private void onListItemPressed() {
        listviewJurnal.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onButtonCreatePressed() {
        GlobalSingleton globalSingleton = singleton;
        globalSingleton.setSelectedJurnalEntry(null);

        App.changeRoot("form_jurnal_create");
    }

    @FXML
    private void onButtonEditPressed() {
        App.changeRoot("form_jurnal_edit");
    }

    @FXML
    private void onButtonDeletePressed() {
        JurnalEntry entry = singleton.getSelectedJurnalEntry();
        int confirmDelete = JOptionPane.showConfirmDialog(
                null,
                "Jurnal yang dihapus tidak bisa dikembalikan\n" +
                        "Lanjutkan menghapus jurnal " + entry.getCreatedDate() + "?",
                "Apakah anda yakin ingin menghapus jurnal?",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmDelete == JOptionPane.YES_OPTION) {
            try {
                DBConfig.deleteJurnal(
                        entry.getId(),
                        singleton.getUserId()
                );

                singleton.setSelectedJurnalEntry(null);
                loadTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    @FXML
    private void onButtonLogoutPressed() {
        singleton.clearData();
        App.changeRoot("user_list");
    }
}
