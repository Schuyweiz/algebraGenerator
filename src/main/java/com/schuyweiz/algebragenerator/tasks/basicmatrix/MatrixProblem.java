package com.schuyweiz.algebragenerator.tasks.basicmatrix;


import com.schuyweiz.algebragenerator.Matrix;
import com.schuyweiz.algebragenerator.Row;
import org.ejml.simple.SimpleMatrix;

public abstract class MatrixProblem implements ProblemInterface {

        protected Matrix firstTerm;
        protected Matrix secondTerm;
        protected Matrix answer;
        protected String problemText;
        protected String answerText;


        protected String getMatrixValues(Matrix matrix){
                int rows = matrix.getHeight();
                StringBuilder sb = new StringBuilder();
                sb.append("\\(\\begin{pmatrix}\n");
                for (int i=0;i<rows-1;i++){
                        sb.append(matrix.getRows().get(i).toString())
                                .append("\\\\\n");
                }
                sb.append(matrix.getRows().get(rows-1))
                        .append("\n")
                        .append("\\end{pmatrix}\\)");
                return sb.toString();
        }

}
