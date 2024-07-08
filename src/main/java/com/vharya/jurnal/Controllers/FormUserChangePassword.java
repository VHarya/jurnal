package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormUserChangePassword implements Initializable {
    @FXML
    private PasswordField inputPasswordOld;
    @FXML
    private PasswordField inputPasswordNew;
    @FXML
    private PasswordField inputPasswordConfirm;
    @FXML
    private Label labelError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    @FXML
    private void onButtonSubmitPressed() {
        int id = GlobalSingleton.getInstance().getSelectedUser().getId();
        String oldPassword = inputPasswordOld.getText();
        String newPassword = inputPasswordNew.getText();
        String confirmPassword = inputPasswordConfirm.getText();

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Konfirmasi kata sandi tidak sama dengan kata sandi baru!");
            return;
        }

        try {
            boolean isSuccess = DBConfig.changePassword(id, oldPassword, confirmPassword);

            if (isSuccess) {
                App.changeRoot("user_list");
                JOptionPane.showMessageDialog(null, "Berhasil mengubah kata sandi");
            } else {
                JOptionPane.showMessageDialog(null, "Kata sandi lama salah!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onButtonBackPressed() {
        App.changeRoot("form_user_edit");
    }
}
