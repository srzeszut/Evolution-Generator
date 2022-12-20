package elements;

import enums.MapDirection;
import enums.MoveDirection;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import maps.AbstractWorldMap;

import java.util.ArrayList;


public class Animal extends AbstractMapElement {

    private final AbstractWorldMap map;

    private final double reproductionCost=0.2;
    private MapDirection direction;
    private int energy;
    private final int startingEnergy;
    private Genome genome;
    private final int numberOfGenes=15;
    private  ArrayList<IPositionChangeObserver> positionChangeObservers;

    public Animal(AbstractWorldMap map, Vector2d initialPosition,int energy,Genome genome){//ewentualnie 2 konstruktory
        this.position = initialPosition;
        this.map = map;
        this.energy=energy;
        this.startingEnergy=energy;
//        this.genome=new Genome(numberOfGenes);
        this.genome=genome;
        this.direction=direction.setRandomOrientation();
        this.positionChangeObservers = new ArrayList<>();


    }
//    public Animal(AbstractWorldMap map, Vector2d initialPosition,int energy){
//        this(map, initialPosition,energy, new Genome(numberOfGenes));
//
//    }
    public void eat(Grass grass){
        this.energy+=grass.getEnergy();

    }
    public boolean isDead(){
        return this.energy==0;
    }

    public void move(MoveDirection direction){
        Vector2d newPosition= position;
        switch(direction){
            case LEFT-> this.direction =this.direction.previous();
            case RIGHT -> this.direction =this.direction.next();
            case FORWARD->{
                newPosition = this.position.add(this.direction.toUnitVector());
            }
            case BACKWARD->{
                newPosition = this.position.subtract(this.direction.toUnitVector());
            }

        }
//        if(map.canMoveTo(newPosition))
//            this.positionChanged(this.position,newPosition);
//        this.position = newPosition;

    }

    protected void addObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.add(observer);


    }
    protected void removeObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.remove(observer);

    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: positionChangeObservers){
            observer.positionChanged(oldPosition,newPosition);
        }
    }

    public Animal reproduce(Animal otherAnimal){
        int childEnergy=(int)(this.energy*reproductionCost + otherAnimal.energy*reproductionCost);
        this.energy=(int)((1-reproductionCost)*this.energy);
        otherAnimal.energy=(int)((1-reproductionCost)*otherAnimal.energy);
        Animal strongerParent;
        Animal weakerParent;

        if(this.energy> otherAnimal.energy){
            strongerParent=this;
            weakerParent=otherAnimal;
        }
        else {
            strongerParent=otherAnimal;
            weakerParent=this;

        }
        Genome childGenome = new Genome(strongerParent.genome,weakerParent.genome,strongerParent.energy, weakerParent.energy, numberOfGenes);
        Animal childAnimal = new Animal(this.map,this.position,childEnergy,childGenome);

        return childAnimal;


    }




    @Override
    public String getResource() {
        return null;
    }


}
