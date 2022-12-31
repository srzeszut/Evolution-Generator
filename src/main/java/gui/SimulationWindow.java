package gui;

import elements.Animal;
import elements.Grass;
import elements.Steppe;
import elements.Vector2d;
import interfaces.IMapElement;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import maps.AbstractWorldMap;
import simulation.SimulationEngine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

public class SimulationWindow {
    private AbstractWorldMap map;
    private GridPane grid = new GridPane();
    private int gridHeight= 20;
    private int gridWidth= 20;
    private final int simulationWidth=950;
    private SimulationEngine engine;
    public SimulationWindow(){
        this.grid.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    public void renderGrid(){//napisac od nowa za wolne

//        grid.setGridLinesVisible(false);
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
//        grid.setGridLinesVisible(true);
//        daysCounter=Integer.toString(map.getDay());
//        daysContainer=new HBox(new Label(this.daysCounter));
//        GridPane newGrid=createGrid(map);
        createGrid();
        placeElements();



    }

    public void createGrid(){
        int maxX = map.getWidth();
        int maxY = map.getHeight();
        this.gridWidth=this.simulationWidth/maxX;
        this.gridHeight=this.simulationWidth/maxY;

        for (int i = 0; i < maxX; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(this.gridWidth));
        }
        for (int i = 0; i < maxY; i++) {
            grid.getRowConstraints().add(new RowConstraints(this.gridHeight));
        }
    }

    public void placeElements(){

        Vector2d[] grassesAndAnimals = getAnimalsAndGrasses();

        for (Vector2d position : grassesAndAnimals) {
            GuiElementBox element;
//            if(map.objectAt(position)!=null) {
//                Label guiElement = new Label(map.objectAt(position).toString());
//                grid.add(guiElement, position.getX(), position.getY(), 1, 1);
//                GridPane.setHalignment(guiElement, HPos.CENTER);
//            }
            try{
                if(map.objectAt(position)!=null){
                element=new GuiElementBox((IMapElement) map.objectAt(position),this.gridWidth,this.gridHeight);
                grid.add(element.getBox(),position.getX(), position.getY(),1,1);
                GridPane.setHalignment(element.getBox(), HPos.CENTER);
                }
            }
            catch (FileNotFoundException err){
                out.println(err.getMessage());
//                        Platform.exit();

            }

            ;
        }
    }



//    private ArrayList<Vector2d> getAnimalsAndGrasses() {//cos nie dziala tu
//        ArrayList<Vector2d> grasses = map.getGrassPositions();
//
//        List<Animal> animals = map.getAnimals();
////        for(Animal position:animals){
////            System.out.println(position+""+position.getPosition() +" "+map.objectAt(position.getPosition()));
////        }
////        System.out.println("-----------------------------------------");
//        ArrayList<Vector2d> animalsAndGrasses = new ArrayList<Vector2d>();
//        for (Animal animal : animals) {
//            animalsAndGrasses.add(animal.getPosition());
//        }
//        for (Vector2d grassPosition : grasses) {
//            animalsAndGrasses.add(grassPosition);
//        }
//
//        return  animalsAndGrasses;
//    }


    private Vector2d[] getAnimalsAndGrasses() {
        List<Grass> grasses = map.getGrass();
        List<Animal> animals = map.getAnimals();
        Vector2d[] animalsAndGrasses = new Vector2d[grasses.size() + animals.size()];
        int index = 0;
        for (Animal animal : animals) {
            animalsAndGrasses[index] = animal.getPosition();
            index++;
        }
        for (Grass grass : grasses) {
            animalsAndGrasses[index] = grass.getPosition();
            index++;
        }
        return animalsAndGrasses;
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setMap(AbstractWorldMap map) {
        this.map = map;
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
    }

    public SimulationEngine getEngine() {
        return engine;
    }
}
