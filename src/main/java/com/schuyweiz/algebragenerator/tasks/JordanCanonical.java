package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Random;

public class JordanCanonical extends MatrixProblem {

    private final int size;
    private final Matrix jordan;
    private final Matrix problemMatrix;

    public JordanCanonical(int seed) {
        this.rand = new Random(seed);
        size = 4;
        jordan = generateJordan();
        Matrix A = Matrix.identity(size);
        Matrix invA = A.strongShuffle(rand, -3, 3);
        problemMatrix = A.mult(jordan).mult(invA);
    }


    private Matrix generateJordan() {
        Matrix jordan;

        ArrayList<IExpr> eigenvalues = new ArrayList<>();
        IExpr currentVal = ExprUtils.getRandomNonNull(rand, -5, 5);
        int i = 0;
        for (i = 0; i < size; i++) {
            if (rand.nextInt(3) == 2) {
                break;
            }
            eigenvalues.add(currentVal);
        }

        currentVal = ExprUtils.getRandomNonNull(rand, -5, 5);
        for (; i < size; i++) {
            eigenvalues.add(currentVal);
        }
        ArrayList<Row> rows = new ArrayList<Row>();
        for (int j = 0; j < size; j++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int k = 0; k < size; k++) {
                if (k == j)
                    row.add(eigenvalues.get(j));
                else
                    row.add(IntegerSym.valueOf(0));
            }
            rows.add(new Row(row));
        }

        for (int j = 0; j < size - 1; j++) {
            if (rows.get(j).get(j).equals(rows.get(j + 1).get(j + 1))) {
                if (rand.nextBoolean()) {
                    rows.get(j).set(j+1, IntegerSym.valueOf(1));
                }
            }
        }

        jordan = new Matrix(rows);
        return jordan;
    }

    @Override
    public String getProblemText() {
        return "Применить алгоритм нахождения жордановой нормальной матрицы: ";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        return this.texExpression(
                String.format("%s", getMatrixValues(problemMatrix))
        );
    }

    @Override
    public String getAnswerContent() {
        return this.texExpression(
                String.format("%s", getMatrixValues(jordan))
        );
    }
}
