package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import com.vharya.jurnal.Models.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserList implements Initializable {
    private final GlobalSingleton singleton = GlobalSingleton.getInstance();

    @FXML
    private HBox listUser;
    @FXML
    private Button buttonCreateAccount;

    private int userCount = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        singleton.setSelectedUser(null);
        loadData();
    }

    private void loadData() {
        try {
            listUser.getChildren().clear();

            ArrayList<User> users = DBConfig.getAllUsers();
            userCount = users.size();

            for (User user : users) {
                Node node = createUserCard(user);
                listUser.getChildren().add(node);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // More / equals to 3 disable
        // Limits to 3 account :)
        buttonCreateAccount.setDisable(userCount >= 3);
    }

    private Node createUserCard(User user) {
        VBox card = new VBox();
        HBox imageContainer = new HBox();
        ImageView imageView = new ImageView();
        Label label = new Label();

        // Set image if available
        // else just use default
        if (user.getFilepath() != null) {
            try {
                InputStream inputStream = new FileInputStream(user.getFilepath());
                imageView.setImage(new Image(inputStream));
            }
            catch (FileNotFoundException e) {
                imageView.setImage(new Image(App.getResourceAsStream("assets/images/avatar_square.png")));
            }
        }
        else {
            imageView.setImage(new Image(App.getResourceAsStream("assets/images/avatar_square.png")));
        }

        label.setText(user.getName());
        label.setFont(new Font(16));

        imageContainer.setPrefWidth(150);
        imageContainer.setPrefHeight(150);
        imageContainer.getChildren().add(imageView);

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        card.setPrefWidth(100);
        card.setPadding(new Insets(20));
        card.setSpacing(20);
        card.setAlignment(Pos.CENTER);
        card.setId(String.valueOf(user.getId()));
        card.getStyleClass().add("userCard");
        card.getChildren().addAll(imageContainer, label);

        card.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Bit of a duct tape and hope solution
                // Basically loop-through listUser HBox and check if any of the "Card" id
                // matches user's id and change the style accordingly

                singleton.setSelectedUser(user);

                for (Node node : listUser.getChildren()) {
                    if (!node.getId().equals(String.valueOf(user.getId()))) {
                        node.getStyleClass().remove("userCardSelected");
                    }
                    else {
                        node.getStyleClass().add("userCardSelected");
                    }
                }
            }
        });

        return card;
    }

    @FXML
    private void onNewUserPressed() {
        if (userCount >= 3) return;

        App.changeRoot("form_user_create");
    }

    @FXML
    private void onEditUserPressed() {
        if (singleton.getSelectedUser() == null) return;

        App.changeRoot("form_user_edit");
    }

    @FXML
    private void onDeleteUserPressed() {
        User user = singleton.getSelectedUser();
        if (user == null) return;

        int confirmDelete = JOptionPane.showConfirmDialog(
                null,
                "Setelah akun dihapus, akun tidak bisa dikembalikan.",
                "Apakah anda yakin ingin menghapus akun ini?",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmDelete == JOptionPane.NO_OPTION) return;

        try {
            DBConfig.deleteUser(user.getId());
            loadData();
            JOptionPane.showMessageDialog(null, "Berhasil menghapus data!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onLoginPressed() {
        if (singleton.getSelectedUser() == null) return;

        if (singleton.getSelectedUser().isProtected()){
            App.changeRoot("user_login");
        } else {
            singleton.setUserId(singleton.getSelectedUser().getId());
            App.changeRoot("home");
        }
    }
}
