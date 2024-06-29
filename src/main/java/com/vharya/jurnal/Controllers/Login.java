package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorMessage;

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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }
}
