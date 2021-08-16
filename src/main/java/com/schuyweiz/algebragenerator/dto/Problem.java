package com.schuyweiz.algebragenerator.model;


import com.schuyweiz.algebragenerator.utility.TexUtils;
import lombok.Getter;

@Getter
public class Problem {
    private final String question;
    private final String questionTex;
    private final String answerTex;


    public Problem(String question, String questionTex, String answerTex) {
        this.question = question;
        this.questionTex = TexUtils.getTex(questionTex);
        this.answerTex = TexUtils.getTex(answerTex);
    }
}
