package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.dto.Problem;
import com.schuyweiz.algebragenerator.utility.MatrixUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;

public class MatrixRank extends MatrixProblem {

    private final int width;
    private final int height;
    private final int rank;

    public MatrixRank(int seed)  {
        super(seed, "Найти ранг матрицы ");
        this.width = MatrixUtils.basedRandom(3,4,rand);
        this.height = MatrixUtils.basedRandom(3,2,rand);
        this.rank = MatrixUtils.basedRandom(1,height-1,rand);

    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var matrix = matrices[0];

        return TexUtils.getMatrixTex(matrix);
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var matrix = matrices[0];

        return TexUtils.getMatrixTex(matrix);
    }

    @Override
    public Problem generate() {
        var answerMatrix = Matrix.ofRank(width,height,rank,rand);
        var questionMatrix = answerMatrix.strongShuffle(rand,-2,2,1);

        return new Problem(
                this.problemText,
                getProblemQuestion(questionMatrix),
                getProblemAnswer(answerMatrix)
        );
    }
}
