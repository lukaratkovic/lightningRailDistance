module eberron.lightningrail.lightningraildistance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens eberron.lightningrail.distance to javafx.fxml;
    exports eberron.lightningrail.distance;
}