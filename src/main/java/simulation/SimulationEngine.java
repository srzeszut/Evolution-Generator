package simulation;

import elements.Animal;
import elements.Genome;
import elements.Vector2d;
import gui.App;
import gui.SimulationWindow;
import interfaces.IFieldOption;
import interfaces.IGeneChoice;
import interfaces.IMutation;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import maps.AbstractWorldMap;
import maps.Earth;

import java.util.*;

public class SimulationEngine implements Runnable {


    private AbstractWorldMap map;
    private final SimulationWindow app;
    private int delay ;
    private boolean flag=false;

    Random random = new Random();

    //genomy,mutacje
    public SimulationEngine(SimulationWindow app, AbstractWorldMap map, int numberOfAnimals, int startingEnergy,
                            double neededEnergy, int minNumberOfMutations, int maxNumberOfMutations,
                            IMutation mutation, int genomeLength, IGeneChoice geneChoice, int fullEnergy, int delay) {

        this.map = map;
        this.app = app;
        this.delay=delay;
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d newPosition = new Vector2d(random.nextInt(this.map.getWidth()), random.nextInt(this.map.getHeight()));
            Genome genome = new Genome(genomeLength, mutation);
            Animal animalToSpawn = new Animal(map, newPosition, startingEnergy, genome, mutation, geneChoice, genomeLength, neededEnergy, fullEnergy, map.getDay());
            this.map.place(animalToSpawn);
//            System.out.println(Arrays.toString(genome.getGenes()));


        }
        for (Vector2d key : map.getGrassPositions().keySet()) {
//            System.out.println(key);
        }

    }
    public void stop(){
        this.flag=true;

    }
    public AbstractWorldMap getMap(){
        return this.map;
    }

    @Override
    public void run() {
//        for (int i = 0; i < 5; i++)
        try{
            Thread.sleep(2000);
        }catch (InterruptedException err){
            System.out.println(err.getMessage());
        }
        int i=0;
        while ((this.map.getAnimals().size())>0 && !flag)
         {
//             System.out.println(this.map.toString());
             System.out.println("number of animals" + this.map.getAnimals().size());
            this.map.removeDead();
            this.map.moveAnimals();
            this.map.eatGrass();
            this.map.reproduction();
            this.map.addNewGrass();
            this.map.anotherDay();

            Platform.runLater(()->{
                this.app.renderGrid();
            });
            try{
                Thread.sleep(delay);
            }catch (InterruptedException err){
                System.out.println(err.getMessage());
            }
        }
        System.out.println(this.map.getDay());

        }
    }

