package elements;

import interfaces.IMapElement;

public class Steppe extends AbstractMapElement {

    public Steppe(Vector2d positiony){
        this.position=position;

    }
    @Override
    public String getResource() {
         return "src/main/resources/dirt.png";
    }
}
