package interfaces;


import elements.Animal;
import elements.Vector2d;

public interface IWorldMap {

    Vector2d MoveTo(Animal animal,Vector2d position);//returns position


    boolean place(Animal animal);


    boolean isOccupied(Vector2d position);


    Object objectAt(Vector2d position);

    void remove(Animal animal);
}
