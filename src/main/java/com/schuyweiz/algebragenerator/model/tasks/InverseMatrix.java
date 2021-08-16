package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.dto.Problem;
import com.schuyweiz.algebragenerator.utility.MatrixUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;

import java.util.Random;

public class InverseMatrix extends MatrixProblem {

    private static final int WIDTH = 3;
    private static final int CYCLES = 3;
    private static final int L_SHUFFLE_BORDER = -2;
    private static final int R_SHUFFLE_BORDER = 2;
    private static final String NON_INVERTIBLE_TEXT = "Матрица не обратима";

    public InverseMatrix(int seed) {
        super(seed, "Найти обратную матрицу");
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var matrix = matrices[0];

        return TexUtils.getMatrixTex(matrix);
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var inverse = matrices[0];

        return TexUtils.getMatrixTex(inverse);
    }

    @Override
    public Problem generate() {
        boolean isInvertible = rand.nextBoolean();
        var matrix = getRandomMatrix(rand, isInvertible);
        var invMatrix = matrix.strongShuffle(rand, L_SHUFFLE_BORDER, R_SHUFFLE_BORDER, CYCLES);

        return new Problem(
                this.problemText,
                getProblemQuestion(matrix),
                isInvertible ? getProblemQuestion(invMatrix) : NON_INVERTIBLE_TEXT
        );
    }

    private Matrix getRandomMatrix(Random rand, boolean isInvertible) {
        if (isInvertible) {
            return Matrix.identity(WIDTH);
        } else {
            int rank = MatrixUtils.basedRandom(1, WIDTH - 1, rand);
            return Matrix.ofRank(WIDTH, rank, rand);
        }
    }
}
