package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SVDdecomposition extends MatrixProblem {


    private Matrix U;
    private Matrix A;
    private Matrix V;
    private Matrix D;
    private int nU;
    private int nV;
    //U^T A V = D

    private String problemText = "Найти сингулярное разложение следующей матрицы: ";


    public SVDdecomposition(int seed){
        this.rand = new Random(seed);
        this.U = createU();
        this.V = createV();
        this.D = createD();
        this.A = U.mult(D).mult(V.transpose());
    }

    private Matrix createD(){
        var list = new ArrayList<IExpr>(
                List.of(
                        ExprUtils.getPositiveRandom(rand,1,3),
                        ExprUtils.getPositiveRandom(rand,1,3),
                        ExprUtils.getPositiveRandom(rand,1,3)
                        )
        );
        return Matrix.diag(3,list).mult(Matrix.diag(3,
                new ArrayList<>(
                        List.of(
                                IntegerSym.valueOf(nU*nV),
                                IntegerSym.valueOf(nU*nV),
                                IntegerSym.valueOf(nU*nV)
                                )
                )));
    }

    private Matrix createU(){
        int a  = rand.nextInt(2)*2+1;
        int b  =rand.nextInt(2)*2;
        int c  =rand.nextInt(2)*2;
        int d = rand.nextInt(2)*2;
        nU = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }
        return Matrix.orthogonal(rand,order,nU);
    }

    private Matrix createV(){
        int a  = rand.nextInt(2)*2+1;
        int b  =rand.nextInt(2)*2;
        int c  =rand.nextInt(2)*2;
        int d = rand.nextInt(2)*2;
        nV = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }
        return Matrix.orthogonal(rand,order,nV);
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
        return "Один из возможных ответов: " + texExpression(
                String.format(
                        "A = %s = U \\times \\Sigma \\times V^{-1} = %s \\times %s \\times %s",
                        getMatrixValues(A),
                        getMatrixValues(U),
                        getMatrixValues(D),
                        getMatrixValues(V.transpose())
                )
        );
    }
}
