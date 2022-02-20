package eberron.lightningrail.model;

import java.util.Objects;

public record Location(Integer id, String name,
                       CardinalDirection cardinal, Integer distanceFromOriginNode,
                       Integer nodeDistance) {

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return id.equals(location.id) && Objects.equals(name, location.name) && cardinal == location.cardinal && distanceFromOriginNode.equals(location.distanceFromOriginNode) && nodeDistance.equals(location.nodeDistance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cardinal, distanceFromOriginNode, nodeDistance);
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
