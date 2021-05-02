package com.schuyweiz.algebragenerator;
import de.uni_bielefeld.cebitec.mzurowie.pretty_formula.main.FormulaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.matheclipse.core.builtin.Arithmetic;
import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.*;
import org.matheclipse.core.interfaces.*;
import org.matheclipse.core.parser.ExprParser;
import symjava.symbolic.Expr;
import static symjava.symbolic.Symbol.*;
import symjava.bytecode.BytecodeFunc;
import symjava.symbolic.*;
import symjava.symbolic.Symbol;

import java.beans.Expression;
import java.util.ArrayList;

class SymJavaTest {


    @Test
    void testFunctionality() {
        ArrayList<Expr> list = new ArrayList<>();
        IInteger expression = IntegerSym.valueOf(5);
        IFraction fs = FractionSym.valueOf(4L, 6L);

        IRational rat = expression.multiply(fs);

        IExpr expr = F.pow(F.Dummy("a"), IntegerSym.valueOf(4).add(F.Dummy("y")));
        expr = expr.add(F.Dummy("b"));
        expr = expr.divide(F.Dummy("c"));
        expr = expr.subtract(IntegerSym.valueOf(3));
        expr = expr.multiply(F.Dummy("d"));


        System.out.println(expr);
        AST2Expr ast2Expr = new AST2Expr();
        ExprParser parser = new ExprParser(new EvalEngine());

        System.out.println(expr.getAt(0));
        System.out.println(expr.getAt(1));
        System.out.println(expr.getAt(2));

        var ex1 = expr.getAt(1);
        System.out.println(ex1);
        System.out.println(ex1.getAt(0));
        System.out.println(ex1.getAt(1));
        System.out.println(ex1.getAt(2));

        var ex2 = ex1.getAt(1);
        System.out.println(ex2);
        System.out.println(ex2.getAt(0));
        System.out.println(ex2.getAt(1));
        System.out.println(ex2.getAt(2));

        var ex3 = ex2.getAt(2);
        System.out.println(ex3);
        System.out.println(ex3.getAt(0));
        System.out.println(ex3.getAt(1));
        System.out.println(ex3.getAt(2));
        System.out.println(ex3.size());




        var ex4 = ex3.getAt(1);
        System.out.println(ex3);
        System.out.println(ex3 +" "+ex3.isAST());
        System.out.println(ex2 +" "+ex2.isAST());
        System.out.println(ex1 +" "+ex1.isAST());
        System.out.println(expr +" "+expr.isAST());
        System.out.println(ex4 +" "+ex4.isAST());
        System.out.println(ex4 + " is a positive: " + ex4.isPositive());










    }




    }

