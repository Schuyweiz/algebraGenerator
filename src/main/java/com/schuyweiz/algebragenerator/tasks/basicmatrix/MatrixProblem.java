package com.schuyweiz.algebragenerator.tasks.basicmatrix;


import com.schuyweiz.algebragenerator.ElementaryCommand;
import com.schuyweiz.algebragenerator.matrix.Matrix;

import java.util.ArrayList;
import java.util.Random;

public abstract class MatrixProblem implements ProblemInterface {

        protected Matrix firstTerm;
        protected Matrix secondTerm;
        protected Matrix answer;
        protected String problemText;
        protected String answerText;
        protected Random rand;


        protected String getMatrixValues(Matrix matrix){
                int rows = matrix.getHeight();
                StringBuilder sb = new StringBuilder();
                sb.append("\\begin{pmatrix}\n");
                for (int i=0;i<rows-1;i++){
                        sb.append(matrix.getRows().get(i).toString())
                                .append("\\\\\n");
                }
                sb.append(matrix.getRows().get(rows-1))
                        .append("\n")
                        .append("\\end{pmatrix}");
                return sb.toString();
        }

        protected Matrix elementaryOperation(ElementaryCommand command, Matrix matrix) throws Exception {
                if (command.getType() == 0){
                        int from = command.getFrom();
                        int to = command.getTo();
                        matrix.swapRow(from,to);
                }
                if (command.getType() == 1){
                        int from = command.getFrom();
                        int to = command.getTo();
                        int coef = command.getCoef();

                        if(from == to){
                                matrix.multRow(from,coef+1);
                        }else{
                                matrix.addRow(from,to,coef);
                        }
                }

                if (command.getType() == 2){
                        int coef = command.getCoef();
                        int to = command.getTo();

                        matrix.multRow(to, coef);
                }
                return matrix;
        }

        protected Matrix elementaryOperationReverse(ElementaryCommand command, Matrix matrix) throws Exception {
                Matrix id = Matrix.identity(matrix.getHeight());
                if (command.getType() == 0){
                        int from = command.getFrom();
                        int to = command.getTo();

                        id.swapRow(from,to);
                }

                if (command.getType() == 1){
                        int from = command.getFrom();
                        int to = command.getTo();
                        int coef = command.getCoef();

                        if(from == to){
                                id.divRow(from,coef+1);
                        }else{
                                id.addRow(from,to,-coef);
                        }
                }

                if (command.getType() == 2){
                        int coef = command.getCoef();
                        int to = command.getTo();

                        id.divRow(to, coef);
                }
                return matrix.mult(id);

        }


}
