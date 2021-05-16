package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import java.util.Random;

public class MatrixRank extends MatrixProblem {

    public MatrixRank(int seed)  {
        this.rand = new Random(seed);
        int width  = rand.nextInt(4)+3;
        int height = rand.nextInt(2)+3;
        rank = rand.nextInt(height-1)+1;

        this.matrix = Matrix.ofRank(width,height,rank,rand);
        initialMatrix = getMatrixValues(matrix);
        matrix.simpleShuffle(rand,-2,2);
    }

    @Override
    public String getProblemText() {
        return "Найти ранг матрицы ";
    }

    @Override
    public String getAnswerText() {
        return "Ранг матрицы равен ";
    }

    @Override
    public String getProblemContent() {
        String matrixString = getMatrixValues(matrix);
        return texExpression(matrixString);
    }

    @Override
    public String getAnswerContent() {
        return
                texExpression( String.format("%s rank = %s", initialMatrix, rank));
    }

    private Matrix matrix;
    private final String initialMatrix;
    private final int rank;

}
