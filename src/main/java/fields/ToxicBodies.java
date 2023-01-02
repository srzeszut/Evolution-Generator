package fields;

import elements.Grass;
import elements.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToxicBodies extends AbstractField {

    @Override
    public void generatePositions() {
        ArrayList<Vector2d> positions=new ArrayList<>();
        HashMap<Vector2d,Integer> deadPositions= map.getDeadPositions();
        deadPositions.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach((element)->{
//                    System.out.println(element.getValue());
                    positions.add(element.getKey());
                });

        for(int i=0;i< positions.size();i++){
            if(i< (int)(positions.size()*0.2)){
                this.favouredGrassPositions.add(positions.get(i));
            }
            else {
                this.disfavouredGrassPositions.add(positions.get(i));

            }
        }

        Collections.shuffle(favouredGrassPositions);
        Collections.shuffle(disfavouredGrassPositions);

    }

    @Override
    protected Vector2d spawnFavoured() {
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
