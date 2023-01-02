package maps;

import elements.Animal;
import elements.Genome;
import elements.Grass;
import elements.Vector2d;
import fields.AbstractField;
import fields.ToxicBodies;
import interfaces.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected int height;
    protected int width;
    private int day;
    int grassEnergy;
    AbstractField field;
    protected int startedGrass;
    protected int dailyGrass;
    private ArrayList<Animal> deadAnimals=new ArrayList<>();

    private HashMap<Vector2d, Integer> deadPositions= new HashMap<Vector2d, Integer>();
    protected HashMap<Vector2d, Grass> grassPositions= new HashMap<Vector2d, Grass>();


    public AbstractWorldMap(int width, int height, int startingGrass, int grassEnergy,
                            int numberOfGrowingGrass, AbstractField field ){
        this.height=height;
        this.width=width;
        this.day=0;
        this.startedGrass=startingGrass;
        this.grassEnergy=grassEnergy;
        this.dailyGrass=numberOfGrowingGrass;
        this.field=field;
        this.field.setMap(this);
        this.field.generatePositions();
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                deadPositions.put(new Vector2d(i,j),0);
            }
        }

    }

    private ArrayList<Animal> animalsList= new ArrayList<>();//zmienic na liste
    protected Map<Vector2d, ArrayList<Animal>> animals = new ConcurrentHashMap<>();




    private void addToMap(Animal animal,Vector2d position){
        ArrayList<Animal> animalsOnPositions = this.animals.get(position);

        if (animalsOnPositions == null) {
            ArrayList<Animal> newList= new ArrayList<Animal>();
            newList.add(animal);

            this.animals.put(position, newList);
        } else {
            animalsOnPositions.add(animal);
            Collections.sort(animalsOnPositions,Comparator.comparingInt(Animal::getEnergy)
                    .thenComparing(Animal::getAge).thenComparing(Animal::getNumberOfChildren).thenComparing(Animal::getActivatedGene));

        }


    }
    private void removeFromMap(Animal animal,Vector2d position){
        ArrayList<Animal> animalsOnPositions = this.animals.get(position);

        if (animalsOnPositions != null) {
//            System.out.println("Usuwam");

             animalsOnPositions.remove(animal);

            if (animalsOnPositions.size() == 0)
                this.animals.remove(position);


        } else
           throw new IllegalArgumentException(position + " is invalid.");
    }



    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition()))
        {
            addToMap(animal,animal.getPosition());
            animalsList.add(animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Couldn't place animal on position: " +animal.getPosition()+".");
    }

    protected void spawnGrass(int numberOfGrass){//opcja jak juz sie nie mieszcza
//        System.out.println("here");
        if(this.field instanceof ToxicBodies){
            this.field.generatePositions();
        }
        for(int i=0;i<numberOfGrass;i++){
            Vector2d newPosition=this.field.spawnGrass();
            if(newPosition!=null){
                Grass clumpOfGrass = new Grass(newPosition,this.grassEnergy);

                grassPositions.put(newPosition,clumpOfGrass);
            }


        }


    }

    @Override
    public Object objectAt(Vector2d position) {
        ArrayList<Animal> positions=animals.get(position);
        if (positions == null || positions.size() == 0) {

            return this.grassPositions.get(position);
        } else
            return positions.get(0);
    }

    @Override
    public void removeDead() {
        ArrayList<Animal> toRemove = new ArrayList<>();
        for(Animal animal:animalsList){
            if(animal.isDead()){
                deadAnimals.add(animal);
                deadPositions.put(animal.getPosition(),deadPositions.get(animal.getPosition())+1);
                animal.setDeathDate(this.day);
                toRemove.add(animal);
                removeFromMap(animal, animal.getPosition());
                animal.removeObserver(this);

            }

        }
        for(Animal animal:toRemove){
            animalsList.remove(animal);
        }


    }
    @Override
    public void moveAnimals() {
        for(Animal animal:animalsList){
//            System.out.println(animal.getEnergy());
            animal.rotate();
            animal.move();
//            System.out.println(animal.getPosition());
        }

    }

    @Override
    public void addNewGrass() {
        this.spawnGrass(dailyGrass);

    }

    @Override
    public void reproduction() {
        for(ArrayList<Animal> animals: this.animals.values()){
            if (animals != null && animals.size() >= 2){
                Animal parent1=animals.get(animals.size()-1);
                Animal parent2=animals.get(animals.size()-2);
                if(parent2.isFull() && parent1.isFull()){
                    Animal child= parent1.reproduce(parent2);
//                    System.out.println("reproduciton");
                    this.place(child);

                }
            }
        }

    }
    @Override
    public void eatGrass() {
        List<Vector2d> grassesToRemove = new ArrayList<>();
        for (Vector2d clumpPosition : this.grassPositions.keySet()) {
            ArrayList<Animal> animalsOnPositions = this.animals.get(clumpPosition);
            if (animalsOnPositions != null && animalsOnPositions.size() > 0) {
                animalsOnPositions.get(animalsOnPositions.size()-1).eat(grassPositions.get(clumpPosition));
                grassesToRemove.add(clumpPosition);
            }
        }
        for (Vector2d clumpPosition : grassesToRemove) {
            this.grassPositions.remove(clumpPosition);
//            System.out.println(clump.getPosition());
        }


    }

    @Override
    public void positionChanged(IMapElement element,Vector2d oldPosition, Vector2d newPosition) {
        removeFromMap((Animal) element,oldPosition);
        addToMap((Animal) element,newPosition);

    }

    public void anotherDay(){
        this.day++;
        for(Animal animal:animalsList){
            animal.reduceEnergy(1);
            animal.addAge(1);
        }
    }

    public int getDay(){
        return this.day;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public int getGrassEnergy(){
        return this.grassEnergy;
    }

    public HashMap<Vector2d, Integer> getDeadPositions() {
        return deadPositions;
    }

    public ArrayList<Animal> getAnimals() {

        return (ArrayList)animalsList.clone();
    }

    public ArrayList<Vector2d> getGrassPositions() {
        ArrayList<Vector2d> positions=new ArrayList<>();
        positions.addAll(grassPositions.keySet());

        return (ArrayList)positions.clone();


    }
    public ArrayList<Grass> getGrass() {
        ArrayList<Grass> positions=new ArrayList<>();
        for(Grass grass:grassPositions.values()){
            positions.add(grass);

        }
        return (ArrayList)positions.clone();


    }


    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0),new Vector2d(this.width,this.height));
    }

    public int getNumberOfAnimals(){
        return this.animalsList.size();
    }

    public int getNumberOfGrass(){
        return this.grassPositions.size();
    }
    public int getFreePositions(){

        return width*height-getNumberOfGrass();//chyba do poprawy
    }

    public double getAverageEnergy(){
        int sumOfEnergy=animalsList.stream().reduce(0,(acc,animal2) -> acc+animal2.getEnergy(),Integer::sum);
        return sumOfEnergy/getNumberOfAnimals();
    }


    public double getAverageAge(){
        int sumOfAge=deadAnimals.stream().reduce(0,(acc,animal2) -> acc+animal2.getAge(),Integer::sum);
        if(deadAnimals.size()>0){
            return sumOfAge/deadAnimals.size();

        }
        else {
            return 0;
        }

    }
    public Genome getDominantGenome(){
        Genome DominantGenotype=null;
        HashMap<Genome, Integer> genotypeCounter = new HashMap<>();
        int max=0;
        int currCounter;
        for (Animal animal : this.animalsList) {
            Genome animalGenotype = animal.getGenome();
            if (genotypeCounter.containsKey(animalGenotype)) {
                currCounter=genotypeCounter.remove(animalGenotype) + 1;
                genotypeCounter.put(animalGenotype, currCounter);
                if(currCounter>max){
                    max=currCounter;
                    DominantGenotype=animalGenotype;
                }
            }
            else {
                genotypeCounter.put(animalGenotype,1);
                if (DominantGenotype == null) {
                    DominantGenotype = animalGenotype;
                    max=1;
                }


            }
        }
//        System.out.println(max);
//        for(Genome g :genotypeCounter.keySet()){
//            System.out.println(g+" "+genotypeCounter.get(g));
//        }

        return DominantGenotype;


    }


}
