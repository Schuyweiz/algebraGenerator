package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindEigenvalues extends MatrixProblem {

    private static final int EIGENVALUES_AMOUNT = 3;
    private static final int R_BORDER = 5;
    private static final int L_BORDER = -5;
    private static final int A_SIZE = 3;
    private static final int R_SHUFFLE_LIM = 3;
    private static final int L_SHUFFLE_LIM = -3;
    private static final int CYCLES = 1;


    public FindEigenvalues(int seed) {
        super(seed, "Найдите все собственные значения матрицы: ");
    }

    private ArrayList<IExpr> initEigenvalues(int amount, int leftBorder, int rightBorder) {
        ArrayList<IExpr> values = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            values.add(ExprUtils.getRandom(rand, leftBorder, rightBorder));
        }

        return values;
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var A = matrices[0];
        return TexUtils.getTex(
                String.format("%s", TexUtils.getMatrixTex(A))
        );
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var D = matrices[0];
        return TexUtils.getTex(
                IntStream.range(0, D.getHeight())
                        .map(i -> D.get(i, i).toIntDefault())
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(" ,"))
        );
    }

    @Override
    public Problem generate() {
        ArrayList<IExpr> eigenvalues = initEigenvalues(EIGENVALUES_AMOUNT, L_BORDER, R_BORDER);
        var diagMatrix = Matrix.diag(EIGENVALUES_AMOUNT, eigenvalues);
        //A = P D invP
        var P = Matrix.identity(A_SIZE);
        var invP = P.strongShuffle(rand, L_SHUFFLE_LIM, R_SHUFFLE_LIM, CYCLES);
        var A = invP.mult(diagMatrix).mult(P);

        return new Problem(
                getProblemQuestion(A),
                getProblemAnswer()
        );
    }
}
