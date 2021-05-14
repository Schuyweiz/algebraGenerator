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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        document.addTask(currentProblem);
        return "redirect:/problems";
    }

    @GetMapping(value = "/download",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> demo()
            throws IOException, InterruptedException {
        
        
        String pathTasks = "src/main/resources/files/tasks.tex";
        String pathAnswers = "src/main/resources/files/answers.tex";
        document.createTasksTex("./src/main/resources/files/");
        ProcessBuilder pb = new ProcessBuilder(
                "/usr/bin/pdflatex",
                "-output-directory=src/main/resources/files",
                "-interaction=nonstopmode",
                pathTasks
        );
        ProcessBuilder pb2 = new ProcessBuilder(
                "/usr/bin/pdflatex",
                "-output-directory=src/main/resources/files",
                "-interaction=nonstopmode",
                pathAnswers
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

        Process p2  = pb2.start();
        p2.waitFor();


        String zipPath = "src/main/resources/files/algebrator.zip";
        createZip(
                zipPath,
                new ArrayList<>(List.of(
                        pathTasks,
                    pathAnswers,
                    "src/main/resources/files/tasks.pdf",
                    "src/main/resources/files/answers.pdf")),
                new ArrayList<>(
                        List.of(
                                "/tasks.tex",
                                "/answers.tex",
                                "/tasks.pdf",
                                "/answers.pdf"
                        )
                )
        );

        var content = Files.readAllBytes(Path.of(zipPath));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("algebrator.zip").build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(content);
    }
    
    private void createZip(String zipPath, ArrayList<String> contentPaths, ArrayList<String> zipPaths) throws IOException {
        FileOutputStream fout = new FileOutputStream(zipPath);
        ZipOutputStream zout = new ZipOutputStream(fout);
        for(int i=0;i<contentPaths.size();i++)
        {
            ZipEntry ze = new ZipEntry(zipPaths.get(i));
            zout.putNextEntry(ze);
            var content = Files.readAllBytes(Path.of(contentPaths.get(i)));
            zout.write(content);
            zout.closeEntry();
        }
        zout.close();
    }

}
