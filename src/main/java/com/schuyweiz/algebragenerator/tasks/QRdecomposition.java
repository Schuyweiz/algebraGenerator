package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QRdecomposition extends MatrixProblem {


    private Matrix Q;
    private Matrix R;
    private Matrix A;
    private int n;

    public QRdecomposition(int seed){

        this.rand = new Random(seed);

        Q = createQ();
        R = createR();

        A = Q.mult(R);

    }

    private Matrix createR(){
        var rows=  Q.getRows();
        ArrayList<Row> rowsR = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                row.add(IntegerSym.valueOf(0));
            }
            for (int j = i; j < rows.get(i).getSize(); j++) {
                row.add(ExprUtils.getRandom(rand,-3,3).multiply(IntegerSym.valueOf(n)));
            }
            rowsR.add(new Row(row));
        }
        return new Matrix(rowsR);
    }

    private Matrix createQ(){

        int a  = rand.nextInt(4)*2;
        int b  =rand.nextInt(4)*2;
        int c  =rand.nextInt(4)*2;
        int d = 0;
        n = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        return Matrix.orthogonal(rand, order, n);

    }


    @Override
    public String getProblemText() {
        return "Найти QR разложение для матрицы";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        return this.texExpression(
                String.format("%s", getMatrixValues(A))
        );
    }

    @Override
    public String getAnswerContent() throws Exception {
        return this.texExpression(
                String.format("Q = %s R = %s", getMatrixValues(Q), getMatrixValues(R))
        );
    }
}
