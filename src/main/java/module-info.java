module com.vharya.jurnal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.slf4j;


    opens com.vharya.jurnal to javafx.fxml;
    exports com.vharya.jurnal;
    exports com.vharya.jurnal.Controllers;
    opens com.vharya.jurnal.Controllers to javafx.fxml;
    exports com.vharya.jurnal.Models;
    opens com.vharya.jurnal.Models to javafx.fxml;
}