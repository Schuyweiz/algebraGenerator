package com.schuyweiz.algebragenerator;

import java.util.Random;

public class ElementaryCommand {
    private int from;
    private int to;
    private int coef;
    private int type;

    public ElementaryCommand(int type, int from, int to, int coef){
        this.type = type;
        this.from = from;
        this.to = to;
        this.coef = coef;
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
