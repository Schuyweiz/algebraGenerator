package com.schuyweiz.algebragenerator.controllers;


import com.schuyweiz.algebragenerator.JordanCanonical;
import com.schuyweiz.algebragenerator.OrthgonalDiag;
import com.schuyweiz.algebragenerator.QRdecomposition;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.FindEigenvalues;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixAddSubMul;

import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixProblem;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixProblemFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.Map;
import java.util.Random;

@Controller
public class BasicMatrixController {
    private static final Random rand = new Random();
    private String message;

    @GetMapping("/problems")
    public String problem(
            Map<String,Object> model, @RequestParam(name="type") String type
    )throws Exception{

        int seed =  new Random().nextInt(10);
        MatrixProblem problem = MatrixProblemFactory.typeof(type,seed);

        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);

        return "/qrdecomp";
    }

    @GetMapping("/basicmatrixop")
    public String basicMatrix(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        MatrixAddSubMul problem = new MatrixAddSubMul(seed);
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

    @GetMapping("/orthdiag")
    public String orthDiag(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        System.out.println(seed);
        OrthgonalDiag problem = new OrthgonalDiag(seed);
        String problemContent = problem.getProblemContent();
        String answerText = problem.getAnswerText();
        String problemText = problem.getProblemText();
        String answerContent = problem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        model.put("problemtext", problemText);
        model.put("answertext", answerText);
        return "orthdiag";
    }
}
