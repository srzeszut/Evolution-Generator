package fields;

import elements.Grass;
import elements.Vector2d;

import java.util.Collections;

public class Trupy extends AbstractField {
    @Override
    public void generatePositions() {

        for(int i=0;i<map.getHeight();i++){
            for(int j=0;j< map.getWidth();j++){

                    this.favouredGrassPositions.add(new Vector2d(j,i));
                }


            }

        Collections.shuffle(favouredGrassPositions);
        Collections.shuffle(disfavouredGrassPositions);

    }

    @Override
    protected Vector2d spawnFavoured() {
        generatePositions();
        Vector2d grassPosition = null;
        for (Vector2d position : favouredGrassPositions) {
            if (!(map.objectAt(position) instanceof Grass)) {
                grassPosition = position;
                break;

            }
        }

        return grassPosition;
    }

    @Override
    protected Vector2d spawnDisfavoured() {
        generatePositions();
        Vector2d grassPosition = null;
        for (Vector2d position : disfavouredGrassPositions) {
            if (!(map.objectAt(position) instanceof Grass)) {
                grassPosition = position;
                break;

            }
        }


        return grassPosition;
    }
}
