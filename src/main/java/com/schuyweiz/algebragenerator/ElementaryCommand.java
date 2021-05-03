package com.schuyweiz.algebragenerator;

import java.util.Random;

public class ElementaryCommand {
    private int from;
    private int to;
    private int coef;
    private int type;

    public ElementaryCommand(int type, Integer... args){
        this.type = type;
        if (type==0){
            from = args[0];
            to = args[1];
        }
        if (type == 1){
            from = args[0];
            to = args[1];
            coef = args[2];
        }
        if (type == 2){
            to = args[0];
            coef = args[1];
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCoef() {
        return coef;
    }

    public int getType() {
        return type;
    }
}
