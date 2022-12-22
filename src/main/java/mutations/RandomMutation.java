package mutations;

import interfaces.IMutation;

import java.util.Random;

public class RandomMutation implements IMutation {

    Random random=new Random();
    @Override
    public int mutate(int gene) {
        int newGene =random.nextInt(8);
        while (newGene==gene) {
            newGene =random.nextInt(8);
        }
        return newGene;

    }
    }


