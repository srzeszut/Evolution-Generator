package gui;

import elements.Animal;
import elements.Grass;
import elements.Steppe;
import elements.Vector2d;
import interfaces.IMapElement;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import maps.AbstractWorldMap;
import simulation.SimulationEngine;

import java.io.FileNotFoundException;
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


    public void renderGrid(){//napisac od nowa za wolne

        grid.setGridLinesVisible(false);
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
        grid.setGridLinesVisible(true);
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

//        for (int i = 0; i <= maxX; i++) {
//            for (int j = 0; j <= maxY; j++) {
//                GuiElementBox element;
//                try{
//                        element=new GuiElementBox((new Steppe(new Vector2d(i,j))));
//                        grid.add(element.getBox(),i,j,1,1);
//                        GridPane.setHalignment(element.getBox(), HPos.CENTER);
//                    }
//                    catch (FileNotFoundException err){
//                        out.println(err.getMessage());
////                        Platform.exit();
//
//                    }
//            }

//    }
    }

    public void placeElements(){

        Vector2d[] grassesAndAnimals = getAnimalsAndGrasses(map);
        for (Vector2d position : grassesAndAnimals) {
            GuiElementBox element;
            Label guiElement = new Label(map.objectAt(position).toString());
            try{
                element=new GuiElementBox((IMapElement) map.objectAt(position),this.gridWidth,this.gridHeight);
                grid.add(element.getBox(),position.getX(), position.getY(),1,1);
                GridPane.setHalignment(element.getBox(), HPos.CENTER);
            }
            catch (FileNotFoundException err){
                out.println(err.getMessage());
//                        Platform.exit();

            }
//            grid.add(guiElement, position.getX(), position.getY(), 1, 1);
            GridPane.setHalignment(guiElement, HPos.CENTER);
        }
    }



    private Vector2d[] getAnimalsAndGrasses(AbstractWorldMap map) {//cos nie dziala tu
        Map<Vector2d, Grass> grasses = map.getGrassPositions();
        List<Animal> animals = map.getAnimals();
        Vector2d[] animalsAndGrasses = new Vector2d[grasses.size() + animals.size()];
        int index = 0;
        for (Animal animal : animals) {
            animalsAndGrasses[index] = animal.getPosition();
            index++;
        }
        for (Vector2d grassPosition : grasses.keySet()) {
            animalsAndGrasses[index] = grassPosition;
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
