package com.schuyweiz.algebragenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Matrix implements Cloneable{


    //region elementary operations
    public void multRow(int rowId, int coef){
        this.rows.get(rowId).mult(coef);
    }

    public void multRow(int rowId, Number coef){
        this.rows.get(rowId).mult(coef);
    }

    public void addRow(int fromId, int toId, int coef) throws Exception {
        this.rows.get(toId).add(this.rows.get(fromId), coef);
    }
    public void addRow(int fromId, int toId, Number coef) throws Exception {
        this.rows.get(toId).add(this.rows.get(fromId), coef);
    }

    public void divRow(int rowId, int coef){
        multRow(rowId,new Number(1,coef));
    }

    public void swapRow(int fromId, int toId){
        Row temp = this.rows.get(fromId);
        this.rows.set(fromId,this.rows.get(toId));
        this.rows.set(toId,temp);
    }

    //endregion

    public Matrix sub(Matrix another) throws Exception {
        Matrix newMatrix = new Matrix(rows);
        for (int i = 0; i < this.rows.size(); i++) {
            newMatrix.getRows().get(i).sub(another.rows.get(i));
        }
        return newMatrix;
    }

    public Matrix add(Matrix matrix) throws Exception {
        Matrix newMatrix = new Matrix(rows);
        for (int i = 0; i < this.rows.size(); i++) {
            newMatrix.rows.get(i).add(matrix.getRows().get(i));
        }
        return newMatrix;
    }

    public Matrix mult(Matrix matrix) throws Exception {
        matrix.convertRowsToCols();
        ArrayList<Row> newRows = new ArrayList<>();
        for (Row row:rows){
            ArrayList<Number> newRow = new ArrayList<>();
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
            ArrayList<Number> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(Number.getRandom(rand,boundL,boundR));
            }
            rows.add(new Row(row));
        }
        return new Matrix(rows);
    }

    public static Matrix identity(int width){
        ArrayList<Row> newMatrix = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<Number> newRow = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                newRow.add(i==j? new Number(1):new Number(0));
            }
            newMatrix.add(new Row(newRow));
        }
        return new Matrix(newMatrix);
    }

    public static Matrix diag(int width, ArrayList<Number> diagonal){
        Matrix matrix = identity(width);
        for (int i = 0; i < width; i++) {
            matrix.rows.get(i).mult(diagonal.get(i));
        }
        return matrix;
    }


    public int getHeight() {
        return height;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public Matrix(Row... rows){
        this.height = rows.length;
        this.width = rows[0].getSize();
        this.rows = new ArrayList<>();

        for (Row row:rows){
            try {
                this.rows.add((Row) row.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        convertRowsToCols();
    }


    public Matrix(ArrayList<Row> rows) {
        this.height = rows.size();
        this.width = rows.get(0).getSize();

        this.rows = new ArrayList<>();

        for (Row row:rows){
            try {
                this.rows.add((Row) row.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        convertRowsToCols();
    }

    private void convertRowsToCols(){
        ArrayList<Column> cols = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<Number> newCol = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                newCol.add(rows.get(j).get(i));
            }
            cols.add(new Column(newCol));
        }
        this.cols = cols;
    }



    private final ArrayList<Row> rows;
    private ArrayList<Column> cols;
    private final int width;
    private final int height;
}
