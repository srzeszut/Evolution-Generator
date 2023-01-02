package fields;

import elements.Grass;
import elements.Vector2d;
import java.util.Collections;
import java.util.Random;

public class ForestedEquator extends AbstractField {


    @Override
    public void generatePositions()
    {
        int equatorStart= (int)(map.getHeight()*0.4);
        int equatorEnd=(int)(map.getHeight()*0.6);
        for(int i=0;i<map.getHeight();i++){
            for(int j=0;j< map.getWidth();j++){
                if(i>=equatorStart && i <= equatorEnd){
                    this.favouredGrassPositions.add(new Vector2d(j,i));
                }
                else {
                    this.disfavouredGrassPositions.add(new Vector2d(j,i));

                }

            }
        }
        Collections.shuffle(favouredGrassPositions);
        Collections.shuffle(disfavouredGrassPositions);
    }



    @Override
    protected Vector2d spawnFavoured() {//co jesli nullem

        Vector2d grassPosition=null;
        for(Vector2d position: favouredGrassPositions){
            if(!(map.objectAt(position) instanceof Grass)){
                grassPosition=position;
                break;

            }
        }

//        System.out.println("fp"+grassPosition);
        return grassPosition;
    }
    Random random= new Random();
    @Override
    protected Vector2d spawnDisfavoured() {
        Vector2d grassPosition=null;
        for(Vector2d position: disfavouredGrassPositions){
            if(!(map.objectAt(position) instanceof Grass)){
                grassPosition=position;
                break;

            }


    }
        return grassPosition;
    }



}
