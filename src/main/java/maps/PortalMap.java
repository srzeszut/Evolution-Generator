package maps;

import elements.Animal;
import elements.Vector2d;
import fields.AbstractField;

import java.util.Random;

public class PortalMap extends AbstractWorldMap{
    Random random= new Random();

    public PortalMap(int width, int height, int startingGrass, int grassEnergy,
                 int numberOfGrowingGrass, AbstractField field){
        super(width,height,startingGrass,grassEnergy,numberOfGrowingGrass,field);
        this.spawnGrass(startedGrass);

    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(new Vector2d(0,0)) && position.precedes(new Vector2d(width-1,height-1));
    }

    @Override
    public Vector2d findNewPosition(Animal animal, Vector2d wantedPosition) {
        Vector2d newPosition= new Vector2d(random.nextInt(width-1),random.nextInt(height-1));
        animal.reduceEnergy((int)(animal.getEnergy()*(1-animal.getReproductionCost())));
        return newPosition;
    }
}
