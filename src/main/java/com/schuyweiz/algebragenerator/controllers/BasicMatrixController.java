package com.schuyweiz.algebragenerator.controllers;


import com.schuyweiz.algebragenerator.JordanCanonical;
import com.schuyweiz.algebragenerator.QRdecomposition;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.FindEigenvalues;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixAddSubMul;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.util.Map;
import java.util.Random;

@Controller
public class BasicMatrixController {
    private static final Random rand = new Random();

    @GetMapping("/basicmatrixop")
    public String basicMatrix(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        MatrixAddSubMul problem = new MatrixAddSubMul(seed,3,3,generateSign());
        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "basicmatrixop";
    }

    @GetMapping("/eigenvalues")
    public String eigenvalues(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        System.out.println(seed);
        FindEigenvalues problem = new FindEigenvalues(seed);
        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "eigenvalues";
    }
    private String generateSign(){
        int value = rand.nextInt(3);
        return value==0? "+"
                : value == 1? "-"
                : "*";
    }

    @GetMapping("/jordan")
    public String jordan(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        System.out.println(seed);
        JordanCanonical problem = new JordanCanonical(seed);
        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "jordan";
    }

    @GetMapping("/qrdecomp")
    public String qrDecomp(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        System.out.println(seed);
        QRdecomposition problem = new QRdecomposition(seed);
        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "qrdecomp";
    }
}
