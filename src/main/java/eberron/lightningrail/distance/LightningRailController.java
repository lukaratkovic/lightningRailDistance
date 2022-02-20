package eberron.lightningrail.distance;

import eberron.lightningrail.database.Database;
import eberron.lightningrail.model.Location;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class LightningRailController {
    @FXML ComboBox<Location> startComboBox;

    @FXML ComboBox<Location> destinationComboBox;

    @FXML
    private void initialize(){
        /* Fetch locations from database and add them to starting point combo box */
        List<Location> locations = Database.getLocations();
        startComboBox.setItems(FXCollections.observableList(locations));

        /* Disable destination combo box until start option is selected */
        destinationComboBox.setDisable(true);
        startComboBox.valueProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location t1) {
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
            }
        });

        /* Calculate on destination combo box change */
        destinationComboBox.valueProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location t1) {
                calculate();
            }
        });
    }

    /**
     * Calculates total distance (in mi), travel time, and prices between starting point and destination
     */
    private void calculate(){
        Location start = startComboBox.getValue();
        Location destination = destinationComboBox.getValue();

        if(start == null || destination == null) return;
        Integer distance = Math.abs(start.getDistanceFromOriginNode()-destination.getDistanceFromOriginNode());
        int hours = (int) ((double) distance/30);
        int minutes = (int) Math.round(((double) distance / 30 - hours) * 60);
        hours += Math.abs(start.getNodeDistance() - destination.getNodeDistance()) - 1;
        System.out.printf("%dh, %dmin%n",hours, minutes);
    }
}