package interfaces;

import elements.Vector2d;
import maps.AbstractWorldMap;

public interface IFieldOption {

    Vector2d spawnGrass(AbstractWorldMap map);//returns array with grass positions to spawn
}
