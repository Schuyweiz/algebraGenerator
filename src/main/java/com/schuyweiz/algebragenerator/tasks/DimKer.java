package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;

import java.util.Random;

public class DimKer extends MatrixProblem{

    private Matrix A;
    private Matrix B;
    private Matrix X;
    private int rank;
    private String problemText = "Найти ранг ядра и отображения\n матрицы перехода из А в В";

    public DimKer(int seed){
        this.rand = new Random(seed);
        this.rank = rand.nextInt(3)+1;
        this.A = initA();
        this.X = initX();
        this.B = initB();
    }

    private Matrix initX(){
        return Matrix.ofRank(4,4,rank,rand);
    }
    private Matrix initA(){
        var matrix = Matrix.randDiag(4,rand);
        return matrix.strongShuffle(rand,-3,3);
    }
    private Matrix initB(){
        return A.mult(X);
    }

    @Override
    public String getProblemText() {
        return problemText;
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        return texExpression(
                String.format(
                        "A = %s\n" +
                                "B = %s",
                        getMatrixValues(A),
                        getMatrixValues(B)
                )
        );
    }

    @Override
    public String getAnswerContent() {
        return String.format(
                "%s\n" +
                        "rank = %s",
                texExpression("X = " + getMatrixValues(X)),
                rank
        );
    }
}
