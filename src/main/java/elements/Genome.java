package elements;

import java.util.Random;

public class Genome {
    private int[] genes;
    Random random=new Random();

    public Genome(int numberOfGenes){
        this.genes=new int[numberOfGenes];
        for(int i=0;i<numberOfGenes;i++){
            this.genes[i]=randomGene();
        }

    }

    public Genome(Genome strongerParentGenome, Genome weakerParentGenome, int strongerParentEnergy,int weakerParentEnergy,int numberOfGenes){

        int strongerParentGenes[]=strongerParentGenome.getGenes();
        int weakerParentGenes[]=weakerParentGenome.getGenes();
        this.genes=new int[numberOfGenes];

        int sumOfEnergy=strongerParentEnergy+weakerParentEnergy;

        int strongerParentPart = (int)((strongerParentEnergy/sumOfEnergy)*numberOfGenes);
        int weakerParentPart =numberOfGenes-strongerParentPart;

        int whichPart= random.nextInt(2);
        int j=0;
        if(whichPart==0){         //lewy
            for(int i=0;i<strongerParentPart;i++){
                this.genes[j]=strongerParentGenes[i];
                j++;
            }
            for(int i=0;i<weakerParentPart;i++){
                this.genes[j]=weakerParentGenes[j];
                j++;
            }
        }
        else{                     //prawy

            for(int i=0;i<weakerParentPart;i++){
                this.genes[j]=weakerParentGenes[i];
                j++;
            }
            for(int i=0;i<strongerParentPart;i++){
                this.genes[j]=strongerParentGenes[j];
                j++;
            }

        }

        int numberOfMutations=random.nextInt(numberOfGenes);
        for(int i = 0;i<numberOfMutations;i++){
            int chooseGene=random.nextInt(numberOfGenes);
            this.genes[chooseGene]=randomGene();
        }
    }

    private int randomGene(){
        return random.nextInt(8);

    }

    public int[] getGenes(){
        return this.genes;
    }


}
