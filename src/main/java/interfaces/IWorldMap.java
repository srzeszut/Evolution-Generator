package interfaces;


import elements.Animal;
import elements.Vector2d;

public interface IWorldMap {

    boolean canMoveTo(Vector2d position);

    Vector2d findNewPosition(Animal animal,Vector2d wantedPosition);//znajduje nową pozycje dla animala jeśli canmoveto zwróci fałsz

    void place(Animal animal);


   default boolean isOccupied(Vector2d position){
       return objectAt(position) != null;
    }


    Object objectAt(Vector2d position);

    void removeDead();
    void addNewGrass();
    void reproduction();
    void eatGrass();
    void moveAnimals();
}
