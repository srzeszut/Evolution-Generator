package gui;


import fields.AbstractField;
import fields.ForestedEquator;
import fields.ToxicBodies;
import geneBehaviors.Madness;
import geneBehaviors.predestination;

import interfaces.IGeneChoice;

import interfaces.IMutation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maps.AbstractWorldMap;
import maps.Earth;
import maps.PortalMap;
import mutations.LightMutation;
import mutations.RandomMutation;
import simulation.SimulationEngine;
import javafx.scene.control.Button;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;


import static java.lang.System.out;

public class App extends Application {
    private GridPane grid = new GridPane();
    private AbstractWorldMap map;
    private SimulationEngine engine;
    private TextField heightTextField;
    private TextField widthTextField;
    private TextField startingEnergyTextField;
    private TextField grassProfitTextField;
    private TextField animalsAtTheBeginningTextField;
    private TextField grassAtTheBeginningTextField;
    private TextField refreshTimeTextField;
    private TextField grassEverydayField;
    private TextField genomeLengthField;
    private VBox optionsVBox;
    private VBox mainVBox;
    private ListView mapList;
    private ListView spawningList;
    private ListView mutationList;
    private ListView genomeList;
    private final int ROW_HEIGHT = 24;
    private String  daysCounter;
    private HBox daysContainer;

    //simulation options
    private ObservableList mapOption;
    private ObservableList fieldOption;
    private ObservableList mutationOption;
    private ObservableList genomeOption;



    private int gridHeight= 20;
    private int gridWidth= 20;
    private final int simulationWidth=950;
    private Button startButton;
    private Button back;
    private TextField minimumEnergyTextField;
    private TextField reproductionCostField;

