package com.moyang.ntoolbox;

import java.util.Random;

public class RandNumGen {

    public int genRandInt(long seed, int bound){
        Random random = new Random(seed);
        return random.nextInt(bound + 1);
    }

}
