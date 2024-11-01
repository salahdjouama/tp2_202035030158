module salah.tp3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens salah.tp3 to javafx.fxml;
    exports salah.tp3;
}