    @Override
    public void start(Stage primaryStage){
        try{
            primaryStage.setTitle("Evolution Generator");
            try{
                Image image = new Image(new FileInputStream("src/main/resources/animal.jpg"));
                primaryStage.getIcons().add(image);
                    }
                    catch (FileNotFoundException err) {
                        out.println(err.getMessage());
                    }

        startMenu();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(this.mainVBox);
        Scene scene = new Scene(this.mainVBox);


        primaryStage.setScene(scene);
        primaryStage.show();
            this.back=new Button("Back");
            back.setOnAction((click)->{
                start(primaryStage);
                engine.end();

            });

        startButton.setOnAction((click) -> {

            this.mapOption = mapList.getSelectionModel().getSelectedIndices();
            this.fieldOption = spawningList.getSelectionModel().getSelectedIndices();
            this.mutationOption = mutationList.getSelectionModel().getSelectedIndices();
            this.genomeOption = genomeList.getSelectionModel().getSelectedIndices();
            SimulationWindow newWindow=new SimulationWindow();
            SimulationEngine newSimulation=setSimulationFromOptions(newWindow);
            AbstractWorldMap newMap=newSimulation.getMap();
            newWindow.setMap(newMap);
            newWindow.createStats();
            newWindow.setEngine(newSimulation);

                Stage simulationWindow=new Stage();

                simulationWindow.setScene(newWindow.getScene());//zmienic na scene z statystykami
                simulationWindow.show();
                simulationWindow.setX(bounds.getMinX());
                simulationWindow.setY(bounds.getMinY());
                simulationWindow.setWidth(bounds.getWidth());
                simulationWindow.setHeight(bounds.getHeight());
            Platform.runLater(()->{
                newWindow.renderGrid();
            });
            Thread engineThread = new Thread(newWindow.getEngine(),"Simulation");
            newWindow.setEngineThread(engineThread);
            engineThread.start();



            simulationWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("Stage is closing");
                    newSimulation.end();
                    newSimulation.resume();
//                    engineThread.interrupt();
                }
            });
            });




    } catch (IllegalArgumentException exception) {
        System.out.println(exception.getMessage());
    }

    }
    private void startMenu(){
        // Welcome text
        HBox welcomeText = createHeadLineText("Evolution Generator 2000!", 24);

        // Map properties
        HBox mapProperties = createHeadLineText("Map properties:", 16);
        Text heightText = createText("Map height:");
        this.heightTextField = new TextField("10");
        HBox heightBox = createHBox(heightText, this.heightTextField);
        Text widthText = createText("Map width:");
        this.widthTextField = new TextField("10");
        HBox widthBox = createHBox(widthText, this.widthTextField);
        Text optionText = createText("Map options:");
        this.mapList=new ListView<>();
        this.mapList.setPrefHeight(2 * ROW_HEIGHT);
        mapList.getItems().add("Kula ziemska ");
        mapList.getItems().add("Piekielny portal");
        HBox optionsBox = createHBox(optionText, this.mapList);

        // Animal properties
        HBox animalProperties = createHeadLineText("Animal properties:", 16);
        Text startingEnergyText = createText("Animal starting energy:");
        this.startingEnergyTextField = new TextField("30");
        HBox startingEnergyBox = createHBox(startingEnergyText, this.startingEnergyTextField);
        Text animalsAtTheBeginningText = createText("Animals spawned at the beginning:");
        this.animalsAtTheBeginningTextField = new TextField("10");
        HBox animalsAtTheBeginningBox = createHBox(animalsAtTheBeginningText, this.animalsAtTheBeginningTextField);

        Text minimumEnergyText = createText("Minimum energy to reproduce:");
        this.minimumEnergyTextField = new TextField("100");
        HBox minimumEnergyBox = createHBox(minimumEnergyText, this.minimumEnergyTextField);

        Text reproductionCostText = createText("Reproduction cost (0,1):");
        this.reproductionCostField = new TextField("0.2");
        HBox reproductionBox = createHBox(reproductionCostText, this.reproductionCostField);

        Text mutationText = createText("Mutation options:");
        this.mutationList=new ListView<>();
        this.mutationList.setPrefHeight(2 * ROW_HEIGHT);
        mutationList.getItems().add("Pelna losowosc");
        mutationList.getItems().add("Lekka korekta");
        HBox mutationBox = createHBox(mutationText, this.mutationList);

        Text genomeLengthText = createText("Genome length:");
        this.genomeLengthField = new TextField("30");
        HBox genomeLengthBox = createHBox(genomeLengthText, this.genomeLengthField);

        Text genomeText = createText("Genome options:");
        this.genomeList=new ListView<>();
        this.genomeList.setPrefHeight(2 * ROW_HEIGHT);
        genomeList.getItems().add("Pelna predestynacja");
        genomeList.getItems().add("Nieco szalenstwa");
        HBox genomeBox = createHBox(genomeText, this.genomeList);


        // grass properties
        HBox grassProperties = createHeadLineText("Grass properties:", 16);
        Text grassProfitText = createText("Grass profit:");
        this.grassProfitTextField = new TextField("10");
        HBox grassProfitBox = createHBox(grassProfitText, this.grassProfitTextField);
        Text grassAtTheBeginningText = createText("Grass spawned at the beginning:");
        this.grassAtTheBeginningTextField = new TextField("10");
        HBox grassAtTheBeginningBox = createHBox(grassAtTheBeginningText, this.grassAtTheBeginningTextField);
        Text grassEverydayText = createText("Grass spawned everyday:");
        this.grassEverydayField = new TextField("5");
        HBox grassEverydayBox = createHBox(grassEverydayText, this.grassEverydayField);
        Text spawnOptionText = createText("Spawning grass options:");
        this.spawningList=new ListView<>();
        this.spawningList.setPrefHeight(2 * ROW_HEIGHT);
        spawningList.getItems().add("Zalesione rowniki");
        spawningList.getItems().add("Toksyczne trupy");
        HBox spawnOptionsBox = createHBox(spawnOptionText, this.spawningList);

        // Other options
        HBox otherOptions = createHeadLineText("Other options:", 16);
        Text refreshTimeText = createText("Refresh time in ms:");
        this.refreshTimeTextField = new TextField("1000");
        HBox refreshTimeBox = createHBox(refreshTimeText, this.refreshTimeTextField);

        HBox configurations = createHeadLineText("Configurations:", 16);

        Button configuration1=new Button("1");
        configuration1.setOnAction((click)->loadConfigurations("src/main/resources/conf1.properties"));

        Button configuration2=new Button("2");
        configuration2.setOnAction((click)->loadConfigurations("src/main/resources/conf2.properties"));

        Button configuration3=new Button("3");
        configuration3.setOnAction((click)->loadConfigurations("src/main/resources/conf3.properties"));

        HBox buttons=new HBox(20,configuration1,configuration2,configuration3);
        buttons.setAlignment(Pos.CENTER);
        VBox configurationBox= new VBox(20,configurations,buttons);

        // Start Button
//        HBox startButton = createStartButton();
        this.startButton =new Button("Start");
        HBox startBox=createHBox(new Text(""),startButton);

        HBox animalAndGrassOptions=new HBox(50,new VBox(20,animalProperties, startingEnergyBox
                , animalsAtTheBeginningBox,mutationBox,genomeLengthBox,genomeBox,minimumEnergyBox,reproductionBox)
                ,new VBox(20,grassProperties,grassAtTheBeginningBox,grassEverydayBox,spawnOptionsBox,grassProfitBox,configurationBox));
        animalAndGrassOptions.setAlignment(Pos.CENTER);

        this.optionsVBox = new VBox(welcomeText, mapProperties, heightBox, widthBox,optionsBox,animalAndGrassOptions ,  otherOptions,
                refreshTimeBox,startBox );
        this.optionsVBox.setSpacing(20);
        this.optionsVBox.setAlignment(Pos.CENTER);

        this.mainVBox = new VBox(this.optionsVBox);


    }

    private void loadConfigurations(String path){

        try (InputStream input = new FileInputStream(path)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value
            this.heightTextField.setText(prop.getProperty("HEIGHT"));
            this.widthTextField.setText(prop.getProperty("WIDTH"));
            this.startingEnergyTextField.setText(prop.getProperty("STARTING_ENERGY"));
            this.animalsAtTheBeginningTextField.setText(prop.getProperty("STARTING_ANIMALS"));
            this.genomeLengthField.setText(prop.getProperty("GENOME_LENGTH"));
            this.minimumEnergyTextField.setText(prop.getProperty("MIN_ENERGY"));
            this.reproductionCostField.setText(prop.getProperty("REPRODUCTION_COST"));
            this.grassAtTheBeginningTextField.setText(prop.getProperty("STARTING_GRASS"));
            this.grassEverydayField.setText(prop.getProperty("SPAWN_GRASS"));
            this.grassProfitTextField.setText(prop.getProperty("GRASS_PROFIT"));


        } catch (IOException ex) {
            System.out.println("Nie udało się załadować konfiguracji");
        }

    }
    private SimulationEngine setSimulationFromOptions(SimulationWindow window){

         int mapHeight=Integer.parseInt(this.heightTextField.getText());
         int mapWidth=Integer.parseInt(this.widthTextField.getText());
         int startingGrass=Integer.parseInt(this.grassAtTheBeginningTextField.getText());
         int grassEnergy=Integer.parseInt(this.grassProfitTextField.getText());
         int growingGrass=Integer.parseInt(this.grassEverydayField.getText());
         int numberOfAnimals=Integer.parseInt(this.animalsAtTheBeginningTextField.getText());
         int startingEnergy=Integer.parseInt(this.startingEnergyTextField.getText());
         int genomeLength=Integer.parseInt(this.genomeLengthField.getText());
         int delay=Integer.parseInt((this.refreshTimeTextField).getText());
         int minimumEnergy=Integer.parseInt((this.minimumEnergyTextField).getText());
         double reproductionCost=Double.parseDouble(this.reproductionCostField.getText());

        AbstractField field=new ForestedEquator();;
        for(Object o : fieldOption){
            if(o.equals(0)){
                field=new ForestedEquator();
            }
            else{
//                System.out.println("toksyczne");
                field=new ToxicBodies();
            }
        }

        IMutation mutation=new RandomMutation();
        for(Object o : mutationOption){
            if(o.equals(0)){
                mutation=new RandomMutation();
            }
            else{
                mutation=new LightMutation();
            }
        }

        IGeneChoice choice= new predestination();
        for(Object o : genomeOption){
            if(o.equals(0)){
                choice= new predestination();
            }
            else{
                choice= new Madness();
            }
        }
        AbstractWorldMap map=new Earth(mapWidth,mapHeight,startingGrass,grassEnergy,growingGrass,field);
        for(Object o : mapOption){
            if(o.equals(0)){
                map=new Earth(mapWidth,mapHeight,startingGrass,grassEnergy,growingGrass,field);
            }
            else{
                map=new PortalMap(mapWidth,mapHeight,startingGrass,grassEnergy,growingGrass,field);
            }
        }
        return new SimulationEngine(window,map,numberOfAnimals,startingEnergy,
                reproductionCost,0,genomeLength,mutation,genomeLength,choice,minimumEnergy,delay);


    }

    private HBox createHeadLineText(String text, int size) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Montserrat", FontWeight.BOLD, size));
        HBox hbox = new HBox(newText);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Text createText(String text) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Montserrat", FontWeight.MEDIUM, 14));
        return newText;
    }

    private HBox createHBox(Text text, Node field) {
        HBox hbox = new HBox(text, field);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        return hbox;
    }


}
