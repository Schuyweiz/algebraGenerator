package com.schuyweiz.algebragenerator;

import java.util.ArrayList;

import org.matheclipse.core.interfaces.IExpr;
import symjava.symbolic.Expr;

public class Column {

    private ArrayList<IExpr> content;
    private int size;

    public Column(ArrayList<IExpr> content){
        this.content = content;
        this.size = content.size();
    }


    public int getSize() {
        return size;
    }

    public ArrayList<IExpr> getContent() {
        return content;
    }
}
