package maps;

import elements.Animal;
import elements.Vector2d;
import fields.AbstractField;

public class PortalMap extends AbstractWorldMap{

    public PortalMap(int width, int height, int startingGrass, int grassEnergy,
                 int numberOfGrowingGrass, AbstractField field){
        super(width,height,startingGrass,grassEnergy,numberOfGrowingGrass,field);
        this.spawnGrass(startedGrass);

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
