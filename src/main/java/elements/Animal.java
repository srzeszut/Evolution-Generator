package elements;

import enums.MapDirection;
import enums.MoveDirection;
import interfaces.IGeneChoice;
import interfaces.IMapElement;
import interfaces.IMutation;
import interfaces.IPositionChangeObserver;
import maps.AbstractWorldMap;

import java.util.ArrayList;


public class Animal extends AbstractMapElement {

    private final AbstractWorldMap map;

    private final double reproductionCost=0.2;
    private MapDirection direction;
    private int energy;
    private final int startingEnergy;
    private IMutation mutation;
    private Genome genome;
    private IGeneChoice geneChoice;
    private int activatedGene;
    private int numberOfGenes=15;
    private  ArrayList<IPositionChangeObserver> positionChangeObservers;

    public Animal(AbstractWorldMap map, Vector2d initialPosition,int energy,Genome genome,IMutation mutation,IGeneChoice geneChoice){//ewentualnie 2 konstruktory
        this.position = initialPosition;
        this.map = map;
        this.mutation=mutation;
        this.energy=energy;
        this.startingEnergy=energy;
        this.activatedGene=0;
        this.geneChoice=geneChoice;
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

    public void rotate(){
        int numberOfRotations=genome.getGenes()[activatedGene];
        for (int i = 0; i < numberOfRotations; i++) {
                this.direction.next();
        }
        this.activatedGene=this.geneChoice.choose(genome.getGenes(),activatedGene);
    }
    public void move(){
        Vector2d newPosition= this.position.add(this.direction.toUnitVector());

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

    public void reproduce(Animal otherAnimal){
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
        Genome childGenome = new Genome(strongerParent.genome,weakerParent.genome,strongerParent.energy, weakerParent.energy, numberOfGenes,this.mutation);
        Animal childAnimal = new Animal(this.map,this.position,childEnergy,childGenome,this.mutation,this.geneChoice);
        this.map.place(childAnimal);
//        return childAnimal;


    }




    @Override
    public String getResource() {
        return null;
    }


}
