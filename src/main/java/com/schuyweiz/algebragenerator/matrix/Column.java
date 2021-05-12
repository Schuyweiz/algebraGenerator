package com.schuyweiz.algebragenerator.matrix;

import java.io.StringWriter;
import java.util.ArrayList;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;

public class Column {

    private ArrayList<IExpr> content;
    private int size;
    public boolean isWeak(){
        int zeroes = 0;
        for (int i = 0; i < size; i++) {
            if (content.get(i).equals(IntegerSym.valueOf(0)))
                zeroes++;
            if (zeroes==size-1)
                return true;
        }
        return false;
    }

    public Column(ArrayList<IExpr> content){
        this.content = content;
        this.size = content.size();
    }

    public Column mult(IExpr times){
        ArrayList<IExpr> col = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).multiply(times);
            col.add(newNum);
        }
        return new Column(col);
    }

    public Column add(Column anotherCol, IExpr coef) throws Exception {
        ArrayList<IExpr> col = new ArrayList<>();
        var tempRow = anotherCol.mult(coef);
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).add(tempRow.get(i));
            col.add(newNum);
        }
        return new Column(col);
    }

    public Column sub(Column anotherCol) throws Exception {
        ArrayList<IExpr> col = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).subtract(anotherCol.get(i));
            col.add(newNum);
        }
        return new Column(col);
    }


    public int getSize() {
        return size;
    }

    public ArrayList<IExpr> getContent() {
        return content;
    }

    public void set(int i, IExpr newValue){
        this.content.set(i, newValue);
    }

    public IExpr get(int at) {
        return content.get(at);
    }

    @Override
    public String toString(){

        StringWriter wr = new StringWriter();
        TeXUtilities tu = new TeXUtilities(new EvalEngine(),false);
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //arr.add(ExprUtils.getExpression(util.eval(content.get(i))));
            tu.toTeX(content.get(i),wr);
            arr.add(wr.toString());
            wr.getBuffer().setLength(0);
            wr.getBuffer().trimToSize();
        }
        return String.join("&", arr);
    }

    public Row toRow(){
        return new Row(this.content);
    }
}
