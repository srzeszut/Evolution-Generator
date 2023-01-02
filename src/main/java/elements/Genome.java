package elements;

import interfaces.IMutation;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Genome {
    private int[] genes;
    private IMutation mutation;
    private int minNumberOfMutations;
    private int maxNumberOfMutations;
    Random random = new Random();

    public Genome(int numberOfGenes,IMutation mutation) {
        this.genes = new int[numberOfGenes];
        for (int i = 0; i < numberOfGenes; i++) {
            this.genes[i] = randomGene();
        }
        this.mutation=mutation;

    }

    public Genome(Genome strongerParentGenome, Genome weakerParentGenome, int strongerParentEnergy,
                  int weakerParentEnergy, int numberOfGenes,IMutation mutation,int minNumberOfMutations,int maxNumberOfMutations) {

        int strongerParentGenes[] = strongerParentGenome.getGenes();
        int weakerParentGenes[] = weakerParentGenome.getGenes();
        this.genes = new int[numberOfGenes];
        this.mutation=mutation;
        this.minNumberOfMutations=minNumberOfMutations;
        this.maxNumberOfMutations=maxNumberOfMutations;

        int sumOfEnergy = strongerParentEnergy + weakerParentEnergy;

        int strongerParentPart = (int) ((strongerParentEnergy / sumOfEnergy) * numberOfGenes);
        int weakerParentPart = numberOfGenes - strongerParentPart;

        int whichPart = random.nextInt(2);
        int j = 0;
        if (whichPart == 0) {         //lewy
            for (int i = 0; i < strongerParentPart; i++) {
                this.genes[j] = strongerParentGenes[i];
                j++;
            }
            for (int i = 0; i < weakerParentPart; i++) {
                this.genes[j] = weakerParentGenes[j];
                j++;
            }
        } else {                     //prawy

            for (int i = 0; i < weakerParentPart; i++) {
                this.genes[j] = weakerParentGenes[i];
                j++;
            }
            for (int i = 0; i < strongerParentPart; i++) {
                this.genes[j] = strongerParentGenes[j];
                j++;
            }

        }

        int numberOfMutations=random.nextInt(minNumberOfMutations,maxNumberOfMutations);//min max liczba mutacji
        for(int i = 0;i<numberOfMutations;i++){
            int chooseGene=random.nextInt(numberOfGenes);
            this.genes[chooseGene]=this.mutation.mutate(this.genes[chooseGene]);

        }
    }

    private int randomGene() {
        return random.nextInt(8);

    }

    public int[] getGenes() {
        return this.genes;
    }


    public void setGene(int index,int gene) {
        this.genes[index] = gene;

    }

    @Override
    public boolean equals(Object other) {
        if(this==other)
            return true;
        if(!(other instanceof Genome))
            return false;
        Genome that = (Genome) other;
        if(Arrays.equals(this.genes, that.genes))
            return true;
        else
            return false;
    }
    @Override
    public int hashCode() {
        int hash=0;
        for(int gene:this.genes){
            hash+=gene;
            hash*=10;
        }
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.genes);
    }
}
