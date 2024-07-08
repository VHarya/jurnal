package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.User;
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

public class FormUserEdit implements Initializable {
    private final GlobalSingleton singleton = GlobalSingleton.getInstance();

    private final Image iconSelectFile = new Image(App.getResourceAsStream("assets/icons/folder-simple.png"), 18, 18, true, true);

    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputImage;
    @FXML
    private Button buttonSelectFile;
    @FXML
    private ImageView imageProfile;

    private File selectedImageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtonIcons();
        initFillInputs();
        loadImageProfile();
    }

    private void initButtonIcons() {
        ImageView imageSelectFile = new ImageView();
        imageSelectFile.setImage(iconSelectFile);
        buttonSelectFile.setGraphic(imageSelectFile);
    }

    private void initFillInputs() {
        User user = singleton.getSelectedUser();
        inputUsername.setText(user.getName());
        inputImage.setText(user.getFilepath());

        if (user.getFilepath() == null) return;
        if (user.getFilepath().isEmpty()) return;

        selectedImageFile = new File(user.getFilepath());
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
    private void onButtonSelectFilePressed() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");

        fileChooser.getExtensionFilters().add(filter);

        selectedImageFile = fileChooser.showOpenDialog(null);
        inputImage.setText(selectedImageFile.getAbsolutePath());
        loadImageProfile();
    }

    @FXML
    private void onButtonSubmitPressed() {
        User user = singleton.getSelectedUser();

        int id = user.getId();
        String username = inputUsername.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama tidak bisa kosong!");
            return;
        }

        try {
            String path = singleton.getSelectedUser().getFilepath();
            if (selectedImageFile != null) {
                path = saveSelectedImage(String.valueOf(id));
            }

            DBConfig.editUser(id, username, path);
            App.changeRoot("user_list");

            JOptionPane.showMessageDialog(null, "Berhasil mengubah data user!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "[IOException]\nGagal menyimpan gambar ke directory!");
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "[SQLException]\nGagal membuat user baru!");
        }
    }

    @FXML
    private void onButtonChangePressed() {
        App.changeRoot("form_user_change_password");
    }

    @FXML
    private void onButtonCancelPressed() {
        App.changeRoot("user_list");
    }
}
