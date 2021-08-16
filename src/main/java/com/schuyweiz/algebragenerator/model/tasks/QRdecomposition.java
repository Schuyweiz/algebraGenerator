package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.dto.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QRdecomposition extends MatrixProblem {

    private final int n, a, b, c, d;

    public QRdecomposition(int seed) {
        super(seed, "Найти QR разложение для матрицы");
        a = rand.nextInt(2) * 2 + 1;
        b = rand.nextInt(2) * 2;
        c = rand.nextInt(2) * 2;
        d = rand.nextInt(2) * 2;

        n = a * a + b * b + c * c + d * d;
    }

    private Matrix createR(Matrix Q) {
        var rows = Q.getRows();
        ArrayList<Row> rowsR = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                row.add(IntegerSym.valueOf(0));
            }
            row.add(ExprUtils.getPositiveRandom(rand, 1, 4).times(IntegerSym.valueOf(n)));
            for (int j = i + 1; j < rows.get(i).getSize(); j++) {
                row.add(ExprUtils.getRandomNonNull(rand, -3, 3).times(IntegerSym.valueOf(n)));
            }
            rowsR.add(new Row(row));
        }
        return new Matrix(rowsR);
    }

    private Matrix createQ() {
        var order = new ArrayList<>(List.of(a, b, c, d));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        return Matrix.orthogonal(rand, order, n);

    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var A = matrices[0];

        return String.format("%s", TexUtils.getMatrixTex(A));
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var Q = matrices[0];
        var R = matrices[1];

        return String.format("Q = %s R = %s",
                TexUtils.getMatrixTex(Q),
                TexUtils.getMatrixTex(R));
    }

    @Override
    public Problem generate() {
        var Q = createQ();
        var R = createR(Q);
        var A = Q.mult(R);

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer(Q, R)
        );
    }
}
