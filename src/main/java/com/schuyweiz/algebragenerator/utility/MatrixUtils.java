package com.schuyweiz.algebragenerator.utility;

import java.util.Random;

public class MatrixUtils {

    public static int basedRandom(int base, int bound, Random random){
        return random.nextInt(bound) + base;
    }
}
