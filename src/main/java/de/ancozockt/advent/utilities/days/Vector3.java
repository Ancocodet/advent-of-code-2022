package de.ancozockt.advent.utilities.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

@Getter @AllArgsConstructor @Setter
public class Vector3 implements Comparable<Vector3> {

    private int x;
    private int y;
    private int z;

    public Vector3 move(Vector3 vector){
        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();

        return this;
    }

    public Vector3 sum(Vector3 vector){
        return new Vector3(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    public int dist(Vector3 o) {
        return Math.abs(o.x - x) + Math.abs(o.y - y) + Math.abs(o.z - z);
    }

    public Vector3 copy(){
        return new Vector3(this.x, this.y, this.z);
    }

    public List<Vector3> directNeighbors(){
        ArrayList<Vector3> neighbors = new ArrayList<>();

        neighbors.add(new Vector3(x + 1, y, z));
        neighbors.add(new Vector3(x - 1, y, z));

        neighbors.add(new Vector3(x, y + 1, z));
        neighbors.add(new Vector3(x, y - 1, z));

        neighbors.add(new Vector3(x, y, z + 1));
        neighbors.add(new Vector3(x, y, z - 1));

        return neighbors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3 other = (Vector3) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    @Override
    public int compareTo(Vector3 o) {
        if(this.equals(o)) {
            return 0;
        }else if(this.getY() > o.getY()) {
            return -1;
        }else if(this.getY() < o.getY()) {
            return 1;
        }

        return o.getX() > this.getX() ? -1 : 1;
    }
}
