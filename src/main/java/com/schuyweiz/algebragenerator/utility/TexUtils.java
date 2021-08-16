package com.schuyweiz.algebragenerator.utility;

import com.schuyweiz.algebragenerator.matrix.Matrix;

public class TexUtils {

    public static String getMatrixTex(Matrix matrix){
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

    public static String getTex(String expr){
        return String.format(
                "$$ %s $$", expr
        );
    }
}
