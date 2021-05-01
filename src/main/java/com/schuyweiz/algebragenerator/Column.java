package com.schuyweiz.algebragenerator;

import java.util.ArrayList;

public class Column {

    private ArrayList<Fraction> content;
    private int size;

    public Column(ArrayList<Fraction> content){
        this.content = content;
        this.size = content.size();
    }


    public int getSize() {
        return size;
    }

    public ArrayList<Fraction> getContent() {
        return content;
    }
}
