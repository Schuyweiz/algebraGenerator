package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import org.matheclipse.core.expression.IntegerSym;

import java.util.Random;

public class InverseMatrix extends MatrixProblem{

    private Matrix matrix;
    private boolean isInvertible =true;
    private final String initialMatrix;

    public InverseMatrix(int seed) throws Exception {
        this.rand = new Random(seed+1);
        this.matrix = Matrix.identity(rand.nextInt(2)+ 3);

        if (rand.nextInt(10)<3){
            isInvertible = false;
            transform(matrix);
        }
        initialMatrix = getMatrixValues(matrix);
        int commandsAmt = rand.nextInt(3)+3;
        commands = initCommands(commandsAmt,matrix.getHeight(),4);

        matrix = shuffleMatrix(commandsAmt,matrix);
    }

    private void transform(Matrix matrix) throws Exception {
        int size = matrix.getHeight();
        int term1 = rand.nextInt(size);
        int term2 = (term1+1)%size;
        int term3 = (term2+1)%size;

        int coef2 = rand.nextInt(3)+1;
        int coef3 = rand.nextInt(3)+1;

        matrix.multRow(term1,IntegerSym.valueOf(0));
        matrix.addRow(term2,term1,coef2);
        matrix.addRow(term2, term3, coef3);
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
        String matrixString = getMatrixValues(matrix);

        return String.format("\\(%s\\)",matrixString);
    }

    @Override
    public String getAnswerContent() throws Exception {
        return String.format("\\( %s invertible = %s \\)", initialMatrix, isInvertible );
    }
}
