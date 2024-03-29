package elements;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;


    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }

    public boolean precedes(Vector2d other) {

        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {

        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d lowerLeft(Vector2d other) {

        int minX = Math.min(this.x, other.x);
        int minY = Math.min(this.y, other.y);
        return new Vector2d(minX, minY);
    }

    Vector2d upperRight(Vector2d other) {

        int maxX = Math.max(this.x, other.x);
        int maxY = Math.max(this.y, other.y);
        return new Vector2d(maxX, maxY);
    }

    Vector2d add(Vector2d other) {
        int tempX = this.x + other.x;
        int tempY = this.y + other.y;
        return new Vector2d(tempX, tempY);
    }

    Vector2d subtract(Vector2d other) {
        int tempX = this.x - other.x;
        int tempY = this.y - other.y;
        return new Vector2d(tempX, tempY);
    }

    Vector2d opposite() {
        int opX = -1 * this.x;
        int opY = -1 * this.y;
        return new Vector2d(opX, opY);
    }

    public boolean equals(Object other){

        if(this==other)
            return true;
        if(!(other instanceof Vector2d that))
            return false;
        return that.x == this.x && that.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}



