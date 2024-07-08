package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class FormJurnalEdit implements Initializable {
    private final GlobalSingleton singleton = GlobalSingleton.getInstance();

    @FXML
    private Label labelDate;
    @FXML
    private TextArea inputContent;
    @FXML
    private Button buttonSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JurnalEntry entry = singleton.getSelectedJurnalEntry();
        labelDate.setText(entry.getCreatedDate());
        inputContent.setText(entry.getContent());
        buttonSubmit.setDisable(true);
    }

    @FXML
    private void onContentTyped() {
        String content = inputContent.getText();
        buttonSubmit.setDisable(content.isEmpty());
    }

    @FXML
    private void onButtonSubmitPressed() {
        String content = inputContent.getText();
        int jurnalId = singleton.getSelectedJurnalEntry().getId();
        int userId = singleton.getUserId();

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi jurnal tidak bisa kosong!");
            return;
        }

        try {
            DBConfig.editJurnal(content, jurnalId, userId);
            App.changeRoot("home");
            JOptionPane.showMessageDialog(null, "Berhasil mengubah jurnal!");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onButtonBackPressed() {
        App.changeRoot("home");
    }
}
