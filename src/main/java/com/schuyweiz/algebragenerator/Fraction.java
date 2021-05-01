package com.schuyweiz.algebragenerator;

import java.util.Random;

public class Fraction implements Cloneable {

    public Fraction() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        Fraction fraction = nil();
        fraction.nom = this.nom;
        fraction.denom = this.denom;
        return fraction;
    }


    private int nom;
    private int denom;

    public Fraction(int nom, int denom){
        this.nom = nom;
        this.denom = denom;
    }
    public Fraction(int nom){
        this.nom = nom;
        this.denom = 1;
    }

    public static Fraction getRandom(Random random, int min, int max){
        return new Fraction(random.nextInt(max-min) + min+1);
    }

    public static Fraction nil(){
        return new Fraction(0);
    }

    public void add(Fraction another, Fraction coef){
            this.add(coef.getProd(another));
    }

    public void add(Fraction another){
        this.add(another,1,1);
    }

    public void add(Fraction another, int coef, int coefAnother){
        if (this.denom == another.denom){
            this.nom= this.nom*coef +  another.nom * coefAnother;
        }

        if (this.denom != another.denom){
            this.nom = this.nom * coef * another.denom + this.denom* another.nom*coefAnother;
            this.denom*= another.denom;;
        }
        if (nom == denom){
            nom  =1;
            denom = 1;
        }
    }

    public void sub(Fraction another){
        this.sub(another, 1,1);
    }

    public void sub(Fraction another, int coef, int coefAnother){
        if (this.denom == another.denom){
            this.nom= this.nom*coef - another.nom*coefAnother;
        }

        if (this.denom != another.denom){
            this.denom = this.nom * coef * another.denom - this.denom* another.nom*coefAnother;
            this.denom*= another.denom;
        }

        if (nom == denom){
            nom  =1;
            denom = 1;
        }
    }

    public void mult(Fraction another){
        this.nom*= another.nom;
        this.denom*= another.denom;
        if(nom==0){
            this.denom = 1;
        }
        if (nom == denom){
            nom  =1;
            denom = 1;
        }
    }

    public void mult(int another){
        this.nom*=another;
        if(nom==0){
            this.denom = 1;
        }
    }

    public Fraction getProd(Fraction another){
        int newNom = this.nom * another.nom;
        int newDen = this.denom * another.denom;

        if(newNom==0){
            return Fraction.nil();
        }
        return  new Fraction(newNom,newDen);
    }


    public void div(Fraction another){
        this.nom*= another.denom;
        this.denom*= another.nom;
        if (nom == denom){
            nom  =1;
            denom = 1;
        }
    }

    public int getNom() {
        return nom;
    }

    public int getDenom() {
        return denom;
    }

    public boolean isPositive() {
        return (nom>0 && denom >0) || (nom<0 && denom<0);
    }

    @Override
    public String toString(){
        if(this.denom == 1){
            return String.valueOf(this.nom
            );
        }
        return String.format("\\frac{%d}{%d}", this.nom, this.denom);
    }

    //TODO: euclide algo to simplify

}
