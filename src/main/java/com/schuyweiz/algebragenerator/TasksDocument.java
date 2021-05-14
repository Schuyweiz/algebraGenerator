package com.schuyweiz.algebragenerator;

import com.schuyweiz.algebragenerator.tasks.MatrixProblem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TasksDocument {
    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();

    public TasksDocument(){
    }

    public void addTask(MatrixProblem problem){
        this.text.add(problem.getProblemText());
        this.content.add(problem.getProblemContent());
        this.answers.add(problem.getAnswerContent());
    }

    public String createTex(String content) throws IOException {

        return String.format(
                "\\documentclass{article}\n" +
                        "\\usepackage[utf8]{inputenc}\n" +
                        "\\usepackage{mathtools} \n" +
                        "\\usepackage[english,russian]{babel}\n" +
                        "\\begin{document}\n" +
                        "%s\n" +
                        "\\end{document}",
                content
        );

    }

    public void createTasksTex(String path) throws IOException {

        String contentTasks = createTex(documentTasks());

        FileWriter fw = new FileWriter( path + "tasks.tex");
        fw.write(contentTasks);
        fw.close();

        String contentAnswer = createTex(documentSolutions());


        FileWriter fw2 = new FileWriter( path + "answers.tex");
        fw2.write(contentAnswer);
        fw2.close();
    }

    private String documentTasks(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.size(); i++) {
            sb.append(String.format("\\section*{Задача № %s}\n",i+1));
            sb.append(text.get(i)).append("\n");
            sb.append(content.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String documentSolutions(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.size(); i++) {
            sb.append(String.format("\\section*{Задача № %s}\n",i+1));
            sb.append(text.get(i)).append("\n");
            sb.append(content.get(i)).append("\n");
            sb.append(String.format("\\section*{Ответ № %s}\n",i+1));
            sb.append(answers.get(i)).append("\n");
        }
        return sb.toString();
    }
}
