package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.MatrixUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P Q P^-1
    private Matrix P;
    private Matrix invP;
    private Matrix Q;
    private Matrix Qn;
    private Matrix A;
    private final int width;

    public MatrixPowerN(int seed) throws Exception {
        super(seed, "Найти n-ую степень матрциы");
        this.width = MatrixUtils.basedRandom(3, 2, rand);
    }


    private Matrix createMatrixQ() {
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(ExprUtils.getPositiveRandom(rand, -3, 3));
        }
        return Matrix.diag(diagonal);
    }

    private Matrix createMatrixQn() {
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(F.Power(Q.get(i, i), F.Dummy("n")));
        }
        return Matrix.diag(diagonal);
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var A = matrices[0];

        return String.format("\\(%s^{%s}\\)", TexUtils.getMatrixTex(A), "n");

    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var Qn = matrices[0];

        return TexUtils.getMatrixTex(Qn);
    }

    @Override
    public Problem generate() {
        var P = Matrix.randDiag(width, rand);
        var invP = P.weakShuffle(rand, -2, 2);
        var Q = createMatrixQ();
        var Qn = createMatrixQn();
        var A = P.mult(Q).mult(invP);

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer(Qn)
        );
    }
}
