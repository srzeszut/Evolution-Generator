package fields;

import elements.Vector2d;
import interfaces.IFieldOption;
import maps.AbstractWorldMap;

import java.util.ArrayList;

public class ForestedEquator implements IFieldOption {

    private double favRatio=0.2;
    private  double disfavRatio=0.8;
    @Override
    public Vector2d[] favouredPositions(AbstractWorldMap map) {
        ArrayList<Vector2d> newPositions=new ArrayList<>();
        int startHeight=(int) (map.getHeight()*(disfavRatio/2));
        int endHeight= startHeight+ (int) (map.getHeight()*(favRatio));
        for(int i=startHeight;i<endHeight;i++){
            for(int j=0;j<map.getWidth();j++){
                Vector2d newPosition= new Vector2d(j,i);
                if(map.getGrassPositions().get(newPosition) == null){
                   newPositions.add(newPosition);
                }

            }

        }
        Vector2d[] positions= newPositions.toArray(new Vector2d[0]);
        return positions;

    }

    @Override
    public Vector2d[] disfavouredPositions(AbstractWorldMap map) {
        ArrayList<Vector2d> newPositions=new ArrayList<>();
        int startHeight=0;
        int endHeight= (int) (map.getHeight()*(disfavRatio/2));
        for(int i=startHeight;i<endHeight;i++){
            for(int j=0;j<map.getWidth();j++){
                Vector2d newPosition= new Vector2d(j,i);
                if(map.getGrassPositions().get(newPosition) == null){
                    newPositions.add(newPosition);
                }

            }

        }
        Vector2d[] positions= newPositions.toArray(new Vector2d[0]);
        return positions;
    }
}
