package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixAddSubMul;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Random;
import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixPowerN;

@Controller
public class PowernController {
    private static final Random rand = new Random();

    @GetMapping("/powern")
    public String powerN(
            Map<String, Object> model
    ) throws Exception {

        int seed =  rand.nextInt(10);
        MatrixPowerN problem = new MatrixPowerN(seed);
        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        String problemText = problem.getProblemText();


        model.put("problemtext", problemText);
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "powern";
    }

    private String generateSign(){
        int value = rand.nextInt(3);
        return value==0? "+"
                : value == 1? "-"
                : "*";
    }
}
