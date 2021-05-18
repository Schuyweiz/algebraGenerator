package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class FindEigenvalues extends MatrixProblem{

    ArrayList<IExpr> eigenvalues;
    Matrix matrix;
    Matrix A;
    Matrix invA;
    private final String problemText = "Найдите все собственные значения матрицы: ";
    private final String answerText = "Собственные значения матрицы: ";


    public FindEigenvalues(int seed) throws Exception {
        this.rand = new Random(seed);
        initEigenvalues();
        this.matrix = Matrix.diag(3,eigenvalues);
        for (int i = 0; i < 1; i++) {
            A = Matrix.identity(3);
            invA = A.strongShuffle(rand,-3,3,1);
            matrix = invA.mult(matrix).mult(A);
        }
    }

    private void initEigenvalues(){
        ArrayList<IExpr> values =  new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            values.add(ExprUtils.getRandom(rand,-5,5));
        }
        eigenvalues = values;
    }


    @Override
    public String getProblemText() {
        return problemText;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

    @Override
    public String getProblemContent() {
        return texExpression(
                String.format("%s", getMatrixValues(matrix))
        );
    }

    //TODO: modify answers format
    @Override
    public String getAnswerContent() {
        return eigenvalues
                .stream()
                .map(val->val.toString())
                .collect(Collectors.joining(" ,"));
    }
}
