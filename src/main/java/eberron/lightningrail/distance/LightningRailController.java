package eberron.lightningrail.distance;

import eberron.lightningrail.database.Database;
import eberron.lightningrail.model.Location;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class LightningRailController {
    @FXML ComboBox<Location> startComboBox;

    @FXML ComboBox<Location> destinationComboBox;

    @FXML Label resultLabel;

    @FXML
    private void initialize(){
        /* Fetch locations from database and add them to starting point combo box */
        List<Location> locations = Database.getLocations();
        startComboBox.setItems(FXCollections.observableList(locations));

        /* Disable destination combo box until start option is selected */
        destinationComboBox.setDisable(true);
        startComboBox.valueProperty().addListener((observableValue, location, t1) -> {
            /* Fill destination dropdown with locations east or west, depending on selected item */
            Location selectedLocation = startComboBox.getValue();
            destinationComboBox.setItems(
                    FXCollections.observableList(
                            locations.stream()
                                    .filter(l -> l.getCardinal().equals(selectedLocation.getCardinal()))
                                    .filter(l -> !l.equals(selectedLocation))
                                    .collect(Collectors.toList())
                    )
            );

            /* Enable destination combo box if it is disabled */
            if(destinationComboBox.isDisable()) destinationComboBox.setDisable(false);

            calculate();
        });

        /* Calculate on destination combo box change */
        destinationComboBox.valueProperty().addListener((observableValue, location, t1) -> calculate());
    }

    /**
     * Calculates total distance (in mi), travel time, and prices between starting point and destination
     */
    private void calculate(){
        Location start = startComboBox.getValue();
        Location destination = destinationComboBox.getValue();

        if(start == null || destination == null) return;
        /* Distance */
        int distance = Math.abs(start.getDistanceFromOriginNode()-destination.getDistanceFromOriginNode());
        /* Time */
        int hours = (int) ((double) distance/30);
        int minutes = (int) Math.round(((double) distance / 30 - hours) * 60);
        hours += Math.abs(start.getNodeDistance() - destination.getNodeDistance()) - 1;
        /* Prices Steerage */
        int priceSteerageGP = distance * 3 / 100;
        int priceSteerageSP = distance * 3 % 100 / 10;
        int priceSteerageCP = distance * 3 % 10;
        /* Prices Standard */
        int priceStandardGP = distance * 20 / 100;
        int priceStandardSP = distance * 20 % 100 / 10;
        int priceStandardCP = distance * 20 % 10;
        /* Prices 1st Class */
        int priceFirstGP = distance * 50 / 100;
        int priceFirstSP = distance * 50 % 100 / 10;
        int priceFirstCP = distance * 50 % 10;

        String result = String.format("""
                Total distance (mi): %d
                Total travel time: %dh, %dmin
                Steerage: %d gp, %d sp, %d cp
                Standard: %d gp, %d sp, %d cp
                1st class: %d gp, %d sp, %d cp
                """,
                distance,
                hours, minutes,
                priceSteerageGP, priceSteerageSP, priceSteerageCP,
                priceStandardGP, priceStandardSP, priceStandardCP,
                priceFirstGP, priceFirstSP, priceFirstCP
        );

        resultLabel.setText(result);
    }
}