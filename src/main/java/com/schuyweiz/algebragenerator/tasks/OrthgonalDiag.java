package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OrthgonalDiag extends MatrixProblem {

    private String problemText = "Найти ортогональное преобразование для приведения \n" +
            "к диагональному виду и вычислить диагональный вид:";
    private String answerText = "";

    private Matrix T;
    private Matrix invT;
    private Matrix D;
    private Matrix A;
    private int n;

    public OrthgonalDiag(int seed){
        this.rand = new Random(seed);
        this.T = createT();
        this.invT = T.transpose();
        this.D = createD();
        this.A = createA();
    }

    private Matrix createT(){
        int a  = rand.nextInt(2)*2+1;
        int b  =rand.nextInt(2)*2;
        int c  =rand.nextInt(2)*2;
        int d = rand.nextInt(2)*2;
        n = a*a+ b*b + c*c + d*d;

        var order = new ArrayList<Integer>(
                List.of(
                        a,b,c,d
                ));
        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        return Matrix.orthogonal(rand, order,n);
    }

    private Matrix createD(){
        var diag = new ArrayList<>(List.of(
                ExprUtils.getRandom(rand, -5,5).times(IntegerSym.valueOf(n*n)),
                ExprUtils.getRandom(rand,-5,5).times(IntegerSym.valueOf(n*n)),
                ExprUtils.getRandom(rand,-5,5).times(IntegerSym.valueOf(n*n))
        ));
        return Matrix.diag(3,diag);
    }

    private Matrix createA(){
        return this.T.mult(this.D).mult(this.invT);
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
                String.format(
                       "%s", getMatrixValues(A)
                )
        );
    }

    @Override
    public String getAnswerContent() {
        return texExpression(
                String.format(
                        "%s \\times %s \\times %s  = %s",
                        getMatrixValues(T),
                        getMatrixValues(D),
                        getMatrixValues(invT),
                        getMatrixValues(T.mult(D).mult(invT))
                )
        );
    }
}
