package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Form implements Initializable {
    @FXML
    TextField inputID;
    @FXML
    TextField inputDate;
    @FXML
    TextArea inputContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JurnalEntry entry = GlobalSingleton.getInstance().getJurnalEntry();

        if (entry != null) {
            inputID.setText(Integer.toString(entry.getId()));
            inputDate.setText(entry.getCreatedDate());
            inputContent.setText(entry.getContent());
        }
    }

    @FXML
    private void onButtonSubmitPressed() {
        if (inputID.getText().isEmpty()) {
            insertData();
        }
        else {
            updateData();
        }
    }

    @FXML
    private void onButtonBackPressed() {
        GlobalSingleton.getInstance().setJurnalEntry(null);
        App.changeRoot("home");
    }

    private void insertData() {
        try {
            String content = inputContent.getText();
            int userId = GlobalSingleton.getInstance().getUserId();

            DBConfig.insertJurnal(content, userId);

            GlobalSingleton.getInstance().setJurnalEntry(null);
            App.changeRoot("home");

            JOptionPane.showMessageDialog(null, "Berhasil Menambahkan Jurnal!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void updateData() {
        try {
            String content = inputContent.getText();
            int jurnalID = Integer.parseInt(inputID.getText());
            int userID = GlobalSingleton.getInstance().getUserId();

            DBConfig.updateJurnal(content, jurnalID, userID);

            GlobalSingleton.getInstance().setJurnalEntry(null);
            App.changeRoot("home");

            JOptionPane.showMessageDialog(null, "Berhasil Mengubah Jurnal!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
