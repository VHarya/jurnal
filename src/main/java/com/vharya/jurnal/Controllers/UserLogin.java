package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserLogin implements Initializable {
    GlobalSingleton singleton = GlobalSingleton.getInstance();
    private final Image defaultProfile = new Image(App.getResourceAsStream("assets/images/avatar_square.png"));

    @FXML
    private ImageView imageProfile;
    @FXML
    private Label labelUsername;
    @FXML
    private PasswordField inputPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = singleton.getSelectedUser();
        labelUsername.setText(user.getName());

        initProfileImage(user.getFilepath());
    }

    private void initProfileImage(String filepath) {
        try {
            InputStream inputStream = new FileInputStream(filepath);
            imageProfile.setImage(new Image(inputStream));

            // if imageProfile has image
            // then stop and return
            // -sun tzu
            if (imageProfile.getImage() != null) return;
        }
        catch (IOException e) {
            imageProfile.setImage(defaultProfile);
            throw new RuntimeException(e);
        }

        Image image = new Image(App.getResourceAsStream("assets/images/avatar_square.png"));
        imageProfile.setImage(image);
    }

    @FXML
    private void onButtonSubmitPressed() {
        int id = singleton.getSelectedUser().getId();
        String password = inputPassword.getText();

        try {
            if (DBConfig.checkPassword(id, password)) {
                singleton.setUserId(id);
                singleton.setSelectedUser(null);
                App.changeRoot("home");
            } else {
                JOptionPane.showMessageDialog(null, "Kata sandi tidak sesuai");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @FXML
    private void onButtonBackPressed() {
        App.changeRoot("user_list");
    }
}
