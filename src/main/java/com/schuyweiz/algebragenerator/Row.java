package com.schuyweiz.algebragenerator;


import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Row implements Cloneable {
    private ArrayList<Number> content;
    private int size;

    @Override
    protected Object clone() throws CloneNotSupportedException{
        Row row = new Row();
        row.content = new ArrayList<>();
        for (Number num: this.content){
            row.content.add((Number) num.clone());
        }
        row.size = this.size;
        return row;
    }

    public Row(){}


    public Number get(int id){
        return content.get(id);
    }


    public Row(ArrayList<Number> content){
        this.content = content;
        this.size = content.size();
    }

    public void mult(int times){
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            this.content.get(i).mult(times);
        }
    }

    public void add(Row anotherRow, int coef) throws Exception {
        if (anotherRow.size != this.size)
            throw new Exception("Wrong rows sizes addition");

        for (int i = 0; i < size; i++) {
            this.content.get(i).add(anotherRow.get(i),1,coef);
        }
    }

    public void add(Row anotherRow) throws Exception {
        this.add(anotherRow,1);
    }


    public void sub(Row anotherRow) throws Exception {
        if (anotherRow.size != this.size)
            throw new Exception("Wrong rows sizes addition");

        ArrayList<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.content.get(i).sub(anotherRow.get(i));
        }
    }

    public Number mult(Column column) throws Exception {
        if (this.size != column.getSize()){
            throw new Exception("Wrong sizes for multiplication");
        }

        Number number = Number.nil();
        for (int i = 0; i < size; i++) {
            number.add(this.content.get(i).getProd(column.getContent().get(i)));
        }

        return number;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        return this.content.stream().map(Number::toString).collect(Collectors.joining("&"));
    }



}
