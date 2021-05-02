package com.schuyweiz.algebragenerator;

import de.lab4inf.math.functions.Power;
import org.apache.tomcat.util.buf.B2CConverter;
import org.matheclipse.core.expression.*;
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

        if (expr instanceof FractionSym){
            var fraction = ((FractionSym) expr);

            if (fraction.isNegative()){
                return String.format("-\\frac{%s}{%s}",
                        fraction.numerator().abs().toString(),
                        fraction.denominator().abs().toString());
            }
            return String.format("\\frac{%s}{%s}",
                    fraction.numerator().toString(),
                    fraction.denominator().toString());
        }

        if (expr.isAST()){
            var operation = expr.getAt(0).toString();
            StringBuilder sb = new StringBuilder();

            if (operation.equals("Plus")){
                sb.append(getExpression(expr.getAt(1)));
                for (int i = 2; i < expr.size(); i++) {
                    String sign = expr.getAt(i).isPositive()?" + ":" - ";
                    sb.append(sign).append(getExpression(expr.getAt(i)) );
                }
            }

            if (operation.equals("Times")){
                sb.append(getExpression(expr.getAt(1)));
                for (int i = 2; i < expr.size(); i++) {
                    sb.append(" \\cdot ")
                            .append(getExpression(expr.getAt(i)) );
                }
            }

            if (operation.equals("Power")){
                if (expr.getAt(2).isAST()) {
                    sb.append(
                            String.format(" %s^{(%s)} ",
                                    getExpression(expr.getAt(1)),
                                    getExpression(expr.getAt(2)))
                    );
                }
                else {
                    sb.append(
                            String.format(" %s^{%s} ",
                                    getExpression(expr.getAt(1)),
                                    getExpression(expr.getAt(2)))
                    );
                }
            }

            return sb.toString();
        }
        else {
            return expr.toString();
        }


    }

    private static String convertSingle(IExpr expr){
        if (expr.size() == 0){
            return expr.toString();
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
