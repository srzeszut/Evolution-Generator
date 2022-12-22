package elements;

public class Grass extends AbstractMapElement{

    private int energyValue ;

    public Grass(Vector2d position,int energy){
        this.position=position;
        this.energyValue=energy;
    }

    protected int getEnergy(){
        return this.energyValue;
    }

    @Override
    public String getResource() {
        return "src/main/resources/grass.png";
    }

}