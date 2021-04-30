package com.schuyweiz.algebragenerator.utility;

import com.schuyweiz.algebragenerator.Matrix;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class ElementaryRowOp {

    public static Matrix addSubRowRandom(int fromRow, int toRow, Matrix matrixFrom, Matrix matrixTo, Random rand){

        int fromCoef = rand.nextInt(10) * (rand.nextInt(2)-1);
        int toCoef = 1 + rand.nextInt(10) * (rand.nextInt(2)-1);

        return matrixTo;
    }

    public static SimpleMatrix swap(int rowA, int rowB, Matrix matrix){
    return null;
    }


}
