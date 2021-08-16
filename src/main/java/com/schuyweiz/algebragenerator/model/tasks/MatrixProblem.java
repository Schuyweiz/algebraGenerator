package com.schuyweiz.algebragenerator.model.tasks;


import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.dto.Problem;

import java.util.Random;

public abstract class MatrixProblem {

        protected Random rand;
        protected String problemText;

        protected MatrixProblem(int seed, String problemText){
                this.rand = new Random(seed);
                this.problemText=problemText;
        }

        protected abstract String getProblemQuestion(Matrix... matrices);
        protected abstract String getProblemAnswer(Matrix... matrices);

        public abstract Problem generate();


}
