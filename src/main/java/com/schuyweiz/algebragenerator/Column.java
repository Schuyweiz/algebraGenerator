package com.schuyweiz.algebragenerator;

import java.util.ArrayList;

public class Column {

    private ArrayList<Integer> content;
    private int size;

    public Column(ArrayList<Integer> content){
        this.content = content;
        this.size = content.size();
    }


    public int getSize() {
        return size;
    }

    public ArrayList<Integer> getContent() {
        return content;
    }
}
