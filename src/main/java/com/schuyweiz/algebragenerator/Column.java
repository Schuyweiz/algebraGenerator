package com.schuyweiz.algebragenerator;

import java.util.ArrayList;

public class Column {

    private ArrayList<Number> content;
    private int size;

    public Column(ArrayList<Number> content){
        this.content = content;
        this.size = content.size();
    }


    public int getSize() {
        return size;
    }

    public ArrayList<Number> getContent() {
        return content;
    }
}
