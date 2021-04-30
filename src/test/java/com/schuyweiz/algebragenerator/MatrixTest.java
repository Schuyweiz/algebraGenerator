package com.schuyweiz.algebragenerator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void multiplyRow(){
        ArrayList<Integer> row1 = new ArrayList<>();
        row1.add(1);
        row1.add(2);
        ArrayList<Integer> row2 = new ArrayList<>();
        row2.add(3);
        row2.add(4);

        Matrix matrix = new Matrix(
                new Row(row1),
                new Row(row2)
        );

        matrix.multRow(0,2);
        System.out.println(matrix.getRows().get(0).toString());

    }

}