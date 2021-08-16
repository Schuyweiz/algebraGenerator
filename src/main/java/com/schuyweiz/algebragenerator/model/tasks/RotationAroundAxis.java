package com.schuyweiz.algebragenerator.model.tasks;

import com.schuyweiz.algebragenerator.matrix.Matrix;
import com.schuyweiz.algebragenerator.matrix.Row;
import com.schuyweiz.algebragenerator.dto.Problem;
import com.schuyweiz.algebragenerator.utility.ExprUtils;
import com.schuyweiz.algebragenerator.utility.TexUtils;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.FractionSym;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RotationAroundAxis extends MatrixProblem {

    private FractionSym frac;
    private final ArrayList<IExpr> axis;
    private final ArrayList<IExpr> possibleFractions = new ArrayList<>(
            List.of(
                    FractionSym.valueOf(1, 4),
                    FractionSym.valueOf(1, 3),
                    FractionSym.valueOf(2, 3),
                    FractionSym.valueOf(1, 2),
                    FractionSym.valueOf(3, 4),
                    FractionSym.valueOf(1, 6),
                    FractionSym.valueOf(5, 6),
                    FractionSym.ONE
            )
    );

    public RotationAroundAxis(int seed) {
        super(seed, "Найти угол поворота и ось вращения: ");
        axis = initAxis();
    }

    private ArrayList<IExpr> initAxis() {
        ArrayList<IExpr> list = new ArrayList<>();
        int zeroes = rand.nextInt(2) + 1;
        for (int i = 0; i < zeroes; i++) {
            list.add(IntegerSym.valueOf(0));
        }
        int axisSize = 3;
        IExpr expr = ExprUtils.getRandomNonNull(rand, -2, 2);
        for (int i = 0; i < axisSize - zeroes; i++) {
            list.add(expr);
            expr = ExprUtils.getRandomNonNull(rand, -2, 2);
        }

        for (int i = 0; i < list.size(); i++) {
            Collections.swap(list, rand.nextInt(list.size()), rand.nextInt(list.size()));
        }
        return list;
    }

    private IExpr initArg() {
        ExprEvaluator util = new ExprEvaluator(false, (short) 100);
        var choice = rand.nextInt(possibleFractions.size());
        var expr = possibleFractions.get(choice).times(F.Pi);
        frac = (FractionSym) possibleFractions.get(choice);
        if (rand.nextBoolean()) {
            return util.eval(expr);
        } else {
            return util.eval(expr.negative());
        }
    }

    private Matrix initA(IExpr arg) {
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


        return new Matrix(new ArrayList<>(
                List.of(
                        new Row(row1),
                        new Row(row2),
                        new Row(row3)
                )
        ));

    }

    @Override
    protected String getProblemQuestion(Matrix... matrices) {
        var A = matrices[0];

        return String.format("%s", TexUtils.getMatrixTex(A));
    }

    @Override
    protected String getProblemAnswer(Matrix... matrices) {
        return String.format(
                "Угол поворота θ = %s\n" +
                        "Ось вращения v = %s",
                String.format("%s\\times \\frac{%s}{%s}", "\\pi", frac.numerator(), frac.denominator()),
                (axis.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")))
        );
    }

    @Override
    public Problem generate() {
        var arg = initArg();
        var A = initA(arg);

        return new Problem(
                this.problemText,
                getProblemQuestion(A),
                getProblemAnswer()
        );
    }
}
