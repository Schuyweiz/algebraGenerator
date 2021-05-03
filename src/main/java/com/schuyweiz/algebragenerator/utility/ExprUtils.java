package com.schuyweiz.algebragenerator.utility;

import org.matheclipse.core.expression.*;
import org.matheclipse.core.interfaces.IExpr;

import java.util.Random;

public class ExprUtils {

    public static IExpr getRandom(Random rand, int left, int right){
        int num = rand.nextInt(right-left) + right+1;
        return IntegerSym.valueOf(num);
    }

    public static IExpr getRandomNonNull(Random rand, int left, int right){
        IExpr rhs;
        if (left<0){
            rhs = getRandom(rand, 1,right);
        }
        else if (left!=0){
            return getRandom(rand, left, right);
        }
        else{
            return getRandom(rand,1,right);
        }

        boolean isPositive = rand.nextBoolean();

        if (isPositive){
            return rhs;
        }
        else{
            return rhs.times(IntegerSym.valueOf(-1));
        }
    }

    public static String getExpression(IExpr expr){

        if (expr instanceof FractionSym){
            var fraction = ((FractionSym) expr);

            if (fraction.isNegativeSigned()){
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
                    String sign = expr.getAt(i).isNegativeSigned()?"":" + ";
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
}
