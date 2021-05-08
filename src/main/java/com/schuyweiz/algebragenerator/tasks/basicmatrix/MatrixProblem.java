package com.schuyweiz.algebragenerator.tasks.basicmatrix;


import com.schuyweiz.algebragenerator.ElementaryCommand;
import com.schuyweiz.algebragenerator.matrix.Matrix;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.reflection.system.In;

import java.util.ArrayList;
import java.util.Random;

public abstract class MatrixProblem implements ProblemInterface {

        protected Matrix firstTerm;
        protected Matrix secondTerm;
        protected Matrix answer;
        protected String problemText;
        protected String answerText;
        protected Random rand;
        protected ArrayList<ElementaryCommand> commands;

        protected Matrix shuffleMatrix(int times, Matrix matrix) throws Exception {
                for (int i = 0; i < times; i++) {
                        matrix = this.elementaryOperation(commands.get(i), matrix );
                }
                return matrix;
        }


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
                                matrix.multRow(from, IntegerSym.valueOf(coef+1));
                        }else{
                                matrix.addRow(from,to,IntegerSym.valueOf(coef));
                        }
                }

                if (command.getType() == 2){
                        int coef = command.getCoef();
                        int to = command.getTo();

                        matrix.multRow(to, IntegerSym.valueOf(coef));
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
                                id.divRow(from,IntegerSym.valueOf(coef+1));
                        }else{
                                id.addRow(from,to,IntegerSym.valueOf(-coef));
                        }
                }

                if (command.getType() == 2){
                        int coef = command.getCoef();
                        int to = command.getTo();

                        id.divRow(to, IntegerSym.valueOf(coef));
                }
                return matrix.mult(id);

        }

        protected ArrayList<ElementaryCommand> initCommands(int amount, int sizeLimit, int coefLimit, Integer... types){
                ArrayList<ElementaryCommand> commands = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                        int from = rand.nextInt(sizeLimit);
                        int to = rand.nextInt(sizeLimit);
                        int coef = rand.nextInt(coefLimit) + 1;
                        int typeId = rand.nextInt(types.length);
                        int type = types[typeId];
                        commands.add(new ElementaryCommand(type,from, to, coef));
                }

                return commands;
        }

        protected String texExpression(String expr){
                return String.format("\\( %s \\)", expr);
        }


}
