package com.schuyweiz.algebragenerator.tasks;


import com.schuyweiz.algebragenerator.matrix.Matrix;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public abstract class MatrixProblem implements ProblemInterface {

        protected Matrix firstTerm;
        protected Matrix secondTerm;
        protected Matrix answer;
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



        protected String texExpression(String expr){
                return String.format(
                        "$$ %s $$", expr
                );
        }



}
