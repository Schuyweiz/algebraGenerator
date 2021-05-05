package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.tasks.basicmatrix.MatrixRank;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Random;

@Controller
public class MatrixRankController {


    @GetMapping("/matrixrank")
    public String basicMatrix(
            Map<String, Object> model
    ) throws Exception {

        int seed =  new Random().nextInt(10);
        MatrixRank matrixRank = new MatrixRank(seed);
        String problemContent = matrixRank.getProblemContent();
        String answerContent = matrixRank.getAnswerContent();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        return "matrixrank";
    }
}
