package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import org.matheclipse.core.expression.IntegerSym;

import java.util.Random;

public class InverseMatrix extends MatrixProblem{

    private final Matrix matrix;
    private final Matrix inverse;
    private boolean isInvertible =true;

    public InverseMatrix(int seed) throws Exception {
        this.rand = new Random(seed+1);
        int width = 3;

        if (rand.nextBoolean()){
            int rank = rand.nextInt(width-1)+1;
            matrix = Matrix.ofRank(width,width,rank,rand);
            isInvertible = false;
        }
        else{
            matrix = Matrix.identity(width);
        }

        inverse = matrix.strongShuffle(rand,-2,2,3);
    }

    @Override
    public String getProblemText() {
        return "Найти обратную матрицу";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        String matrixString = getMatrixValues(matrix);

        return texExpression(matrixString);
    }

    @Override
    public String getAnswerContent() {
        String reverseString = getMatrixValues(this.inverse);
        if (!isInvertible)
            return "Матрица не обратима";
        return texExpression(reverseString);
    }
}
