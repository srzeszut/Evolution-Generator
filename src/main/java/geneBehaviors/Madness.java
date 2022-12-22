package geneBehaviors;

import interfaces.IGeneChoice;

import java.util.Random;

public class Madness implements IGeneChoice {
    Random random = new Random();

    @Override
    public int choose(int[] genes, int activatedGene) {

        int chances = random.nextInt(101);
        if (chances <= 80) {
            return (activatedGene + 1) % genes.length;
        }

        else {
            int newGene = random.nextInt(genes.length);
            while (newGene == activatedGene) {
                newGene = random.nextInt(genes.length);
            }
            return newGene;


        }
    }
}
