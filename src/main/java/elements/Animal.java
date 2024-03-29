package elements;

import enums.MapDirection;
import interfaces.IGeneChoice;
import interfaces.IMutation;
import interfaces.IPositionChangeObserver;
import maps.AbstractWorldMap;

import java.util.ArrayList;

import static enums.MapDirection.setRandomOrientation;


public class Animal extends AbstractMapElement {

    private final AbstractWorldMap map;

    private final double reproductionCost;
    private final int fullEnergy;
    private MapDirection direction;
    private int energy;
    private final IMutation mutation;
    private final Genome genome;
    private final IGeneChoice geneChoice;
    private int activatedGene;
    private int age;
    private int numberOfChildren;
    private final int bornDate;
    private int deathDate;
    private final int minNumberOfMutations;
    private final int maxNumberOfMutations;

    private int plantsEaten;
    private final int numberOfGenes;
    private  final ArrayList<IPositionChangeObserver> positionChangeObservers;

    public Animal(AbstractWorldMap map, Vector2d initialPosition,int energy,
                  Genome genome,IMutation mutation,IGeneChoice geneChoice,
                  int numberOfGenes,double reproductionCost,int fullEnergy,
                  int bornDay,int minNumberOfMutations,int maxNumberOfMutations){
        this.position = initialPosition;//random position
        this.map = map;
        this.mutation=mutation;
        this.energy=energy;
        this.activatedGene=0;
        this.age=0;
        this.numberOfChildren=0;
        this.geneChoice=geneChoice;
        this.minNumberOfMutations=minNumberOfMutations;
        this.maxNumberOfMutations=maxNumberOfMutations;
        this.genome=genome;
        this.direction=setRandomOrientation();
        this.positionChangeObservers = new ArrayList<>();
        this.numberOfGenes=numberOfGenes;
        this.reproductionCost=reproductionCost;
        this.fullEnergy=fullEnergy;
        this.bornDate=bornDay;
        this.deathDate=0;
        this.plantsEaten=0;


    }

    public void eat(Grass grass){
        this.energy+=grass.getEnergy();
        this.plantsEaten++;


    }
    public boolean isDead(){
        return this.energy<=0;
    }

    public boolean isFull(){
        return this.energy>=fullEnergy;
    }

    public void rotate(){
        int numberOfRotations=genome.getGenes()[this.activatedGene];
        for (int i = 0; i < numberOfRotations; i++) {
                this.direction=this.direction.next();
//            System.out.println(this.direction);

        }
        this.activatedGene=this.geneChoice.choose(genome.getGenes(),activatedGene);
//        System.out.println(this.activatedGene);
    }
    public void move(){
        Vector2d newPosition= this.position.add(this.direction.toUnitVector());
        if (!map.canMoveTo(newPosition)) {
            newPosition = this.map.findNewPosition(this, newPosition);
        }

        this.positionChanged(this.position,newPosition);
        this.position = newPosition;


    }

    public void addObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.add(observer);


    }
    public void removeObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.remove(observer);

    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: positionChangeObservers){
            observer.positionChanged(this,oldPosition,newPosition);
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
        Genome childGenome = new Genome(strongerParent.genome,weakerParent.genome,strongerParent.energy,
                weakerParent.energy, numberOfGenes,this.mutation,this.minNumberOfMutations,this.maxNumberOfMutations);

        Animal childAnimal = new Animal(this.map,this.position,childEnergy,childGenome,this.mutation,
                this.geneChoice,numberOfGenes,reproductionCost,fullEnergy,this.map.getDay(),this.minNumberOfMutations,this.maxNumberOfMutations);

        this.numberOfChildren++;
        otherAnimal.numberOfChildren++;
        return childAnimal;


    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }

    public void setDeathDate(int day) {
        this.deathDate = day - this.bornDate;
    }






    @Override
    public String getResource() {
        if(this.energy>fullEnergy*0.6){
            return "src/main/resources/animalstrong.png";

        }
        else if (this.energy<=fullEnergy*0.6 && this.energy>fullEnergy*0.3) {
            return "src/main/resources/animalmid.png";

        }
        else {
            return "src/main/resources/animalweak.png";

        }

    }

    public Genome getGenome() {
        return genome;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }


    public int getEnergy(){
        return this.energy;
    }

    public int getAge(){
        return this.age;
    }
    public void addAge(int age){
        this.age+=age;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public int getNumberOfChildren(){
        return this.numberOfChildren;
    }

    public int getActivatedGene(){
        return  this.activatedGene;
    }

    public double getReproductionCost() {
        return reproductionCost;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public String toString() {
//        return direction.toString();
        return "A";
    }
}
