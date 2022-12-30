package main;

import fields.AbstractField;
import fields.ForestedEquator;
import geneBehaviors.Madness;
import geneBehaviors.predestination;
import gui.App;
import interfaces.IGeneChoice;
import interfaces.IMutation;
import javafx.application.Application;
import maps.AbstractWorldMap;
import maps.Earth;
import mutations.LightMutation;
import mutations.RandomMutation;
import simulation.SimulationEngine;

import static java.lang.System.out;

public class World {

    public static void main(String[] args) {
        out.println("system wystartował");
        Application.launch(App.class,args);
//        AbstractField field=new ForestedEquator();
//        AbstractWorldMap map=new Earth(50,50,150,15,20,field);
//        IMutation mutation= new RandomMutation();
//        IGeneChoice choice=new Madness();
//        SimulationEngine engine =
//                new SimulationEngine(map,100,100,
//                        0.2,0,3,mutation,30,choice,100);//zrobic minmax mutacje
////        Thread engineThread = new Thread(engine);
//        engineThread.start();
//        engine.run();
        out.println("system zakończył działanie");

    }
}
