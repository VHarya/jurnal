package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormUserCreate implements Initializable {
    private final Image iconPasswordOpen = new Image(App.getResourceAsStream("assets/icons/eye.png"), 18, 18, true, true);
    private final Image iconPasswordClosed = new Image(App.getResourceAsStream("assets/icons/eye-slashed.png"), 18, 18, true, true);
    private final Image iconSelectFile = new Image(App.getResourceAsStream("assets/icons/folder-simple.png"), 18, 18, true, true);

    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputImage;
    @FXML
    private TextField inputPasswordShow;
    @FXML
    private PasswordField inputPasswordHide;
    @FXML
    private Button buttonShowPassword;
    @FXML
    private Button buttonSelectFile;
    @FXML
    private ImageView imageProfile;

    private File selectedImageFile;
    private boolean isPasswordShown = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtonIcons();
        loadImageProfile();
    }

    private void initButtonIcons() {
        ImageView imageShowPassword = new ImageView();
        ImageView imageSelectFile = new ImageView();

        imageShowPassword.setImage(isPasswordShown ? iconPasswordOpen : iconPasswordClosed);
        imageSelectFile.setImage(iconSelectFile);

        buttonShowPassword.setGraphic(imageShowPassword);
        buttonSelectFile.setGraphic(imageSelectFile);
    }

    private void loadImageProfile() {
        if (selectedImageFile == null) {
            Image profileImage = new Image(App.getResourceAsStream("assets/images/avatar_square.png"));
            imageProfile.setImage(profileImage);
            return;
        }

        try {
            InputStream inputStream = new FileInputStream(selectedImageFile);
            Image profileImage = new Image(inputStream);
            imageProfile.setImage(profileImage);
        }
        catch (FileNotFoundException e) {
            Image profileImage = new Image(App.getResourceAsStream("assets/images/avatar_square.png"));
            imageProfile.setImage(profileImage);

            JOptionPane.showMessageDialog(null, "Gambar yang dipilih tidak dapat diakses.\nCek jika file sudah benar.");
        }
    }

    private String saveSelectedImage(String id) throws IOException {
        String appDir = System.getProperty("user.dir");
        String filename = selectedImageFile.getName();
        String selectedImageFileExt = filename.substring(filename.lastIndexOf("."));

        File destination = new File(appDir + "/images/" + id + selectedImageFileExt);
        Files.copy(selectedImageFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return destination.getPath();
    }

    @FXML
    private void onButtonShowPasswordPressed() {
        isPasswordShown = !isPasswordShown;

        ImageView imageShowPassword = new ImageView();

        if (isPasswordShown) {
            inputPasswordShow.setText(inputPasswordHide.getText());
            inputPasswordHide.setVisible(false);
            inputPasswordShow.setVisible(true);
            imageShowPassword.setImage(iconPasswordOpen);
        }
        else {
            inputPasswordHide.setText(inputPasswordShow.getText());
            inputPasswordHide.setVisible(true);
            inputPasswordShow.setVisible(false);
            imageShowPassword.setImage(iconPasswordClosed);
        }

        buttonShowPassword.setGraphic(imageShowPassword);

    }

    @FXML
    private void onButtonSelectFilePressed() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");

        fileChooser.getExtensionFilters().add(filter);

        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) inputImage.setText(selectedImageFile.getAbsolutePath());
        loadImageProfile();
    }

    @FXML
    private void onButtonSubmitPressed() {
        String username = inputUsername.getText();
        String password = isPasswordShown ? inputPasswordShow.getText() : inputPasswordHide.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama tidak bisa kosong!");
            return;
        }

        try {
            Integer id = DBConfig.createUser(username, password, "");

            if (selectedImageFile != null && id != null) {
                String path = saveSelectedImage(String.valueOf(id));
                DBConfig.editUserImage(id, path);
            }

            App.changeRoot("user_list");

            JOptionPane.showMessageDialog(null, "Berhasil membuat user baru!");
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "[IOException]\nGagal menyimpan gambar ke directory!");
        }
        catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "[SQLException]\nGagal membuat user baru!");
        }
    }

    @FXML
    private void onButtonCancelPressed() {
        App.changeRoot("user_list");
    }
}
