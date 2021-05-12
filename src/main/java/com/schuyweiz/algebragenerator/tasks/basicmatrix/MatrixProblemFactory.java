package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.JordanCanonical;
import com.schuyweiz.algebragenerator.OrthgonalDiag;
import com.schuyweiz.algebragenerator.QRdecomposition;

public class MatrixProblemFactory {

    public static MatrixProblem typeof(String type, int seed) throws Exception {

        switch (type){
            case "qr":
                return new QRdecomposition(seed);
            case "orthdiag":
                return new OrthgonalDiag(seed);
            case "eigenvalues":
                return new FindEigenvalues(seed);
            case "inverse":
                return new InverseMatrix(seed);
            case "addsubmult":
                return new MatrixAddSubMul(seed);
            case "powern":
                return new MatrixPowerN(seed);
            case "rank":
                return new MatrixRank(seed);
            case "jordan":
                return new JordanCanonical(seed);
        }
        return null;
    }



}
