package enums;

import elements.Vector2d;

import java.util.Random;

public enum MapDirection {
    NORTH("N"),
    NORTHWEST("NW"),
    NORTHEAST("NE"),//new vector2d(0,1)
    SOUTH("S"),
    SOUTHEAST("SE"),
    SOUTHWEST("SW"),
    WEST("W"),
    EAST("E");
    private String direction;
    MapDirection(String direction) {

        this.direction=direction;
    }


    public String toString() {

        return direction;
    }

    public MapDirection next() {//value,ordinal
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;

        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> NORTHWEST;
            case NORTHEAST -> NORTH;
            case EAST -> NORTHEAST;
            case SOUTHEAST -> EAST;
            case SOUTH -> SOUTHEAST;
            case SOUTHWEST -> SOUTH;
            case WEST -> SOUTHWEST;
            case NORTHWEST -> WEST;
        };

    }



    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    public static MapDirection setRandomOrientation(){
        Random random= new Random();
        int orientation=random.nextInt(8);
        return switch (orientation) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalStateException();// jeszcze zobaczyc
        };


    }
}
