package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SVDdecomposition extends MatrixProblem {


    private Matrix U;
    private Matrix A;
    private Matrix V;
    private Matrix D;
    //U^T A V = D

    private String problemText = "Найти сингулярное разложение следующей матрицы: ";


    public SVDdecomposition(int seed){
        this.rand = new Random(seed);
        this.U = createU();
        this.V = createV();
        this.D = Matrix.randDiag(3,rand);
        this.A = U.mult(D).mult(V.transpose());
    }

    private Matrix createU(){
        int a  = rand.nextInt(4)*2;
        int b  =rand.nextInt(4)*2;
        int c  =rand.nextInt(4)*2;
        int d = 0;
        int n = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }
        return Matrix.orthogonal(rand,order,n);
    }

    private Matrix createV(){
        int a  = rand.nextInt(4)*2;
        int b  =rand.nextInt(4)*2;
        int c  =rand.nextInt(4)*2;
        int d = 0;
        int n = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }
        return Matrix.orthogonal(rand,order,n);
    }

    @Override
    public String getProblemText() {
        return problemText;
    }

    @Override
    public String getAnswerText() {
        return "";
    }

    @Override
    public String getProblemContent() {
        return texExpression(
                String.format(
                        "%s",
                        getMatrixValues(A)
                )
        );
    }

    @Override
    public String getAnswerContent()  {
        return texExpression(
                String.format(
                        "%s = %s \\times %s \\times %s",
                        getMatrixValues(A),
                        getMatrixValues(U),
                        getMatrixValues(D),
                        getMatrixValues(V.transpose())
                )
        );
    }
}
