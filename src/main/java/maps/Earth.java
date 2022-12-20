package maps;

import elements.Animal;
import elements.Vector2d;

public class Earth extends AbstractWorldMap{

    public Earth(int width,int height){
        this.height=height;
        this.width=width;

    }

    @Override
    public Vector2d MoveTo(Animal animal, Vector2d newPosition) {
//        if(newPosition)
    }
}
