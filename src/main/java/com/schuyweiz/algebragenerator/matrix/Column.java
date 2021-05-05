package com.schuyweiz.algebragenerator.matrix;

import java.util.ArrayList;

import org.matheclipse.core.interfaces.IExpr;

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
