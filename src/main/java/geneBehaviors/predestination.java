package geneBehaviors;

import interfaces.IGeneChoice;

public class predestination implements IGeneChoice {

    @Override
    public int choose(int[] genes,int activatedGene) {
//        int newGene=(activatedGene+1)%genes.length;
        return (activatedGene+1)%genes.length;

    }
}
