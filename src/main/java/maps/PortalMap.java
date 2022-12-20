package maps;

import elements.Animal;
import elements.Vector2d;

public class PortalMap extends AbstractWorldMap{

    public PortalMap(int height, int width){
        this.height=height;
        this.width=width;
    }

    private void teleport(Animal animal,Vector2d position){//przenosi animala po mapie a jak wychodzi zaz granice to teleportuje i zuzywa energie



    }

    @Override
    public Vector2d MoveTo(Animal animal, Vector2d position) {
        return null;
    }
}
