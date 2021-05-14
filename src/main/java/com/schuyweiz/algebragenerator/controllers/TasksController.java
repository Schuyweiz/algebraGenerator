package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.TasksDocument;
import com.schuyweiz.algebragenerator.tasks.MatrixProblem;
import com.schuyweiz.algebragenerator.tasks.MatrixProblemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Random;

@Controller
public class TasksController {

    @Autowired
    private final TasksDocument document = new TasksDocument();

    private MatrixProblem currentProblem;

    @GetMapping("/problems")
    public String problem(
            Map<String,Object> model, @RequestParam(name="type", defaultValue = "none") String type
    )throws Exception{

        int seed =  new Random().nextInt(10);
        MatrixProblem problem;

        if (type.equals("none")){
                problem = currentProblem;
        }
        else
            problem = MatrixProblemFactory.typeof(type,seed);

        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        String problemText = problem.getProblemText();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        model.put("problemtext", problemText);
        currentProblem = problem;

        return "/problems";
    }


    @PostMapping("/problems/add")
    public String add()
    {
        document.addTask(currentProblem.getProblemText(),currentProblem.getProblemContent());
        return "redirect:/problems";
    }

    @GetMapping(value = "/download",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> demo() throws IOException, InterruptedException {
        document.createTex("./src/main/resources/files/");
        ProcessBuilder pb = new ProcessBuilder(
                "/usr/bin/pdflatex",
                "-output-directory=src/main/resources/files",
                "-interaction=nonstopmode",
                "src/main/resources/files/tasks.tex"
        );

        pb.redirectErrorStream(true);
        Process p  = pb.start();
        InputStream is = p.getInputStream();
        int in=-1;

        while((in=is.read())!=-1){
            System.out.print((char)in);
        }

        int exitWith = p.waitFor();
        System.out.println("\nExited with " + exitWith);


        var content = Files.readAllBytes(Path.of("src/main/resources/files/tasks.pdf"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("tasks.pdf").build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(content);
    }

}
