package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class FindEigenvalues extends MatrixProblem{

    ArrayList<IExpr> eigenvalues;
    Matrix matrix;


    public FindEigenvalues(int seed) throws Exception {
        this.rand = new Random(seed);
        initEigenvalues();
        this.matrix = Matrix.diag(3,eigenvalues);
        int shuffleTimes = rand.nextInt(3)+1;
        for (int i = 0; i < 3; i++) {
            eigenvaluesShuffle();
        }
    }

    private void eigenvaluesShuffle() throws Exception {
        Matrix A = Matrix.identity(3);
        this.commands = initCommands(5,3,4,0,1,2);
        for (int i = 0; i < commands.size(); i++) {
            A = this.elementaryOperation(commands.get(i),A);
        }
        Matrix invA = Matrix.identity(3);
        for (int i = 0; i < commands.size(); i++) {
            invA = this.elementaryOperationReverse(commands.get(i),invA);
        }
        matrix = invA.mult(matrix).mult(A);
    }

    private void initEigenvalues(){
        ArrayList<IExpr> values =  new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            values.add(ExprUtils.getRandom(rand,-5,5));
        }
        eigenvalues = values;
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
        return texExpression(
                String.format("%s", getMatrixValues(matrix))
        );
    }

    @Override
    public String getAnswerContent() throws Exception {
        return eigenvalues.stream().map(val->val.toString()).collect(Collectors.joining(" ,")).toString();
    }
}
