package fields;

import elements.Grass;
import elements.Vector2d;

import java.util.ArrayList;
import java.util.Optional;

public class ForestedEquator extends AbstractField {


    @Override
    public void generatePositions()
    {

        int equatorStart= (int)(map.getHeight()*0.4);
        int equatorEnd=(int)(map.getHeight()*0.6);
        for(int i=0;i<map.getHeight();i++){
            for(int j=0;j< map.getWidth();j++){
                if(i>=equatorStart && i <= equatorEnd){
                    this.favouredGrassPositions.put(new Vector2d(j,i),new Grass(new Vector2d(j,i),0));
                }
                else {
                    this.disfavouredGrassPositions.put(new Vector2d(j,i),new Grass(new Vector2d(j,i),0));

                }

            }
        }
//        for (Vector2d key : favouredGrassPositions.keySet()) {
//            System.out.println("f"+key+" "+favouredGrassPositions.get(key).getEnergy());
//
//        }
//        for (Vector2d key : disfavouredGrassPositions.keySet()) {
//            System.out.println("d"+key+" "+disfavouredGrassPositions.get(key).getEnergy());
//
//        }
    }

    @Override
    public void eatGrass(Vector2d position) {
        boolean flag=false;
        for(Vector2d grassPosition: favouredGrassPositions.keySet()){
            if(grassPosition==position){
                favouredGrassPositions.put(position,new Grass(position,0));
                flag=true;
                break;
            }
        }
        if(!flag){
            for(Vector2d grassPosition: disfavouredGrassPositions.keySet()){
                if(grassPosition==position){
                    disfavouredGrassPositions.put(position,new Grass(position,0));
                    break;
                }

        }

    }}

    @Override
    protected Vector2d spawnFavoured() {//co jesli nullem
//        System.out.println("sizefffffffffffffffffffffffffffffff"+favouredGrassPositions.size());
//        for (Vector2d key : favouredGrassPositions.keySet()) {
//            System.out.println("f"+key+" "+favouredGrassPositions.get(key).getEnergy());
//
//        }

        Vector2d grassPosition=null;
        for(Vector2d position: favouredGrassPositions.keySet()){
            if(favouredGrassPositions.get(position).getEnergy() ==0){
                grassPosition=position;
                break;

            }
        }
        if(grassPosition!=null){
            favouredGrassPositions.put(grassPosition,new Grass(grassPosition,this.map.getGrassEnergy()));

        }

//        System.out.println("fp"+grassPosition);
        return grassPosition;
    }

    @Override
    protected Vector2d spawnDisfavoured() {
//        System.out.println("sizeddddddddddddddddddddddddddddddddd"+disfavouredGrassPositions.size());
//        for (Vector2d key : disfavouredGrassPositions.keySet()) {
//            System.out.println("d"+key+" "+disfavouredGrassPositions.get(key).getEnergy());
//
//        }
//        System.out.println("here4");
        Vector2d grassPosition=null;
        for(Vector2d position: disfavouredGrassPositions.keySet()){
            if(disfavouredGrassPositions.get(position).getEnergy() == 0){
                grassPosition=position;
//                disfavouredGrassPositions.put(position,new Grass(position,this.map.getGrassEnergy()));
                break;
            }
        }
        if(grassPosition!=null){
            disfavouredGrassPositions.put(grassPosition,new Grass(grassPosition,this.map.getGrassEnergy()));

        }
//        System.out.println("dp"+grassPosition);
        return grassPosition;

    }



}
