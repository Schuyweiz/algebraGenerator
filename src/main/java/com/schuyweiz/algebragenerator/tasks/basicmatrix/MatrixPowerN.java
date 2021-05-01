package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.Matrix;
import com.schuyweiz.algebragenerator.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import symjava.symbolic.Expr;
import symjava.symbolic.Pow;
import symjava.symbolic.Symbol;

import java.util.*;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P Q P^-1
    private Matrix P;
    private Matrix invP;
    private Matrix Q;
    private Matrix Qn;
    private Matrix A;
    private final int width;
    private final ArrayList<ArrayList<Integer>> opOrder = new ArrayList<>();

    public MatrixPowerN(int width, int seed) throws Exception {
        this.width = width;
        this.rand = new Random(seed);
        this.P = Matrix.identity(width);
        this.invP = Matrix.identity(width);
        createMatrixP();
        createMatrixQ();
        createMatrixA();
        createMatrixQn();
    }

    private void createMatrixQ(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(ExprUtils.getRandom(rand,-3,3));
        }
        this.Q = Matrix.diag(width,diagonal);
    }

    private void createMatrixQn(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(F.Power(Q.get(i,i),F.Dummy("n")));
        }
        this.Qn = Matrix.diag(width,diagonal);
    }

    private void createMatrixA() throws Exception {
        this.A = this.P.mult(Q).mult(invP);
    }

    private void createMatrixP() throws Exception {

        int operations = rand.nextInt(2) + 3;

        for (int i = 0; i < operations; i++) {
            int operation = rand.nextInt(3);
            operationOnP(operation);
        }

        for (int i = 0; i < operations; i++) {
            operationOnInvP(opOrder.get(i));
        }

    }

    private void operationOnP(int operation) throws Exception {
        ArrayList<Integer> op = new ArrayList<>();
        op.add(operation);
        if (operation == 0){
            int from = rand.nextInt(P.getHeight());
            int to = rand.nextInt(P.getHeight());
            op.add(from);
            op.add(to);

            P.swapRow(from,to);
        }

        if (operation == 1){
            int from = rand.nextInt(P.getHeight());
            int to = rand.nextInt(P.getHeight());
            int coef = rand.nextInt(2)+1;

            op.add(from);
            op.add(to);
            op.add(coef);
            if(from == to){
                P.multRow(from,coef+1);
            }else{
                P.addRow(from,to,coef);
            }
        }

        if (operation == 2){
            int coef = rand.nextInt(3) + 1;
            int to = rand.nextInt(P.getHeight());

            op.add(to);
            op.add(coef);
            P.multRow(to, coef);
        }
        opOrder.add(op);
    }

    private void operationOnInvP(ArrayList<Integer> op) throws Exception {
        int operation = op.get(0);
        Matrix id = Matrix.identity(width);
        if (operation == 0){
            int from = op.get(1);
            int to = op.get(2);

            id.swapRow(from,to);
        }

        if (operation == 1){
            int from = op.get(1);
            int to = op.get(2);
            int coef = op.get(3);


            if(from == to){
                id.divRow(from,coef+1);
            }else{
                id.addRow(from,to,-coef);
            }
        }

        if (operation == 2){
            int to = op.get(1);
            int coef =op.get(2);

            id.divRow(to, coef);
        }
        invP = invP.mult(id);

    }

    @Override
    public String getProblemText() {
        return "Найти n-ую степень матрциы";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {

        String aString = getMatrixValues(this.A);

        return String.format("\\(%s^{%s}\\)",aString,"n");
    }

    @Override
    public String getAnswerContent() throws Exception {
        Matrix m = this.P.mult(this.Qn).mult(this.invP);
        String pInvString = getMatrixValues(m);

        return String.format("\\(%s\\)",pInvString);

    }
}
