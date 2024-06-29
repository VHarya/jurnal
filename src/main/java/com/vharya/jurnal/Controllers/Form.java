package com.vharya.jurnal.Controllers;

import com.vharya.jurnal.App;
import com.vharya.jurnal.DBConfig;
import com.vharya.jurnal.GlobalSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Form {
    @FXML
    TextField inputID;
    @FXML
    TextField inputDate;
    @FXML
    TextArea inputContent;

    @FXML
    private void onButtonSubmitPressed() {
        if (inputID.getText().isEmpty()) {
            insertData();
        }
        else {
            updateData();
        }
    }

    @FXML
    private void onButtonBackPressed() throws IOException {
        App.changeRoot("home");
    }

    private void insertData() {
        try {
            String sql = "INSERT INTO jurnals VALUES (null, ?, null, null, ?)";

            Connection connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, inputContent.getText());
            statement.setInt(2, GlobalSingleton.getInstance().getUserId());

            statement.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Menambahkan Jurnal!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void updateData() {
        try {
            String sql = "UPDATE jurnals SET content=? WHERE id=?";

            Connection connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, inputContent.getText());
            statement.setInt(2, GlobalSingleton.getInstance().getUserId());

            statement.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Mengubah Jurnal!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
