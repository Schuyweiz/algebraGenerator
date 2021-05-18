package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import org.junit.jupiter.api.Test;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.FractionSym;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MatrixTest {

    @Test
    void testmatrix() throws Exception {
        ArrayList<Row> rows = new ArrayList<Row>();

        for (int i = 0; i < 3; i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(IntegerSym.valueOf(i+j));
            }
            rows.add(new Row(row));
        }

        Matrix matrix = new Matrix(rows);
        Matrix matrix1 = new Matrix(rows);

        var sum = matrix.add(matrix1);
        var prod = matrix.mult(matrix1);
        matrix.addRow(0,1,IntegerSym.valueOf(2));
        matrix.multRow(0,IntegerSym.valueOf(2));
        matrix.swapRow(0,2);


    }

    @Test
    void testcolop() throws Exception {
        ArrayList<Row> rows = new ArrayList<Row>();

        for (int i = 0; i < 3; i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(IntegerSym.valueOf(i+j));
            }
            rows.add(new Row(row));
        }

        Matrix matrix = new Matrix(rows);


    }

    @Test
    void testShuffle() throws Exception {
        Matrix m = Matrix.identity(3);
        var var = m.strongShuffle(new Random(2),-3,3,1);
        System.out.println(var.mult(m));
        System.out.println(var);
        System.out.println(m);


    }

    @Test
    void rotationMatrixFormula(){
        var matr = initA();
        System.out.println(matr.toString());
    }

    private Matrix initA(){
        var arg = F.Dummy("theta");
        var x = F.Dummy("x");
        var y = F.Dummy("y");
        var z = F.Dummy("z");
        ExprEvaluator util = new ExprEvaluator(false, (short) 100);
        var cos = util.eval(F.Cos(F.Pi.$times(arg)));
        var sin = util.eval(F.Sin(F.Pi.$times(arg)));

        ArrayList<IExpr> row1 = new ArrayList<>(
                List.of(
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(x.times(x))),
                        x.times(y).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(z)),
                        x.times(z).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(y))
                )
        );

        ArrayList<IExpr> row2 = new ArrayList<>(
                List.of(
                        x.times(y).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(z)),
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(y.times(y))),
                        y.times(z).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(x))
                )
        );

        ArrayList<IExpr> row3 = new ArrayList<>(
                List.of(
                        x.times(z).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(y)),
                        y.times(z).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(x)),
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(z.times(z)))
                )
        );


        return new Matrix(new ArrayList<Row>(
                List.of(
                        new Row(row1),
                        new Row(row2),
                        new Row(row3)
                )
        ));

    }


}