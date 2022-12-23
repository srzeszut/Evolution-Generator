package interfaces;

import elements.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition);
}


