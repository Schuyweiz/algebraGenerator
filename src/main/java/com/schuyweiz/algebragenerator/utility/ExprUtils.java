package com.schuyweiz.algebragenerator.utility;

import org.matheclipse.core.expression.*;
import org.matheclipse.core.interfaces.IExpr;

import java.util.Random;

public class ExprUtils {

    public static IExpr getPositiveRandom(Random rand, int left, int right){
        int num = rand.nextInt(right-left) + right+1;
        return IntegerSym.valueOf(num);
    }
    public static IExpr getRandom(Random rand, int left, int right){
        if (rand.nextBoolean()){
            return IntegerSym.valueOf(-rand.nextInt(-left));
        }
        return IntegerSym.valueOf(rand.nextInt(right));
    }

    public static IExpr getRandomNonNull(Random rand, int left, int right){
        IExpr rhs;
        if (left<0){
            rhs = getPositiveRandom(rand, 1,right);
        }
        else if (left!=0){
            return getPositiveRandom(rand, left, right);
        }
        else{
            return getPositiveRandom(rand,1,right);
        }

        boolean isPositive = rand.nextBoolean();

        if (isPositive){
            return rhs;
        }
        else{
            return rhs.times(IntegerSym.valueOf(-1));
        }
    }

}
