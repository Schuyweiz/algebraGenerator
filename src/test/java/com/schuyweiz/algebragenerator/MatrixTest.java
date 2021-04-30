package com.schuyweiz.algebragenerator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void multiplyRow(){
        ArrayList<Number> row1 = new ArrayList<>();
        row1.add(new Number(1));
        row1.add(new Number(2));
        ArrayList<Number> row2 = new ArrayList<>();
        row2.add(new Number(3));
        row2.add(new Number(4));

        Matrix matrix = new Matrix(
                new Row(row1),
                new Row(row2)
        );

        matrix.multRow(0,2);
        System.out.println(matrix.getRows().get(0).toString());

    }

    @Test
    void addMatrix() throws Exception {
        ArrayList<Number> row1 = new ArrayList<>();
        row1.add(new Number(1));
        row1.add(new Number(2));
        ArrayList<Number> row2 = new ArrayList<>();
        row2.add(new Number(3));
        row2.add(new Number(4));

        Matrix matrix = new Matrix(
                new Row(row1),
                new Row(row2)
        );
        ArrayList<Number> row3 = new ArrayList<>();
        row3.add(new Number(1));
        row3.add(new Number(2));
        ArrayList<Number> row4 = new ArrayList<>();
        row4.add(new Number(3));
        row4.add(new Number(4));

        Matrix matrix2 = new Matrix(
                new Row(row3),
                new Row(row4)
        );

        Matrix matrix3 = matrix.add(matrix2);
        System.out.println(matrix3.getRows().get(0).toString());
    }

    @Test
    void swaprows(){

        ArrayList<Number> row1 = new ArrayList<>();
        row1.add(new Number(1));
        row1.add(new Number(2));
        ArrayList<Number> row2 = new ArrayList<>();
        row2.add(new Number(3));
        row2.add(new Number(4));

        Matrix matrix = new Matrix(
                new Row(row1),
                new Row(row2)
        );

        matrix.swapRow(0,1);
        System.out.println(matrix.getRows().get(0).toString());

    }

}