package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.tasks.basicmatrix.InverseMatrix;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixAddSubMul;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Random;

@Controller
public class MatrixInvertibleController {
    @GetMapping("/matrixinv")
    public String basicMatrix(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        InverseMatrix imProblem = new InverseMatrix(seed);
        String problemContent = imProblem.getProblemContent();
        String answerContent = imProblem.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "/matrixinv";
    }
}
