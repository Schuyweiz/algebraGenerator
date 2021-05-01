package com.schuyweiz.algebragenerator;

public class Power extends Fraction {
    private Fraction base;
    private PowerRate power;
    private Fraction extraTerm = Fraction.nil();

    public Power(Fraction base, PowerRate power) {
        this.base = base;
        this.power = power;
    }

    public void mult(Fraction another) {
        this.base.mult(another);
    }

    public void add(Fraction another) {
        this.extraTerm.add(another);
    }


    @Override
    public String toString(){
        String powerString;
        if(power.isNumeric() && power.getNumber() == 1){
            powerString = base.toString();
        }
        else{
            powerString = base.toString() + String.format("^{%s}", power.toString());
        }
        if(extraTerm.getNom()!=0){
            powerString += extraTerm.isPositive()? "+":"-";
            powerString += extraTerm.toString();
        }
        return powerString;

    }
}
