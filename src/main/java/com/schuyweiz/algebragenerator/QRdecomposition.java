package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixProblem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.hipparchus.fraction.Fraction;
import org.matheclipse.core.expression.FractionSym;
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

        int a  ;
        int b  ;
        int c ;
        int d;

        var order = new ArrayList<Integer>(
                List.of(
                rand.nextInt(4)*2,
                rand.nextInt(4)*2,
                rand.nextInt(4)*2-1,0));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        a = order.get(0);
        b = order.get(1);
        c = order.get(2);
        d = order.get(3);

        n = a*a + b*b + c*c + d*d;
        ArrayList<Row> rows = new ArrayList<Row>();
        ArrayList<IExpr> row1 = new ArrayList<>();
        row1.add(FractionSym.valueOf(a*a+b*b-c*c-d*d,n));
        row1.add(FractionSym.valueOf(2*(-a*d+b*c),n));
        row1.add(FractionSym.valueOf(2*(a*c+b*d),n));


        ArrayList<IExpr> row2 = new ArrayList<>();
        row2.add(FractionSym.valueOf(2*(a*d+b*c),n));
        row2.add(FractionSym.valueOf(a*a-b*b+c*c-d*d,n));
        row2.add(FractionSym.valueOf(2*(-a*b + c*d),n));

        ArrayList<IExpr> row3 = new ArrayList<>();
        row3.add(FractionSym.valueOf(2*(-a*c+b*d),n));
        row3.add(FractionSym.valueOf(2*(a*b+c*d),n));
        row3.add(FractionSym.valueOf(a*a-b*b-c*c+d*d,n));

        rows.add(new Row(row1));
        rows.add(new Row(row2));
        rows.add(new Row(row3));


        return new Matrix(rows);

    }


    @Override
    public String getProblemText() {
        return null;
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
