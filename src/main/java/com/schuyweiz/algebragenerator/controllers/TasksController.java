package com.schuyweiz.algebragenerator.controllers;

import com.schuyweiz.algebragenerator.TasksDocument;
import com.schuyweiz.algebragenerator.tasks.MatrixAddSubMul;
import com.schuyweiz.algebragenerator.tasks.MatrixProblem;
import com.schuyweiz.algebragenerator.tasks.MatrixProblemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@SessionScope
public class TasksController {

    @Autowired
    private TasksDocument document = new TasksDocument();

    private MatrixProblem currentProblem = new MatrixAddSubMul((int) System.currentTimeMillis());

    String currentType = "addsubmult";

    @GetMapping("/")
    public String greeting(
            Map<String, Object> model
    ){
        String problemContent = currentProblem.getProblemContent();
        String answerContent = currentProblem.getAnswerContent();
        String problemText = currentProblem.getProblemText();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        model.put("problemtext", problemText);
        model.put("items", document.getSize());
        model.put("size",document.getSize());
        return "redirect:/problems?addsubmult";
    }

    @GetMapping("/problems")
    public String problem(
            Map<String,Object> model, @RequestParam(name="type", defaultValue = "none") String type
    )throws Exception{

        int seed =  new Random((int) System.currentTimeMillis()).nextInt();
        MatrixProblem problem;

        if (!type.equals("none")){
            currentType = type;
        }

        if (type.equals("none")){
                problem = MatrixProblemFactory.typeof(currentType,seed);
        }
        else
            problem = MatrixProblemFactory.typeof(type,seed);

        //TODO:track amount of roblems, empty the problems after download

        String problemContent = problem.getProblemContent();
        String answerContent = problem.getAnswerContent();
        String problemText = problem.getProblemText();
        model.put("problem", problemContent);
        model.put("answer", answerContent);
        model.put("problemtext", problemText);
        model.put("items", document.getSize());
        model.put("size",document.getSize());
        currentProblem = problem;

        return "/problems";
    }


    @PostMapping("/problems/add")
    public String add()
    {
        document.addTask(currentProblem);
        return "redirect:/problems";
    }

    @GetMapping(value = "/download")
    public @ResponseBody
    ResponseEntity<byte[]> download()
            throws IOException, InterruptedException {

        Random rand = new Random(System.currentTimeMillis());
        String tname =  getRandomName(rand);
        String aname =  getRandomName(rand);
        String tasksName = "tasks" + tname + ".tex";
        String answersName = "answers" + aname + ".tex";

        String pathTasks = "src/main/resources/files/"+tasksName;
        String pathAnswers = "src/main/resources/files/"+ answersName;
        document.createTasksTex(pathTasks,pathAnswers);
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


        String zipName = getRandomName(rand) + ".zip";
        String zipPath = "src/main/resources/files/"+zipName;
        createZip(
                zipPath,
                new ArrayList<>(List.of(
                        pathTasks,
                    pathAnswers,
                    "src/main/resources/files/tasks" + tname + ".pdf",
                    "src/main/resources/files/answers" + aname + ".pdf")),
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
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("algebrator.zip").build().toString());
        File dir = new File("src/main/resources/files");
        for(File file: Objects.requireNonNull(dir.listFiles()))
            if (!file.isDirectory())
                file.delete();

        this.document = new TasksDocument();

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

    private String getRandomName(Random rand){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (rand.nextBoolean())
                sb.append('A' + rand.nextInt(26));
            else
                sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    @PostMapping (value = "/reset")
    public String endSession(){
        this.document = new TasksDocument();
        return "redirect:/";
    }

    @GetMapping(value = "/reset")
    public String reset(){
        this.document = new TasksDocument();
        return "redirect:/";
    }
}
