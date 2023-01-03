package main;

import gui.App;
import javafx.application.Application;


import static java.lang.System.out;

public class World {

    public static void main(String[] args) {
        out.println("system wystartował");
        Application.launch(App.class,args);
        out.println("system zakończył działanie");

    }
}
