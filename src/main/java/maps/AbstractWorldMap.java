package maps;

import elements.AbstractMapElement;
import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IFieldOption;
import interfaces.IMapElement;
import interfaces.IPositionChangeObserver;
import interfaces.IWorldMap;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected int height;

    protected int width;

    private int maxNumberOfGrass=height*width;
    private int maxFavouredNumberOfGrass= (int)(0.2*maxNumberOfGrass);
    private int maxDisfavouredNumberOfGrass= maxNumberOfGrass-maxFavouredNumberOfGrass;

    protected int startedGrass;
    private int energy;
    protected int dailyGrass;
    protected IFieldOption field;
    protected HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected HashMap<Vector2d, Grass> grassPositions= new HashMap<Vector2d, Grass>();
    private SortedSet<Animal> animalSet= new TreeSet<>(Comparator.comparingInt(Animal::getEnergy).thenComparing(Animal::getAge)
            .thenComparing(Animal::getNumberOfChildren).thenComparing(Animal::getActivatedGene));


    private void addToMap(Animal animal,Vector2d position){
        ArrayList<Animal> animalsOnPositions = this.animals.get(position);
        if (animalsOnPositions == null) {
            ArrayList<Animal> newList = new ArrayList<>();
            newList.add(animal);
            this.animals.put(position, newList);
        } else {
            animalsOnPositions.add(animal);
        }


    }
    private void removeFromMap(Animal animal,Vector2d position){
        ArrayList<Animal> animalsOnPositions = this.animals.get(position);
        if (animalsOnPositions != null) {
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
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Couldn't place animal on position: " +animal.getPosition()+".");
    }
    protected void spawnGrass(int numberOfGrass){//opcja jak juz sie nie mieszcza

        Random random = new Random();
        Vector2d[] favoured=this.field.favouredPositions(this);
        Vector2d[] disFavoured=this.field.disfavouredPositions(this);

        for(int i=0;i<numberOfGrass;i++){
            int chances = random.nextInt(101);
            if (chances <= 80 && favoured.length>i) {
                Vector2d grassPosition=favoured[i];
                Grass clumpOfGrass = new Grass(grassPosition,energy);
                this.grassPositions.put(grassPosition,clumpOfGrass);
            }
            else if(disFavoured.length>i){
                Vector2d grassPosition=disFavoured[i];
                Grass clumpOfGrass = new Grass(grassPosition,energy);
                this.grassPositions.put(grassPosition,clumpOfGrass);

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

    }

    @Override
    public void addNewGrass() {
        this.spawnGrass(dailyGrass);

    }

    @Override
    public void reproduction() {

    }

    @Override
    public void positionChanged(IMapElement element,Vector2d oldPosition, Vector2d newPosition) {
        removeFromMap((Animal) element,oldPosition);
        addToMap((Animal) element,newPosition);

    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public Map<Vector2d, Grass> getGrassPositions() {
        return Collections.unmodifiableMap(grassPositions);
    }


}
