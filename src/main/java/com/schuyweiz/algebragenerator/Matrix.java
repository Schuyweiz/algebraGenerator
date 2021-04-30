package com.schuyweiz.algebragenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Matrix {


    public void multRow(int rowId, int coef){
        this.rows.get(rowId).mult(coef);
    }

    public Matrix sub(Matrix matrix) throws Exception {
        ArrayList<Row> newMatrix = new ArrayList<>();
        for (int i = 0; i < this.rows.size(); i++) {
            newMatrix.add(this.rows.get(i).sub(matrix.rows.get(i)));
        }
        return new Matrix(newMatrix);
    }

    public Matrix add(Matrix matrix) throws Exception {
        ArrayList<Row> newMatrix = new ArrayList<>();
        for (int i = 0; i < this.rows.size(); i++) {
            newMatrix.add(this.rows.get(i).add(matrix.rows.get(i)));
        }
        return new Matrix(newMatrix);
    }

    public Matrix mult(Matrix matrix) throws Exception {
        ArrayList<Row> newRows = new ArrayList<>();
        for (Row row:rows){
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int i = 0; i < matrix.width; i++) {
                newRow.add(row.mult(matrix.cols.get(i)));
            }
            newRows.add(new Row(newRow));
        }
        return new Matrix(newRows);
    }

    public static Matrix randomMatrix(Random rand, int boundL, int boundR, int height, int width){
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(rand.nextInt(boundR - boundL) + boundL + 1);
            }
            rows.add(new Row(row));
        }
        return new Matrix(rows);
    }

    private final ArrayList<Row> rows;
    private ArrayList<Column> cols;
    private final int width;
    private final int height;

    public int getHeight() {
        return height;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public Matrix(Row... rows){
        this.height = rows.length;
        this.width = rows[0].getSize();

        this.rows = (ArrayList<Row>) Arrays.stream(rows).collect(Collectors.toList());
        convertRowsToCols();
    }


    public Matrix(ArrayList<Row> rows) {
        this.height = rows.size();
        this.width = rows.get(0).getSize();

        this.rows = rows;
        convertRowsToCols();
    }





    private void convertRowsToCols(){
        ArrayList<Column> cols = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<Integer> newCol = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                newCol.add(rows.get(j).get(i));
            }
            cols.add(new Column(newCol));
        }
        this.cols = cols;
    }
}
