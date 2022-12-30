package maps;

import elements.Animal;
import elements.Vector2d;
import enums.MapDirection;
import fields.AbstractField;
import interfaces.IFieldOption;

public class Earth extends AbstractWorldMap{


    public Earth(int width, int height, int startingGrass, int grassEnergy,
                 int numberOfGrowingGrass, AbstractField field){
        super(width,height,startingGrass,grassEnergy,numberOfGrowingGrass,field);
        this.spawnGrass(startedGrass);

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(new Vector2d(0,0)) && position.precedes(new Vector2d(width-1,height-1));
    }

    @Override//wychodzi poza mape w rogach
    public Vector2d findNewPosition(Animal animal,Vector2d wantedPosition) {
        Vector2d newPosition=wantedPosition;

        if(wantedPosition.getX()<0 && wantedPosition.getY()<0){
            newPosition=new Vector2d(width-1,0);
            animal.setDirection(MapDirection.NORTH);
        }
        else if(wantedPosition.getX()<0 && wantedPosition.getY()>height-1){
            newPosition=new Vector2d(width-1,height-1);
            animal.setDirection(MapDirection.SOUTH);
        }
        else if(wantedPosition.getX()>width-1 && wantedPosition.getY()<0){
            newPosition=new Vector2d(0,0);
            animal.setDirection(MapDirection.NORTH);
        }
        else if(wantedPosition.getX()>width-1 && wantedPosition.getY()>height-1){
            newPosition=new Vector2d(0,height-1);
            animal.setDirection(MapDirection.SOUTH);
        }

        else if(wantedPosition.getX()<0){
            newPosition=new Vector2d(width-1,wantedPosition.getY());
        }
        else if (wantedPosition.getX()>width-1) {
            newPosition=new Vector2d(0,wantedPosition.getY());
        }
        else if (wantedPosition.getY()>height-1) {
            newPosition=new Vector2d(wantedPosition.getX(),height-1);
            animal.setDirection(MapDirection.SOUTH);

        }
        else if (wantedPosition.getY()<0) {
            newPosition=new Vector2d(wantedPosition.getX(),0);
            animal.setDirection(MapDirection.NORTH);

        }


        return newPosition;
    }
}
