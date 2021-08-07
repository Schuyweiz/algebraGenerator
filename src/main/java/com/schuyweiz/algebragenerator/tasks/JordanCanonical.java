package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JordanCanonical extends MatrixProblem {

    private static final int SIZE = 4;
    private final int size;

    public JordanCanonical(int seed) {
        super(seed, "Применить алгоритм нахождения жордановой нормальной матрицы: ");
        size = 4;
    }


    private Matrix generateJordan() {

        ArrayList<IExpr> eigenvalues = new ArrayList<>();
        IExpr currentVal = ExprUtils.getRandomNonNull(rand, -5, 5);
        boolean firstChange = true;

        for (int i = 0; i < size; i++) {
            if (firstChange && rand.nextInt(3) == 2) {
                currentVal = ExprUtils.getRandomNonNull(rand, -5, 5);
                firstChange = false;
            }
            eigenvalues.add(currentVal);
        }

        var rows =  Matrix.diag(eigenvalues).getRows();

        for (int j = 0; j < size - 1; j++) {
            if (rows.get(j).get(j).equals(rows.get(j + 1).get(j + 1))) {
                if (rand.nextBoolean()) {
                    rows.get(j).set(j + 1, IntegerSym.valueOf(1));
                }
            }
        }

        return new Matrix(rows);
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var problemMatrix = matrices[0];
        return TexUtils.getTex(
                String.format("%s", TexUtils.getMatrixTex(problemMatrix))
        );
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var jordan = matrices[0];
        return TexUtils.getTex(
                String.format("%s", TexUtils.getMatrixTex(jordan))
        );
    }

    @Override
    public Problem generate() {
        var jordanMatrix = generateJordan();
        var A = Matrix.identity(size);
        var invA = A.strongShuffle(rand,-2,2,1);
        var problemMatrix = A.mult(jordanMatrix).mult(invA);

        return new Problem(
                getProblemQuestion(problemMatrix),
                getProblemAnswer(jordanMatrix)
        );
    }
}
