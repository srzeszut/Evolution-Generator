package maps;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import fields.AbstractField;
import interfaces.*;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected int height;
    protected int width;
    private int day;
    int grassEnergy;
    AbstractField field;
    protected int startedGrass;
    protected int dailyGrass;
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

    }

    private ArrayList<Animal> animalsList= new ArrayList<>();//zmienic na liste
    protected HashMap<Vector2d, SortedSet<Animal>> animals = new HashMap<>();





    private void addToMap(Animal animal,Vector2d position){
        SortedSet<Animal> animalsOnPositions = this.animals.get(position);

        if (animalsOnPositions == null) {
            SortedSet<Animal> newList= new TreeSet<>(Comparator.comparingInt(Animal::getEnergy).thenComparing(Animal::getAge)
                    .thenComparing(Animal::getNumberOfChildren).thenComparing(Animal::getActivatedGene));
            newList.add(animal);
            this.animals.put(position, newList);
        } else {
            animalsOnPositions.add(animal);
        }


    }
    private void removeFromMap(Animal animal,Vector2d position){
        SortedSet<Animal> animalsOnPositions = this.animals.get(position);
        if (animalsOnPositions != null) {
             animalsOnPositions.remove(animal);

            if (animalsOnPositions.size() == 0)
                this.animals.remove(position);


        } //else
//            throw new IllegalArgumentException(position + " is invalid.");
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
        this.field.spawnGrass(numberOfGrass);

    }
    public void placeGrass(Vector2d position) {
//        System.out.println("herep");
        Grass clumpOfGrass = new Grass(position,this.grassEnergy);
        grassPositions.put(position,clumpOfGrass);
    }

    @Override
    public Object objectAt(Vector2d position) {
        SortedSet<Animal> positions=animals.get(position);
        if (positions == null || positions.size() == 0) {
            return this.grassPositions.get(position);
        } else
            return positions.last();
    }

    @Override
    public void removeDead() {
        ArrayList<Animal> toRemove = new ArrayList<>();
        for(Animal animal:animalsList){
            if(animal.isDead()){
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

        for(SortedSet<Animal> animals: this.animals.values()){
            if (animals != null && animals.size() >= 2){
                Animal parent1=animals.last();
                animals.remove(parent1);
                Animal parent2=animals.last();
                if(parent2.isFull() && parent1.isFull()){
                    Animal child= parent1.reproduce(parent2);
                    animals.add(parent1);//iterator
                    this.place(child);

                }
                else {
                    animals.add(parent1);
                    animals.add(parent2);

                }
            }
        }

    }
    @Override
    public void eatGrass() {
        List<Grass> grassesToRemove = new LinkedList<>();
        for (Grass clump : this.grassPositions.values()) {
            SortedSet<Animal> animalsOnPositions = this.animals.get(clump.getPosition());
            if (animalsOnPositions != null && animalsOnPositions.size() > 0) {
                animalsOnPositions.last().eat(clump);
                grassesToRemove.add(clump);
            }
        }
        for (Grass clump : grassesToRemove) {
            this.grassPositions.remove(clump.getPosition());
            System.out.println(clump.getPosition());
            this.field.eatGrass(clump.getPosition());
//            this.freeGrassPositions.add(tuft.getPosition());
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

    public ArrayList<Animal> getAnimals() {
        return animalsList;
    }

    public Map<Vector2d, Grass> getGrassPositions() {
        return Collections.unmodifiableMap(grassPositions);
    }

    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0),new Vector2d(this.width,this.height));
    }


}
