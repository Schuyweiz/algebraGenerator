package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.exception.MatrixProblemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixProblemFactory {

    public static MatrixProblem generateProblem(String type, int seed) throws Exception {

        MatrixProblem problem = null;
        switch (type){
            case "qr":
                problem =new  QRdecomposition(seed);
                break;
            case "orthdiag":
                problem = new OrthogonalDiag(seed);
                break;
            case "eigenvalues":
                problem = new FindEigenvalues(seed);
                break;
            case "inverse":
                problem = new InverseMatrix(seed);
                break;
            case "addsubmult":
                problem = new MatrixAddSubMul(seed);
                break;
            case "powern":
                problem = new MatrixPowerN(seed);
                break;
            case "rank":
                problem = new MatrixRank(seed);
                break;
            case "jordan":
                problem = new JordanCanonical(seed);
                break;
            case  "svd":
                problem = new SVDdecomposition(seed);
                break;
            case "rot":
                problem = new RotationAroundAxis(seed);
                break;
            case  "dimker":
                problem = new DimKer(seed);
                break;
            default:
                throw new MatrixProblemException("Unrecognized type of the problem.");
        }

        problem.generate();
        return problem;
    }

    public static MatrixProblem createRandomProblem(Random rand, int seed) throws Exception {
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
        return generateProblem(
                vars.get(rand.nextInt(vars.size())),
                seed
        );
    }



}
