package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import java.util.Random;

public class MatrixAddSubMul extends MatrixProblem{

    private final String sign;

    public MatrixAddSubMul(int randomSeed, int rows, int cols, String sign) throws Exception {

        this.rand = new Random(randomSeed);

        this.firstTerm = Matrix.randomMatrix(this.rand, -5,5,3,3);
        this.secondTerm = Matrix.randomMatrix(this.rand, -5,5,3,3);

        if (sign.equals("+"))
            this.answer = firstTerm.add(secondTerm);
        if (sign.equals("-"))
            this.answer = firstTerm.sub(secondTerm);
        if (sign.equals("*"))
            this.answer = firstTerm.mult(secondTerm);

        this.sign = " " + sign + " ";

    }

    @Override
    public String getProblemText() {
        return null;
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        String firstTermString = getMatrixValues(this.firstTerm);
        String secondTermString = getMatrixValues(this.secondTerm);

        return String.format("\\(%s\\)",firstTermString + sign + secondTermString);
    }

    @Override
    public String getAnswerContent() {
        return String.format("\\(%s\\)", getProblemContent() + " = " + getMatrixValues(answer));
    }


}
