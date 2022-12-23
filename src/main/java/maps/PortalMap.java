package maps;

import elements.Animal;
import elements.Vector2d;

public class PortalMap extends AbstractWorldMap{

    public PortalMap(int height, int width){
        this.height=height;
        this.width=width;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public Vector2d findNewPosition(Animal animal, Vector2d wantedPosition) {
        return null;
    }
}
