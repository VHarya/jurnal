package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.JurnalEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Detail implements Initializable {
    @FXML
    private Label labelDatetime;
    @FXML
    private Label labelContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JurnalEntry entry = GlobalSingleton.getInstance().getJurnalEntry();
        labelDatetime.setText(entry.getCreatedDate());
        labelContent.setText(entry.getContent());
    }

    @FXML
    private void onButtonBackPressed() {
        App.changeRoot("home");
    }
}
