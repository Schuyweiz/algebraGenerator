package com.schuyweiz.algebragenerator.tasks;


import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.model.Problem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Random;

public abstract class MatrixProblem {

        protected Random rand;
        protected String problemText;
        protected String answerText;

        protected MatrixProblem(int seed, String problemText){
                this.rand = new Random(seed);
                this.problemText=problemText;
        }

        protected abstract String getProblemQuestion(Matrix... matrices);
        protected abstract String getProblemAnswer(Matrix... matrices);

        public abstract Problem generate();


}
