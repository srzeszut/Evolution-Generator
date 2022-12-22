package maps;

import elements.AbstractMapElement;
import elements.Animal;
import elements.Vector2d;
import interfaces.IFieldOption;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected int height;
    protected int width;
    protected IFieldOption field;
    protected HashMap<Vector2d, AbstractMapElement> mapElements = new HashMap<Vector2d, AbstractMapElement>();

//    abstract Vector2d MoveTo(Animal animal ,Vector2d position);


    @Override
    public boolean place(Animal animal) {
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
    public
}
