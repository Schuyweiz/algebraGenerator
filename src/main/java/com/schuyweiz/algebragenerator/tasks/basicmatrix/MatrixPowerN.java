package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.Matrix;
import com.schuyweiz.algebragenerator.Number;
import org.ejml.simple.SimpleMatrix;

import java.util.*;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P D P^-1
    private Matrix P;
    private Matrix invP;
    private Matrix D;
    private int width;
    private ArrayList<ArrayList<Integer>> opOrder = new ArrayList<>();

    public MatrixPowerN(int width, int seed) throws Exception {
        this.width = width;
        this.rand = new Random(seed);
        this.P = Matrix.identity(width);
        this.invP = Matrix.identity(width);
        createMatrixP();
    }

    private void createMatrixQ(){
        ArrayList<Number> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(Number.getRandom(rand,-3,3));
        }
        this.D = Matrix.diag(width,diagonal);
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
            int coef =op.get(1);
            int to = op.get(2);

            id.divRow(to, coef);
        }
        invP = invP.mult(id);

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
        String firstTermString = getMatrixValues(this.P);
        String secondTermString = getMatrixValues(this.invP);

        return firstTermString + " * " + secondTermString;
    }

    @Override
    public String getAnswerContent() {
        return null;
    }
}
