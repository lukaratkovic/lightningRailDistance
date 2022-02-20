package eberron.lightningrail.model;

import java.util.Objects;

public class Location {
    private Integer id;
    private String name;
    private CardinalDirection cardinal;
    private Integer distanceFromOriginNode;
    private Integer nodeDistance;

    public Location(Integer id, String name, CardinalDirection cardinal, Integer distanceFromOriginNode, Integer nodeDistance) {
        this.id = id;
        this.name = name;
        this.cardinal = cardinal;
        this.distanceFromOriginNode = distanceFromOriginNode;
        this.nodeDistance = nodeDistance;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return id.equals(location.id) && Objects.equals(name, location.name) && cardinal == location.cardinal && distanceFromOriginNode.equals(location.distanceFromOriginNode) && nodeDistance.equals(location.nodeDistance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cardinal, distanceFromOriginNode, nodeDistance);
    }

    public String getName() {
        return name;
    }

    public CardinalDirection getCardinal() {
        return cardinal;
    }

    public Integer getDistanceFromOriginNode() {
        return distanceFromOriginNode;
    }

    public Integer getNodeDistance() {
        return nodeDistance;
    }
}
