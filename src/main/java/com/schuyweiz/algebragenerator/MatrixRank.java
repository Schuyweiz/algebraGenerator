package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixProblem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MatrixRank extends MatrixProblem {

    private Matrix matrix;
    private int rank;

    public MatrixRank(int seed){
        this.rand = new Random(seed);
        int width  = rand.nextInt(4)+2;
        int height = rand.nextInt(3)+2;

        createRankMatrix(width, height);

    }

    private void createRankMatrix(int width, int height){
        ArrayList<Row> rows = new ArrayList<>();
        int indent = 0;

        for (int i = 0; i < height; i++) {
            ArrayList<IExpr> exprs = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (j>=indent)
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
        return null;
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {
        return null;
    }

    @Override
    public String getAnswerContent() throws Exception {
        return null;
    }


}
