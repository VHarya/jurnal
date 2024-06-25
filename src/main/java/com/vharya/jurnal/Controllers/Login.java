package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.DatabaseManager;
import com.vharya.jurnal.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
            DatabaseManager db = new DatabaseManager();
            User user = db.login(username, password);
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }
    }
}
