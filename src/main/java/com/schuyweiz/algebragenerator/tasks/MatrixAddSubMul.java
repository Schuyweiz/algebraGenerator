package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import com.schuyweiz.algebragenerator.utility.MatrixUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import jdk.jshell.execution.Util;

import java.util.Random;

public class MatrixAddSubMul extends MatrixProblem{

    private final String sign;
    private final int width;
    private final int height;

    public MatrixAddSubMul(int randomSeed) {
        super(randomSeed,"Вычислить результат выражения: ");
        sign = generateSign();
        height = MatrixUtils.basedRandom(2,3,rand);
        width = MatrixUtils.basedRandom(2,3,rand);
    }

    private String generateSign(){
        int value = rand.nextInt(3);
        return value==0? "+"
                : value == 1? "-"
                : "\\times";
    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var first = matrices[0];
        var second = matrices[1];
        return TexUtils.getTex(
                String.format("%s %s %s",
                        TexUtils.getMatrixTex(first),
                        sign,
                        TexUtils.getMatrixTex(second))
        );
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        var answer = matrices[0];
        return TexUtils.getTex(
                TexUtils.getMatrixTex(answer)
        );
    }

    @Override
    public Problem generate() {
        var firstTerm = Matrix.randomMatrix(this.rand, -5,5,height,width);
        var secondTerm = getSecondTerm();
        var result = getResult(firstTerm,secondTerm);

        return new Problem(
                getProblemQuestion(firstTerm,secondTerm),
                getProblemAnswer(result)
        );
    }

    private Matrix getResult(Matrix firstTerm, Matrix secondTerm) {
        Matrix answer = null;
        if (sign.trim().equals("+"))
            answer = firstTerm.add(secondTerm);
        if (sign.trim().equals("-"))
            answer = firstTerm.sub(secondTerm);
        if (sign.trim().equals("\\times"))
            answer = firstTerm.mult(secondTerm);

        return answer;
    }

    private Matrix getSecondTerm(){
        if (sign.trim().equals("\\times")){
            int secondWidth = rand.nextInt(2)+2;
            return Matrix.randomMatrix(this.rand, -5,5,width,secondWidth);
        }
        else
            return Matrix.randomMatrix(this.rand, -5,5,height,width);
    }
}
