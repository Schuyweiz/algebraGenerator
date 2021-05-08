package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import org.matheclipse.core.expression.IntegerSym;

import java.util.Random;

public class InverseMatrix extends MatrixProblem{

    private Matrix matrix;
    private Matrix inverse;
    private boolean isInvertible =true;

    public InverseMatrix(int seed) throws Exception {
        this.rand = new Random(seed+1);
        this.matrix = Matrix.identity(3);

        inverse = matrix.strongShuffle(rand,-3,3);
    }

    private void transform(Matrix matrix) throws Exception {
        int size = matrix.getHeight();
        int term1 = rand.nextInt(size);
        int term2 = (term1+1)%size;
        int term3 = (term2+1)%size;

        int coef2 = rand.nextInt(3)+1;
        int coef3 = rand.nextInt(3)+1;

        matrix.multRow(term1,IntegerSym.valueOf(0));
        matrix.addRow(term2,term1,IntegerSym.valueOf(coef2));
        matrix.addRow(term2, term3, IntegerSym.valueOf(coef3));
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
        String reverseString = getMatrixValues(this.inverse);

        return String.format("\\(%s %s\\)",matrixString, reverseString);
    }

    @Override
    public String getAnswerContent() throws Exception {
        return String.format("\\( %s invertible = %s \\)", getMatrixValues(matrix.mult(inverse)), isInvertible );
    }
}
