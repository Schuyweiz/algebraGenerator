package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.tasks.MatrixProblem;
import com.schuyweiz.algebragenerator.tasks.MatrixProblemFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Random;

@Controller
public class TasksController {
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

        return "/problems";
    }
}
