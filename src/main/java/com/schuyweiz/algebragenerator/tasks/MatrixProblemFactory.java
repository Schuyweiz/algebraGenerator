package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            case  "dimker":
                return new DimKer(seed);
        }
        return null;
    }

    public static MatrixProblem getRandomProblem(Random rand, int seed) throws Exception {
        ArrayList<String> vars = new ArrayList<>(
                List.of(
                        "qr",
                        "orthdiag",
                        "eigenvalues",
                        "inverse",
                        "addsubmult",
                        "powern",
                        "rank",
                        "jordan",
                        "svd",
                        "rot",
                        "dimker"
                )
        );
        return typeof(
                vars.get(rand.nextInt(vars.size())),
                seed
        );
    }



}
