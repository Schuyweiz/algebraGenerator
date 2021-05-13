package com.schuyweiz.algebragenerator.tasks;

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
            case  "svd":
                return new SVDdecomposition(seed);
            case "rot":
                return new RotationAroundAxis(seed);
        }
        return null;
    }



}
