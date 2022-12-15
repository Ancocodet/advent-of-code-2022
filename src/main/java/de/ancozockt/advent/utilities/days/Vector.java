package de.ancozockt.advent.utilities.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @AllArgsConstructor @Setter
public class Vector implements Comparable<Vector> {

    public static Vector DOWN = new Vector(0, 1);
    public static Vector UP = new Vector(0, -1);
    public static Vector LEFT = new Vector(-1, 0);
    public static Vector RIGHT = new Vector(1, 0);

    private int x;
    private int y;

    public Vector move(Vector vector){
        this.x += vector.getX();
        this.y += vector.getY();

        return this;
    }

    public Vector sum(Vector vector){
        return new Vector(this.x + vector.getX(), this.y + vector.getY());
    }

    public int dist(Vector o) {
        return Math.abs(x - o.x) + Math.abs(y - o.y);
    }

    public Vector copy(){
        return new Vector(this.x, this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(y,x);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector other = (Vector) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int compareTo(Vector o) {
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
