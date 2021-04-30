package com.schuyweiz.algebragenerator;


import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Row {
    private final ArrayList<Integer> content;
    private final int size;

    public int get(int id){
        return content.get(id);
    }


    public Row(ArrayList<Integer> content){
        this.content = content;
        this.size = content.size();
    }

    public void mult(int times){
        for (int i = 0; i < size; i++) {
            //this.content.set(i,this.content.get(i)*times);
            this.content.set(i,this.content.get(i)*times);
        }
    }

    public Row add(Row anotherRow) throws Exception {
        if (anotherRow.size != this.size)
            throw new Exception("Wrong rows sizes addition");

        ArrayList<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            newRow.add(i,this.content.get(i) + anotherRow.content.get(i));
        }

        return new Row(newRow);
    }

    public Row sub(Row anotherRow) throws Exception {
        if (anotherRow.size != this.size)
            throw new Exception("Wrong rows sizes addition");

        ArrayList<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            newRow.add(i,this.content.get(i) - anotherRow.content.get(i));
        }

        return new Row(newRow);
    }

    public int mult(Column column) throws Exception {
        if (this.size != column.getSize()){
            throw new Exception("Wrong sizes for multiplication");
        }

        int result=0;
        for (int i = 0; i < size; i++) {
            result += this.content.get(i) * column.getContent().get(i);
        }

        return result;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        return this.content.stream().map(String::valueOf).collect(Collectors.joining("&"));
    }



}
