package eberron.lightningrail.database;

import eberron.lightningrail.model.CardinalDirection;
import eberron.lightningrail.model.Location;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Database {
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/database.properties";

    private static Connection connect() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileReader(PROPERTIES_FILE_PATH));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    public static List<Location> getLocations(){
        List<Location> locations = new ArrayList<>();
        try(Connection connection = connect()){
            Statement query = connection.createStatement();
            ResultSet locationResultSet = query.executeQuery("SELECT * FROM LOCATION ORDER BY NAME");
            while(locationResultSet.next()){
                Integer id = locationResultSet.getInt("ID");
                String name = locationResultSet.getString("NAME");
                CardinalDirection direction = switch(locationResultSet.getString("CARDINAL_DIRECTION")){
                    case "EAST" -> CardinalDirection.EAST;
                    case "WEST" -> CardinalDirection.WEST;
                    default -> throw new RuntimeException("Cardinal direction does not exist.");
                };
                Integer distanceFromPreviousNode = locationResultSet.getInt("DISTANCE_FROM_ORIGIN_NODE");
                Integer nodeDistance = locationResultSet.getInt("NODE_DISTANCE");
                Location location = new Location(id, name, direction, distanceFromPreviousNode, nodeDistance);
                locations.add(location);
            }
        } catch(SQLException | IOException ex){
            ex.printStackTrace();
        }
        return locations;
    }
}
