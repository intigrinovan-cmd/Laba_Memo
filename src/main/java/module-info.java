module com.example.memo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.memo to javafx.fxml;
    exports com.example.memo;
}