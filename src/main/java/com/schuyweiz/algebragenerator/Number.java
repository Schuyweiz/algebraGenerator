package com.schuyweiz.algebragenerator;

import java.util.Random;

public class Number implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException{
        Number number = nil();
        number.nom = this.nom;
        number.denom = this.denom;
        return number;
    }


    private int nom;
    private int denom;

    public Number(int nom, int denom){
        this.nom = nom;
        this.denom = denom;
    }
    public Number(int nom){
        this.nom = nom;
        this.denom = 1;
    }

    public static Number getRandom(Random random, int min, int max){
        return new Number(random.nextInt(max-min) + min+1);
    }

    public static Number nil(){
        return new Number(0);
    }

    public void add(Number another, Number coef){
            this.add(coef.getProd(another));
    }

    public void add(Number another){
        this.add(another,1,1);
    }

    public void add(Number another, int coef, int coefAnother){
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

    public void sub(Number another){
        this.sub(another, 1,1);
    }

    public void sub(Number another, int coef, int coefAnother){
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

    public void mult(Number another){
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

    public Number getProd(Number another){
        int newNom = this.nom * another.nom;
        int newDen = this.denom * another.denom;

        if(newNom==0){
            return Number.nil();
        }
        return  new Number(newNom,newDen);
    }


    public void div(Number another){
        this.nom*= another.denom;
        this.denom*= another.nom;
        if (nom == denom){
            nom  =1;
            denom = 1;
        }
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
