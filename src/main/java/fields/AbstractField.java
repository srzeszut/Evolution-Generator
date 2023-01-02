package fields;


import elements.Vector2d;
import interfaces.IFieldOption;
import maps.AbstractWorldMap;

import java.util.ArrayList;

import java.util.Random;

public abstract class AbstractField implements IFieldOption {//protected

    protected AbstractWorldMap map;


    protected ArrayList<Vector2d> favouredGrassPositions=new ArrayList<>();
    protected ArrayList<Vector2d> disfavouredGrassPositions=new ArrayList<>();
    public abstract void generatePositions();

    public void setMap(AbstractWorldMap map) {
        this.map = map;
    }
    @Override
    public Vector2d spawnGrass() {

        Random random = new Random();
            int chances = random.nextInt(101);

            if (chances <= 80 ) {
                Vector2d favPos = spawnFavoured();
                if(favPos!=null){
                    return favPos;
                }
                else {
                    Vector2d dfavPos = spawnDisfavoured();
                    if(dfavPos!=null){
                        return dfavPos;
                    }
                }
            } else {
                Vector2d dfavPos = spawnDisfavoured();
                if(dfavPos!=null) {
                    return dfavPos;
                }
            }
        return null;
    }





    protected abstract Vector2d spawnFavoured();
    protected abstract Vector2d spawnDisfavoured();
}
