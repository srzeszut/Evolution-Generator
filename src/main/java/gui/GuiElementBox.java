package gui;

import interfaces.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    private VBox vbox ;
    public GuiElementBox(IMapElement element,int width,int height) throws FileNotFoundException {//cos zeby szybciej ładowało
        Image image = new Image(new FileInputStream(element.getResource()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vbox=new VBox(imageView);
        vbox.setAlignment(Pos.CENTER);



    }
    public GuiElementBox(IMapElement element,int width,int height,Image image) {//cos zeby szybciej ładowało
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        vbox=new VBox(imageView);
        vbox.setAlignment(Pos.CENTER);


    }

    protected VBox getBox(){
        return vbox;
    }

}

