package maps;

import elements.Animal;
import elements.Vector2d;
import interfaces.IFieldOption;

public class Earth extends AbstractWorldMap{



    public Earth(int width, int height, IFieldOption field){
        this.height=height;
        this.width=width;
        this.field=field;

    }

    @Override
    public Vector2d MoveTo(Animal animal, Vector2d newPosition) {
//        if(newPosition)
        return null;
    }
}
