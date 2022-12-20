package maps;

import elements.Animal;
import elements.Vector2d;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected int height;
    protected int width;

//    abstract Vector2d MoveTo(Animal animal ,Vector2d position);


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }

    @Override
    public void remove(Animal animal) {

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
