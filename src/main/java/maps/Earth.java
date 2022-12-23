package maps;

import elements.Animal;
import elements.Vector2d;
import enums.MapDirection;
import interfaces.IFieldOption;

public class Earth extends AbstractWorldMap{


    public Earth(int width, int height, IFieldOption field){
        this.height=height;
        this.width=width;
        this.field=field;
        this.spawnGrass(startedGrass);

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(new Vector2d(0,0)) && position.precedes(new Vector2d(width,height));
    }

    @Override
    public Vector2d findNewPosition(Animal animal,Vector2d wantedPosition) {
        Vector2d newPosition=wantedPosition;
        if(wantedPosition.getX()<0){
            newPosition=new Vector2d(width,wantedPosition.getY());
        }
        else if (wantedPosition.getX()>width) {
            newPosition=new Vector2d(0,wantedPosition.getY());
        }
        else if (wantedPosition.getY()>height) {
            newPosition=new Vector2d(wantedPosition.getX(),height);
            animal.setDirection(MapDirection.SOUTH);

        }
        else if (wantedPosition.getY()<0) {
            newPosition=new Vector2d(wantedPosition.getX(),0);
            animal.setDirection(MapDirection.NORTH);

        }


        return newPosition;
    }
}
