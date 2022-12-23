package simulation;

import interfaces.IFieldOption;
import interfaces.IGeneChoice;
import interfaces.IMutation;
import maps.AbstractWorldMap;
import maps.Earth;

public class SimulationEngine implements Runnable{


    private AbstractWorldMap map;

    public SimulationEngine(int width, int height,
                            int startingGrass, int grassEnergy, int growingGrass,
                            IFieldOption field, int numberOfAnimals, int startingEnergy,
                            int neededEnergy, int reproductionEnergy, int numberOfMutations,
                            IMutation mutation, int genomeLength, IGeneChoice geneChoice){

        this.map= new Earth(width,height,field);



    }
    @Override
    public void run() {

    }
}
