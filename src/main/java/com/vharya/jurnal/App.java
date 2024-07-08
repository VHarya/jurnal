package com.vharya.jurnal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            DBConfig.initDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        scene = new Scene(loadFXML("user_list"));

//        stage.setWidth(720);
//        stage.setHeight(490);
        stage.setTitle("Jurnal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

//        stage.setOnCloseRequest(windowEvent -> {
//            Platform.exit();
//            System.exit(0);
//        });
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void changeRoot(String root) {
        try {
            scene.setRoot(loadFXML(root));
        } catch (IOException e) {
//            System.out.println(e);
//            JOptionPane.showMessageDialog(null, "Failed to open page!\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static InputStream getResourceAsStream(String path) {
        return App.class.getResourceAsStream(path);
    }

    public static void main(String[] args) {
        launch();
    }
}


// Possible breakage when Application is moved
// Mayhaps... because the way profile image is stored :P