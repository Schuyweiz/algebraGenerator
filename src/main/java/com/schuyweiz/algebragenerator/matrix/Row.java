package com.schuyweiz.algebragenerator.matrix;


import java.io.StringWriter;
import java.util.ArrayList;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;


public class Row implements Cloneable {


    @Override
    protected Object clone() throws CloneNotSupportedException{
        Row row = new Row();
        row.content = new ArrayList<>();
        for (IExpr num: this.content){
            row.content.add(num);
        }
        row.size = this.size;
        return row;
    }

    public Row(){}


    public IExpr get(int id){
        return content.get(id);
    }
    public void set(int id, IExpr newValue){
        this.content.set(id, newValue);
    }


    public Row(ArrayList<IExpr> content){
        this.content = content;
        this.size = content.size();
    }



    public Row mult(IExpr times){
        ArrayList<IExpr> row = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).multiply(times);
            row.add(newNum);
        }
        return new Row(row);
    }

    public Row add(Row anotherRow, IExpr coef) throws Exception {
        ArrayList<IExpr> row = new ArrayList<>();
        var tempRow = anotherRow.mult(coef);
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).add(tempRow.get(i));
            row.add(newNum);
        }
        return new Row(row);
    }

    public Row add(Row anotherRow) throws Exception {
        return this.add(anotherRow,IntegerSym.valueOf(1));
    }

    public Row sub(Row anotherRow) throws Exception {
        ArrayList<IExpr> row = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            var newNum = this.content.get(i).subtract(anotherRow.get(i));
            row.add(newNum);
        }
        return new Row(row);
    }

    public IExpr mult(Column column) throws Exception {
        if (this.size != column.getSize()){
            throw new Exception("Wrong sizes for multiplication");
        }

        IExpr result = IntegerSym.valueOf(0);
        for (int i = 0; i < size; i++) {
            result = result.add(this.content.get(i).multiply(column.getContent().get(i)));
        }

        return result;
    }

    public int getSize() {
        return size;
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

    private ArrayList<IExpr> content;
    private int size;

}
