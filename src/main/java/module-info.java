module eberron.lightningrail.lightningraildistance {
    requires javafx.controls;
    requires javafx.fxml;


    opens eberron.lightningrail.lightningraildistance to javafx.fxml;
    exports eberron.lightningrail.lightningraildistance;
}