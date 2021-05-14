package com.schuyweiz.algebragenerator.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.FractionSym;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RotationAroundAxis extends MatrixProblem{

    private String problemText = "Найти угол поворота матрицы оператора и ось поворота: ";

    private final ArrayList<IExpr> possibleFractions = new ArrayList<>(
            List.of(
                    FractionSym.valueOf(1,4),
                    FractionSym.valueOf(1,3),
                    FractionSym.valueOf(2,3),
                    FractionSym.valueOf(1,2),
                    FractionSym.valueOf(3,4),
                    FractionSym.valueOf(1,6),
                    FractionSym.valueOf(5,6),
                    FractionSym.ONE
                    )
    );

    private IExpr arg;
    private Matrix A;
    private FractionSym frac;
    private ArrayList<IExpr> axis;

    public RotationAroundAxis(int seed){
        this.rand = new Random(seed);
        this.arg = initArg();
        this.axis = initAxis();
        this.A = initA();
    }

    private ArrayList<IExpr> initAxis() {
        ArrayList<IExpr> list = new ArrayList<>();
        int zeroes = rand.nextInt(2)+1;
        for (int i = 0; i < zeroes; i++) {
            list.add(IntegerSym.valueOf(0));
        }
        int axisSize = 3;
        IExpr expr = ExprUtils.getRandomNonNull(rand,-2,2);
        for (int i = 0; i < axisSize-zeroes; i++) {
            list.add(expr);
            expr = ExprUtils.getRandomNonNull(rand,-2,2);
        }

        for (int i = 0; i < list.size(); i++) {
            Collections.swap(list, rand.nextInt(list.size()), rand.nextInt(list.size()));
        }
        return list;
    }

    private IExpr initArg() {
        ExprEvaluator util = new ExprEvaluator(false, (short) 100);
        var choice  = rand.nextInt(possibleFractions.size());
        var expr = possibleFractions.get(choice).times(F.Pi);
        frac = (FractionSym) possibleFractions.get(choice);
        if (rand.nextBoolean()) {
            return util.eval(expr);
        }
        else {
            return util.eval(expr.negative());
        }
    }

    private Matrix initA(){
        var x = axis.get(0);
        var y = axis.get(1);
        var z = axis.get(2);
        ExprEvaluator util = new ExprEvaluator(false, (short) 100);
        var cos = util.eval(F.Cos(arg));
        var sin = util.eval(F.Sin(arg));

        ArrayList<IExpr> row1 = new ArrayList<>(
                List.of(
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(x.times(x))),
                        x.times(y).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(z)),
                        x.times(z).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(y))
                )
        );

        ArrayList<IExpr> row2 = new ArrayList<>(
                List.of(
                        x.times(y).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(z)),
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(y.times(y))),
                        y.times(z).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(x))
                )
        );

        ArrayList<IExpr> row3 = new ArrayList<>(
                List.of(
                        x.times(z).times(IntegerSym.valueOf(1).minus(cos)).minus(sin.times(y)),
                        y.times(z).times(IntegerSym.valueOf(1).minus(cos)).plus(sin.times(x)),
                        cos.plus(IntegerSym.valueOf(1).minus(cos).times(z.times(z)))
                )
        );


        return new Matrix(new ArrayList<Row>(
                List.of(
                        new Row(row1),
                        new Row(row2),
                        new Row(row3)
                )
        ));

    }

    @Override
    public String getProblemText() {
        return problemText;
    }

    @Override
    public String getAnswerText() {
        //TODO: write [roper answer text
        return "Найти угол поворота и ось вращения: ";
    }

    @Override
    public String getProblemContent() {
        return texExpression(
                String.format(
                        "%s", getMatrixValues(A)
                )
        );
    }

    @Override
    public String getAnswerContent()  {
        return String.format(
                "Угол поворота θ = %s\n" +
                        "Ось вращения v = %s",
                texExpression(
                        String.format("%s\\times \\frac{%s}{%s}", "\\pi",frac.numerator(),frac.denominator())
                ),
                texExpression(axis.stream()
                        .map(s->s.toString())
                        .collect(Collectors.joining(",")))
        );
    }
}
