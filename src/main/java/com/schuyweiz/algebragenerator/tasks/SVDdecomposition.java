package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.IntegerSym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SVDdecomposition extends MatrixProblem {

    //U^T A V = D


    public SVDdecomposition(int seed) {
        super(seed, "Найти сингулярное разложение следующей матрицы: ");
    }


    private Matrix createD(int nU, int nV) {
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

    private Matrix createOrthogonal(ArrayList<Integer> order, int n) {
        Collections.shuffle(order);

        return Matrix.orthogonal(rand, order, n);
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
        var argsU = createArgs();
        var argsV = createArgs();
        var nU = sumSquare.apply(argsU);
        var nV = sumSquare.apply(argsV);
        var U = createOrthogonal(argsU, nU);
        var V = createOrthogonal(argsV,nV);
        var D = createD(nU,nV);
        var A = U.mult(D).mult(V.transpose());

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer(A, U, D, V)
        );
    }

    private ArrayList<Integer> createArgs() {
        return IntStream.range(0, 4).map(i -> rand.nextInt(2) * 2 + i / 4).collect(ArrayList::new, List::add, List::addAll);
    }

    private final Function<ArrayList<Integer>, Integer> sumSquare = (list) ->
            list.stream().mapToInt(Integer::intValue).map(i -> i * i).sum();
}
