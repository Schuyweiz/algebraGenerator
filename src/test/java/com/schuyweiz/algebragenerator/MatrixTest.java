package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import org.junit.jupiter.api.Test;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
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
    void testcolop(){
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

}