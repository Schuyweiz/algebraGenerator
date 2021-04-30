package com.schuyweiz.algebragenerator.controllers;


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

    private String generateSign(){
        int value = rand.nextInt(3);
        return value==0? "+"
                : value == 1? "-"
                : "*";
    }
}
