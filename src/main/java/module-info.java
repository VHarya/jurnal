module com.vharya.jurnal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.vharya.jurnal to javafx.fxml;
    exports com.vharya.jurnal;
}