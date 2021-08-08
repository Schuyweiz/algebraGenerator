package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.MatrixUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;

public class DimKer extends MatrixProblem {

    private int rank;
    private static final int MAX_RANK = 4;
    private static final int SIZE = 4;

    public DimKer(int seed) {
        super(seed, "Найти ранг ядра и отображения матрицы перехода из А в В");
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        assert matrices.length!=2: "The argument of the getProblemQuestion should contain 2 arguments";

        var A = matrices[0];
        var B = matrices[1];

        return String.format("A = %s\nB = %s",
                        TexUtils.getMatrixTex(A),
                        TexUtils.getMatrixTex(B)
        );
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var X = matrices[0];

        return String.format(
                "%s\nРазмерность отображения = %s, Размерность ядра = %s",
                TexUtils.getTex("X = " + TexUtils.getMatrixTex(X)),
                rank,
                MAX_RANK - rank
        );
    }

    @Override
    public Problem generate() {
        //AX = B
        this.rank = MatrixUtils.basedRandom(1, 3, rand);
        Matrix A = Matrix.randDiag(SIZE, rand).strongShuffle(rand, -1, 2, 1);
        Matrix X = Matrix.ofRank(SIZE, SIZE, rank, rand);
        Matrix B = A.mult(X);

        return new Problem(
                this.problemText,
                getProblemQuestion(A, B),
                getProblemAnswer(X)
        );
    }
}
