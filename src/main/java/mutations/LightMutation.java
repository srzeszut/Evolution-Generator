package mutations;

import interfaces.IMutation;

import java.util.Random;

public class LightMutation implements IMutation {

    Random random=new Random();
    @Override
    public int mutate(int gene) {
        int newGene;
        int option =random.nextInt(2);
        if(option==0){  //gen w górę

            if(gene<7){
             newGene=gene+1;
            }
            else{
                newGene=0;

            }
        }
        else {      //w dół
            if(gene>0){
                newGene=gene-1;
            }
            else{
                newGene=7;

            }

        }
        return newGene;
        }


}
