package com.schuyweiz.algebragenerator;
import org.junit.jupiter.api.Test;
import org.matheclipse.core.builtin.Arithmetic;
import org.matheclipse.core.expression.*;
import org.matheclipse.core.interfaces.*;
import symjava.symbolic.Expr;
import static symjava.symbolic.Symbol.*;
import symjava.bytecode.BytecodeFunc;
import symjava.symbolic.*;
import symjava.symbolic.Symbol;

import java.beans.Expression;
import java.util.ArrayList;

class SymJavaTest {


    @Test
    void testFunctionality(){
        ArrayList<Expr> list = new ArrayList<>();
        IInteger expression = IntegerSym.valueOf(5);
        IFraction fs = FractionSym.valueOf(4L,6L);

        IRational rat = expression.multiply(fs);
        System.out.println(rat.toString());
        IExpr expr = F.Dummy("a");
        expr = F.Power(expr,3);
        expr = fs.add(expr);
        expr = expr.subtract(FractionSym.valueOf(2));
        expr = expr.multiply(FractionSym.valueOf(4));
        System.out.println(expr.toString());
        System.out.println(expr.size());

        IExpr expr2 = F.pow(F.Dummy("a"), IntegerSym.valueOf(3));

        for (int i=0;i<expr2.size();i++){

            var val = expr2.getAt(i);

            if (val instanceof BuiltInSymbol){
                var tmep = ((BuiltInSymbol) val);
                if (val.toString().equals("Power"))
                    System.out.println("hooray");
                System.out.println("yay");
                System.out.println(val);
            }
            System.out.println(val.getClass().getSimpleName());
        }

    }
}
