package interfaces;

import elements.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    boolean isAt(Vector2d position);
    String getResource();
}