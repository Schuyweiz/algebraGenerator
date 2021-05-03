package com.schuyweiz.algebragenerator.tasks.basicmatrix;

import com.schuyweiz.algebragenerator.ElementaryCommand;
import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.*;

public class MatrixPowerN extends MatrixProblem {

    //для создания задач такого типа будем пользоваться формой A = P Q P^-1
    private Matrix P;
    private Matrix invP;
    private Matrix Q;
    private Matrix Qn;
    private Matrix A;
    private final int width;
    private final ArrayList<ArrayList<Integer>> opOrder = new ArrayList<>();
    private final ArrayList<ElementaryCommand> commands = new ArrayList<>();

    public MatrixPowerN(int width, int seed) throws Exception {
        this.width = width;
        this.rand = new Random(seed);
        this.P = Matrix.identity(width);
        this.invP = Matrix.identity(width);
        int commandsAmnt = rand.nextInt(3)+2;
        initCommands(commandsAmnt,width,3);
        createMatrixP();
        createMatrixInvP();
        createMatrixQ();
        createMatrixA();
        createMatrixQn();
    }

    private void initCommands(int amount, int sizeLimit, int coefLimit){
        for (int i = 0; i < amount; i++) {
            int from = rand.nextInt(sizeLimit);
            int to = rand.nextInt(sizeLimit);
            int coef = rand.nextInt(coefLimit) + 1;
            int type = rand.nextInt(3);
            if (type==0)
                commands.add(new ElementaryCommand(type,from, to));
            if (type==1)
                commands.add(new ElementaryCommand(type,from, to, coef));
            if (type==2)
                commands.add(new ElementaryCommand(type, from, coef));
        }
    }

    private void createMatrixQ(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(ExprUtils.getRandom(rand,-3,3));
        }
        this.Q = Matrix.diag(width,diagonal);
    }

    private void createMatrixQn(){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(F.Power(Q.get(i,i),F.Dummy("n")));
        }
        this.Qn = Matrix.diag(width,diagonal);
    }

    private void createMatrixA() throws Exception {
        this.A = this.P.mult(Q).mult(invP);
    }

    private void createMatrixP() throws Exception {
        for (ElementaryCommand command: commands){
            P = elementaryOperation(command,P);
        }
    }

    private void createMatrixInvP() throws Exception {
        for (ElementaryCommand command: commands){
            invP = elementaryOperationReverse(command,invP);
        }
    }

    @Override
    public String getProblemText() {
        return "Найти n-ую степень матрциы";
    }

    @Override
    public String getAnswerText() {
        return null;
    }

    @Override
    public String getProblemContent() {

        String aString = getMatrixValues(this.A);

        return String.format("\\(%s^{%s}\\)",aString,"n");
    }

    @Override
    public String getAnswerContent() throws Exception {
        Matrix m = this.P.mult(this.Qn).mult(this.invP);
        String pInvString = getMatrixValues(m);

        return String.format("\\( %s%s%s = %s\\)",
                getMatrixValues(P),getMatrixValues(Qn),getMatrixValues(invP),pInvString);

    }
}
