package com.schuyweiz.algebragenerator;

public class Rational {
    private long num;
    private long denum;

    public long getNum() {
        return num;
    }

    public long getDenum() {
        return denum;
    }

    public Rational(long num, long denum){
        this.num = num;
        this.denum = denum;
    }



    public static String toRational(double number) {

        double negligibleRatio = 0.000001;
        boolean isNegative = false;

        StringBuilder sb = new StringBuilder();

        if (number<0){
            sb.append("-");
            number*=-1;
            isNegative = true;
        }
        sb.append("\\frac{");
        for (int i = 1; ; i++) {
            double tem = number / (1D / i);
            if (Math.abs(tem - Math.round(tem)) < negligibleRatio) {
                if(i==1){
                    return String.valueOf(isNegative? (int)-number:(int)number);
                }
                sb.append(Math.round(tem)+"}{" + i+"}");
                break;
            }
        }
        return sb.toString();
    }
}
