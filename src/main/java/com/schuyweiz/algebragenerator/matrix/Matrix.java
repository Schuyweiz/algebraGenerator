package com.schuyweiz.algebragenerator.matrix;

import java.util.ArrayList;
import java.util.Random;

import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;


public class Matrix implements Cloneable{


    //region elementary operations
    public void multRow(int rowId, int coef){
        var temp = this.rows.get(rowId).mult(coef);
        rows.set(rowId,temp);
    }

    public void multRow(int rowId, IExpr coef){
        var temp = this.rows.get(rowId).mult(coef);
        rows.set(rowId,temp);
    }

    public void addRow(int fromId, int toId, int coef) throws Exception {
        var temp = this.rows.get(toId).add(this.rows.get(fromId), coef);
        rows.set(toId,temp);
    }
    public void addRow(int fromId, int toId, IExpr coef) throws Exception {
        var temp = this.rows.get(toId).add(this.rows.get(fromId), coef);
        rows.set(toId,temp);
    }

    public void divRow(int rowId, int coef){
        IExpr e = F.Power(IntegerSym.valueOf(coef),IntegerSym.valueOf(-1));
        multRow(rowId, e);
    }

    public void swapRow(int fromId, int toId){
        Row temp = this.rows.get(fromId);
        this.rows.set(fromId,this.rows.get(toId));
        this.rows.set(toId,temp);
    }

    //endregion

    public Matrix sub(Matrix another) throws Exception {
        ArrayList<Row> newRows = new ArrayList<>();
        for (int i = 0; i < this.rows.size(); i++) {
            newRows.add(getRows().get(i).sub(another.rows.get(i)));
        }
        return new Matrix(newRows);
    }

    public Matrix add(Matrix another) throws Exception {
        ArrayList<Row> newRows = new ArrayList<>();
        for (int i = 0; i < this.rows.size(); i++) {
            newRows.add(getRows().get(i).add(another.rows.get(i)));
        }
        return new Matrix(newRows);
    }

    public Matrix mult(Matrix matrix) throws Exception {
        matrix.convertRowsToCols();
        ArrayList<Row> newRows = new ArrayList<>();
        for (Row row:rows){
            ArrayList<IExpr> newRow = new ArrayList<>();
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
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(ExprUtils.getRandom(rand,boundL,boundR));
            }
            rows.add(new Row(row));
        }
        return new Matrix(rows);
    }

    public static Matrix identity(int width){
        ArrayList<Row> newMatrix = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<IExpr> newRow = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                newRow.add(i==j? IntegerSym.valueOf(1):IntegerSym.valueOf(0));
            }
            newMatrix.add(new Row(newRow));
        }
        return new Matrix(newMatrix);
    }

    public static Matrix diag(int width, ArrayList<IExpr> diagonal){
        Matrix matrix = identity(width);
        for (int i = 0; i < width; i++) {
            var temp = matrix.rows.get(i).mult(diagonal.get(i));
            matrix.rows.set(i,temp);
        }
        return matrix;
    }


    public int getHeight() {
        return height;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public IExpr get(int row, int col){
        return this.rows.get(row).get(col);
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
            ArrayList<IExpr> newCol = new ArrayList<>();
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
