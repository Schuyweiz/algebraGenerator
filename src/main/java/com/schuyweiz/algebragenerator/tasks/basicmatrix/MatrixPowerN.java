package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.Matrix;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P D P^-1

    public MatrixPowerN(int width, int seed){;
        this.rand = new Random(seed);
    }

    private Matrix createMatrixP(Matrix id) throws Exception {

        int operations = rand.nextInt(2) + 3;

        for (int i = 0; i < operations; i++) {
            int operation = rand.nextInt(3);

            if (operation == 0){
                int from = rand.nextInt(id.getHeight());
                int to = rand.nextInt(id.getHeight());

                id.swapRow(from,to);
            }

            if (operation == 1){
                int from = rand.nextInt(id.getHeight());
                int to = rand.nextInt(id.getHeight());
                int coef = rand.nextInt(2)+1;

                id.addRow(from,to,coef);
            }

            if (operation == 2){
                int coef = rand.nextInt(3) + 1;
                int to = rand.nextInt(id.getHeight());

                id.multRow(to, coef);
            }
        }
        return id;
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
    public String getAnswerContent() {
        return null;
    }
}
