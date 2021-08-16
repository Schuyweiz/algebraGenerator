package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.dto.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.expression.IntegerSym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrthogonalDiag extends MatrixProblem {

    private final int n,a,b,c,d;

    public OrthogonalDiag(int seed){
        super(seed, "Найти ортогональное преобразование для приведения \n" +
                "к диагональному виду и вычислить диагональный вид:");
        a  = rand.nextInt(2)*2+1;
        b  =rand.nextInt(2)*2;
        c  =rand.nextInt(2)*2;
        d = rand.nextInt(2)*2;
        n = a*a+ b*b + c*c + d*d;
    }

    private Matrix createT(){
        var order = new ArrayList<>(List.of(a,b,c,d));
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
        return Matrix.diag(diag);
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var matrix = matrices[0];

        return String.format("%s",
                TexUtils.getMatrixTex(matrix));
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var T = matrices[0];
        var D = matrices[1];
        var invT = matrices[2];
        var A = matrices[3];

        return String.format(
                        "%s \\times %s \\times %s  = %s",
                        TexUtils.getMatrixTex(T),
                        TexUtils.getMatrixTex(D),
                        TexUtils.getMatrixTex(invT),
                        TexUtils.getMatrixTex(A)
        );

    }

    @Override
    public Problem generate() {
        var T = createT();
        var invT = T.transpose();
        var D = createD();
        var A = T.mult(D).mult(invT);

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer(T,D,invT,A)
        );
    }
}
