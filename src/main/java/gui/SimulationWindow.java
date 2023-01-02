package gui;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapElement;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import maps.AbstractWorldMap;
import simulation.SimulationEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static java.lang.System.out;

public class SimulationWindow {
    private Thread engineThread;
    private AbstractWorldMap map;
    private GridPane grid = new GridPane();
    private Scene scene;
    private int gridHeight= 20;
    private int gridWidth= 20;
    private final int simulationWidth=950;
    private SimulationEngine engine;
    private Image grassImage;
    private  VBox stastisticsBox;
    private Animal trackedAnimal;
    private boolean stopped;

    private  VBox animalBox;
    private HBox buttonsBox;
    public SimulationWindow(){

        this.grid.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.buttonsBox=new HBox(buttons());
        this.animalBox=new VBox();
        this.animalBox.setPrefWidth(400);
        stastisticsBox= new VBox();
        stastisticsBox.setPrefWidth(400);
        this.scene=this.createScene();
        stopped=false;
        try{
             grassImage = new Image(new FileInputStream(new Grass(new Vector2d(0,0),0).getResource()));
        }
        catch (FileNotFoundException err){
            out.println(err.getMessage());
        }
    }

    private Scene createScene(){

        HBox container =new HBox(10,new VBox(10,buttonsBox,stastisticsBox), grid,animalBox);
        Scene simulationScene = new Scene(container, 900, 768);
        return simulationScene;

    }
    public void createStats(){

        Text days=new Text("Days: ");
        Label daysCounter=new Label(Integer.toString(this.map.getDay()));
        HBox daysBox=createStatsElement(days,daysCounter);

        Text animals=new Text("Number of animals: ");
        Label animalsCounter=new Label(Integer.toString(this.map.getNumberOfAnimals()));
        HBox animalsBox=createStatsElement(animals,animalsCounter);

        Text plants=new Text("Number of Plants: ");
        Label plantsCounter=new Label(Integer.toString(this.map.getNumberOfGrass()));
        HBox plantsBox=createStatsElement(plants,plantsCounter);

        Text free=new Text("Free places: ");
        Label freeCounter=new Label(Integer.toString(this.map.getFreePositions()));
        HBox freeBox=createStatsElement(free,freeCounter);

        Text genome=new Text("Dominant genotype: ");
        Label genomeCounter=new Label(this.map.getDominantGenome().toString());
        HBox genomeBox=createStatsElement(genome,genomeCounter);

        Text energy=new Text("Average energy: ");
        Label energyCounter=new Label(Double.toString(this.map.getAverageEnergy()));
        HBox energyBox=createStatsElement(energy,energyCounter);

        Text age=new Text("Average dead age: ");
        Label ageCounter=new Label(Double.toString(this.map.getAverageAge()));
        HBox ageBox=createStatsElement(age,ageCounter);

        VBox newStastisticsBox= new VBox(10,daysBox,animalsBox,plantsBox,genomeBox,energyBox,ageBox,freeBox);
        stastisticsBox.getChildren().add(newStastisticsBox);

    }

    public void renderStats(){

        this.stastisticsBox.getChildren().clear();
        this.createStats();



    }
    private HBox createStatsElement(Text text, Label label){
        text.setFont(Font.font("Monserrat", FontWeight.NORMAL, 15));
        label.setFont(Font.font("Monserrat", FontWeight.BOLD, 15));
        return new HBox(10,text,label);


    }

    private void createAnimalStats(Animal animal){

        Text genome=new Text("Genome: ");
        Label genomeCounter=new Label(animal.getGenome().toString());
        HBox genomeBox=createStatsElement(genome,genomeCounter);

        Text activated=new Text("Activated gene: ");
        Label activatedCounter=new Label(Integer.toString(animal.getActivatedGene()));
        HBox activatedBox=createStatsElement(activated,activatedCounter);

        Text energy=new Text("Energy: ");
        Label energyCounter=new Label(Double.toString(animal.getEnergy()));
        HBox energyBox=createStatsElement(energy,energyCounter);

        Text plants=new Text("Plants eaten: ");
        Label plantsCounter=new Label(Integer.toString(animal.getPlantsEaten()));
        HBox plantsBox=createStatsElement(plants,plantsCounter);

        Text children=new Text("Number of children: ");
        Label childrenCounter=new Label(Integer.toString(animal.getNumberOfChildren()));
        HBox childrenBox=createStatsElement(children,childrenCounter);

        Text age=new Text("Age: ");
        Label ageCounter=new Label(Integer.toString(animal.getAge()));
        HBox ageBox=createStatsElement(age,ageCounter);

        Text death=new Text("Death date: ");
        Label deathCounter=new Label(Double.toString(animal.getDeathDate()));
        HBox deathBox=createStatsElement(death,deathCounter);

        VBox newStastisticsBox= new VBox(10,new Label("ANIMAL STATS"),genomeBox,activatedBox,energyBox,plantsBox,childrenBox,ageBox,deathBox);
        animalBox.getChildren().add(newStastisticsBox);




    }


    public void renderGrid(){

        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
        createGrid();
        placeElements();
//        System.out.println(this.stopped);



    }


    private void createGrid(){
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

    private void placeElements(){

        Vector2d[] grassesAndAnimals = getAnimalsAndGrasses();


        for (Vector2d position : grassesAndAnimals) {
            GuiElementBox element;
//            if(map.objectAt(position)!=null) {
//                Label guiElement = new Label(map.objectAt(position).toString());
//                grid.add(guiElement, position.getX(), position.getY(), 1, 1);
//                GridPane.setHalignment(guiElement, HPos.CENTER);
//            }
            try{
                if(map.objectAt(position)!=null && map.objectAt(position) instanceof Animal){
                element=new GuiElementBox((IMapElement) map.objectAt(position),this.gridWidth,this.gridHeight);//dodac na klikniecie
                    element.getBox().setOnMouseClicked((action)->{
                        if(stopped){
                        trackedAnimal= (Animal)map.objectAt(position);
                        animalBox.getChildren().clear();
                        createAnimalStats(trackedAnimal);
                        }
                });

                if(trackedAnimal !=null ){
                    animalBox.getChildren().clear();
                    createAnimalStats(trackedAnimal);
                }


                grid.add(element.getBox(),position.getX(), position.getY(),1,1);
                GridPane.setHalignment(element.getBox(), HPos.CENTER);
                }
                else if (map.objectAt(position) instanceof Grass) {
                    element=new GuiElementBox((IMapElement) map.objectAt(position),this.gridWidth,this.gridHeight,grassImage);
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

    private HBox buttons(){
        Button stopButton=new Button("Stop");
        Button resumeButton=new Button("Resume");

        stopButton.setOnAction((click->{
            this.stopped=true;
            this.engineThread.suspend();
//           this.engine.stop();

        }));
        resumeButton.setOnAction((click)->{
//            this.engine.resume();
            this.engineThread.resume();
            this.stopped=false;



        });

        return new HBox(20, stopButton,resumeButton);
    }

    private void showDominantGenotype(){

    }



    public Scene getScene() {
        return scene;
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

    public void setEngineThread(Thread engineThread) {
        this.engineThread = engineThread;
    }
}
