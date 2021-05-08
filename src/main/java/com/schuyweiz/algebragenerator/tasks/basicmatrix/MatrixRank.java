package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Random;

public class MatrixRank extends MatrixProblem {



    public MatrixRank(int seed) throws Exception {
        this.rand = new Random(seed);
        int width  = rand.nextInt(4)+2;
        int height = rand.nextInt(3)+2;
        rank = rand.nextInt(height-1)+2;

        createRankMatrix(width, height);
        initialMatrix = getMatrixValues(matrix);
        int commandsAmt = rand.nextInt(3)+4;
        this.commands = initCommands(commandsAmt,height,4);
        this.matrix = shuffleMatrix(commandsAmt, matrix);
    }

    private void createRankMatrix(int width, int height){
        ArrayList<Row> rows = new ArrayList<>();
        int indent = 0;

        for (int i = 0; i < height; i++) {
            ArrayList<IExpr> exprs = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (j>=indent && i<rank)
                    exprs.add(ExprUtils.getRandomNonNull(rand,1,5));
                else
                    exprs.add(IntegerSym.valueOf(0));
            }
            rows.add(new Row(exprs));
            indent += (rand.nextInt(2)+1);
        }

        this.matrix = new Matrix(rows);
    }


    @Override
    public String getProblemText() {
        return "Найти ранг матрицы ";
    }

    @Override
    public String getAnswerText() {
        return "Ранг матрицы равен ";
    }

    @Override
    public String getProblemContent() {
        String matrixString = getMatrixValues(matrix);
        return String.format("\\(%s\\)",matrixString);
    }

    @Override
    public String getAnswerContent() throws Exception {
        return String.format("\\( %s rank = %s \\)", initialMatrix, rank);
    }

    private Matrix matrix;
    private final String initialMatrix;
    private final int rank;

}
