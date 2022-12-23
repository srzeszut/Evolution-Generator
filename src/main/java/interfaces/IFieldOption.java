package interfaces;

import elements.Vector2d;
import maps.AbstractWorldMap;

public interface IFieldOption {

    Vector2d[] favouredPositions(AbstractWorldMap map);//returns array with grass positions to spawn
    Vector2d[] disfavouredPositions(AbstractWorldMap map);//returns array with grass positions to spawn
}
