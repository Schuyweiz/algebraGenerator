package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.IntegerSym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SVDdecomposition extends MatrixProblem {

    private final int nU, aU, bU, cU, dU;
    private final int nV, aV, bV, cV, dV;
    //U^T A V = D


    public SVDdecomposition(int seed) {
        super(seed, "Найти сингулярное разложение следующей матрицы: ");

        aU = rand.nextInt(2) * 2 + 1;
        bU = rand.nextInt(2) * 2;
        cU = rand.nextInt(2) * 2;
        dU = rand.nextInt(2) * 2;
        nU = aU * aU + bU * bU + cU * cU + dU * dU;

        aV = rand.nextInt(2) * 2 + 1;
        bV = rand.nextInt(2) * 2;
        cV = rand.nextInt(2) * 2;
        dV = rand.nextInt(2) * 2;

        nV = aV * aV + bV * bV + cV * cV + dV * dV;


    }

    private Matrix createD() {
        var list = new ArrayList<>(
                List.of(
                        ExprUtils.getPositiveRandom(rand, 1, 3),
                        ExprUtils.getPositiveRandom(rand, 1, 3),
                        ExprUtils.getPositiveRandom(rand, 1, 3)
                )
        );
        return Matrix.diag(list).mult(Matrix.diag(new ArrayList<>(
                List.of(
                        IntegerSym.valueOf(nU * nV),
                        IntegerSym.valueOf(nU * nV),
                        IntegerSym.valueOf(nU * nV)
                )
        )));
    }

    private Matrix createU() {
        var order = new ArrayList<>(
                List.of(
                        aU, bU, cU, dU
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        return Matrix.orthogonal(rand, order, nU);
    }

    private Matrix createV() {
        var order = new ArrayList<>(
                List.of(
                        aV, bV, cV, dV
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        return Matrix.orthogonal(rand, order, nV);
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var A = matrices[0];

        return String.format("%s", TexUtils.getMatrixTex(A));
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var A = matrices[0];
        var U = matrices[1];
        var D = matrices[2];
        var V = matrices[3];

        return "Один из возможных ответов: " +
                String.format(
                        "A = %s = U \\times \\Sigma \\times V^{-1} = %s \\times %s \\times %s",
                        TexUtils.getMatrixTex(A),
                        TexUtils.getMatrixTex(U),
                        TexUtils.getMatrixTex(D),
                        TexUtils.getMatrixTex(V.transpose())
                );
    }

    @Override
    public Problem generate() {
        var U = createU();
        var V = createV();
        var D = createD();
        var A = U.mult(D).mult(V.transpose());

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer(A, U, D, V)
        );
    }
}
