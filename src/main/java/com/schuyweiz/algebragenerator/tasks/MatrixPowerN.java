package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.*;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P Q P^-1
    private Matrix P;
    private Matrix invP;
    private Matrix Q;
    private Matrix Qn;
    private Matrix A;
    private final int width;

    public MatrixPowerN(int seed) throws Exception {
        this.rand = new Random(seed);
        this.width = rand.nextInt(2)+3;
        this.P = Matrix.randDiag(width,rand);
        this.invP = P.weakShuffle(rand,-2,2);
        createMatrixQ();
        createMatrixA();
        createMatrixQn();
    }



    private void createMatrixQ(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(ExprUtils.getPositiveRandom(rand,-3,3));
        }
        this.Q = Matrix.diag(width,diagonal);
    }

    private void createMatrixQn(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(F.Power(Q.get(i,i),F.Dummy("n")));
        }
        this.Qn = Matrix.diag(width,diagonal);
    }

    private void createMatrixA() throws Exception {
        this.A = this.P.mult(Q).mult(invP);
    }

    @Override
    public String getProblemText() {
        return "Найти n-ую степень матрциы";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {

        String aString = getMatrixValues(this.A);

        return String.format("\\(%s^{%s}\\)",aString,"n");
    }

    @Override
    public String getAnswerContent()  {

        return texExpression(getMatrixValues(Qn));

    }
}
