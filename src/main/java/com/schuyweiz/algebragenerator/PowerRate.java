package com.schuyweiz.algebragenerator;

public class PowerRate {
    private String symbol;
    private int number;
    private boolean isNumeric;

    public PowerRate(int number){
        this.number = number;
        isNumeric = true;
    }
    public PowerRate(String symbol){
        this.symbol = symbol;
        isNumeric = false;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNumber() {
        return number;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    @Override
    public String toString(){
        if(isNumeric){
            return String.valueOf(number);
        }
        else{
            return symbol;
        }
    }
}
