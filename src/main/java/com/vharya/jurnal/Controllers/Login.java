package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    @FXML
    public void onSubmitButtonClicked() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            Integer id = DBConfig.login(username, password);
            if (id != null) {
                GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
                globalSingleton.setUserId(id);

                App.changeRoot("home");
            }
            else {
                errorMessage.setText("Username atau Password salah!");
            }
        } catch (SQLException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }
    }
}
