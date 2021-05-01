package com.schuyweiz.algebragenerator;

import de.lab4inf.math.functions.Power;
import org.apache.tomcat.util.buf.B2CConverter;
import org.matheclipse.core.expression.B2;
import org.matheclipse.core.expression.FractionSym;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;
import symjava.symbolic.Expr;
import symjava.symbolic.Pow;

import java.util.Random;

public class ExprUtils {

    public static IExpr getRandom(Random rand, int left, int right){
        int num = rand.nextInt(right-left) + right+1;
        return IntegerSym.valueOf(num);
    }

    public static String getExpression(IExpr expr){
        if(expr.size()==1){

        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expr.size(); i+=2) {
            var val = expr.getAt(i);

            if (val instanceof Pow){
                String base = getExpression(val.getAt(i+1));
                String pow = getExpression(val.getAt(i+2));

            }
        }

        return null;
    }

    private static String convertSingle(IExpr expr){
        if (expr instanceof IntegerSym){
            return ((IntegerSym) expr).toString();
        }

        if (expr instanceof B2){
            var b2Expr = ((B2) expr);
            return String.format("%s^{%s}",
                    b2Expr.arg1().toString(),b2Expr.arg2().toString());
        }

        if (expr instanceof FractionSym){
            var fractionExpr = ((FractionSym) expr);
            return String.format("\\frac{%s}{%s}", fractionExpr.getAt(0), fractionExpr.getAt(1));
        }

        return expr.toString();
    }
}
