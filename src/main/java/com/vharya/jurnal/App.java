package com.vharya.jurnal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 720, 480);

        stage.setTitle("Jurnal");
        stage.setScene(scene);
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

    public static void changeRoot(String root) throws IOException {
        scene.setRoot(loadFXML(root));
    }

    public static void main(String[] args) {
        launch();
    }
}