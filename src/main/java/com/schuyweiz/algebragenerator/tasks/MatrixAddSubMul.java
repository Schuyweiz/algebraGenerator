package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import java.util.Random;

public class MatrixAddSubMul extends MatrixProblem{

    private final String sign;

    public MatrixAddSubMul(int randomSeed) {

        this.rand = new Random(randomSeed);
        String sign = generateSign();

        int height = rand.nextInt(2)+3;
        int width = rand.nextInt(2)+3;
        this.firstTerm = Matrix.randomMatrix(this.rand, -5,5,height,width);

        if (sign.equals("\\times")){
            int secondWidth = rand.nextInt(2)+2;
            secondTerm = Matrix.randomMatrix(this.rand, -5,5,width,secondWidth);
        }
        else
            this.secondTerm = Matrix.randomMatrix(this.rand, -5,5,height,width);

        if (sign.equals("+"))
            this.answer = firstTerm.add(secondTerm);
        if (sign.equals("-"))
            this.answer = firstTerm.sub(secondTerm);
        if (sign.equals("\\times"))
            this.answer = firstTerm.mult(secondTerm);

        this.sign = " " + sign + " ";
    }

    private String generateSign(){
        int value = rand.nextInt(3);
        return value==0? "+"
                : value == 1? "-"
                : "\\times";
    }

    @Override
    public String getProblemText() {
        return "Вычислить результат выражения: ";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        String firstTermString = getMatrixValues(this.firstTerm);
        String secondTermString = getMatrixValues(this.secondTerm);

        return texExpression(String.format("%s",firstTermString + sign + secondTermString));
    }

    @Override
    public String getAnswerContent() {
        return texExpression(getMatrixValues(answer));
    }


}
