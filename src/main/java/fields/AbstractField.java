package fields;

import elements.Grass;
import elements.Vector2d;
import interfaces.IFieldOption;
import maps.AbstractWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class AbstractField implements IFieldOption {//protected

    protected AbstractWorldMap map;


    protected HashMap<Vector2d,Grass> favouredGrassPositions=new HashMap<>();
    protected HashMap<Vector2d,Grass> disfavouredGrassPositions=new HashMap<>();
    //mapa z trawami chcianymi i i niechcianymi
    public abstract void generatePositions();
    public abstract void eatGrass(Vector2d position);

    public void setMap(AbstractWorldMap map) {
        this.map = map;
    }
    @Override
    public void spawnGrass(int numberOfGrass) {
//        System.out.println("here1");

        Random random = new Random();
        for(int i=0;i<numberOfGrass;i++) {

            int chances = random.nextInt(101);
//            System.out.println("chances"+chances);


            if (chances <= 80 ) {
                Vector2d favPos = spawnFavoured();
//                System.out.println("f"+i);
                if(favPos!=null){
                    this.map.placeGrass(favPos);
                }
                else {
                    Vector2d dfavPos = spawnDisfavoured();
//                    System.out.println("d"+i);
                    if(dfavPos!=null){
                        this.map.placeGrass(dfavPos);

                    }
//                System.out.println(dFavPos);
                }

//                System.out.println(favPos);
            } else {
                Vector2d dfavPos = spawnDisfavoured();
//                System.out.println("d"+i);
                if(dfavPos!=null){
                    this.map.placeGrass(dfavPos);

                }
//                System.out.println(dFavPos);
            }
        }
    }





    protected abstract Vector2d spawnFavoured();
    protected abstract Vector2d spawnDisfavoured();
}
