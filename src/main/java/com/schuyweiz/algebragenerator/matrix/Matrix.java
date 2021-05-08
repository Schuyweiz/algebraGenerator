package com.schuyweiz.algebragenerator.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;


public class Matrix implements Cloneable{


    //region elementary operations on rows

    public void multRow(int rowId, IExpr coef){
        var temp = this.rows.get(rowId).mult(coef);
        rows.set(rowId,temp);
        this.updateCols(rowId);
    }

    public void addRow(int fromId, int toId, IExpr coef) {
        var temp = this.rows.get(toId).add(this.rows.get(fromId), coef);
        rows.set(toId,temp);
        this.updateCols(toId);
    }

    public void divRow(int rowId, IExpr coef){
        IExpr e = F.Power(coef,IntegerSym.valueOf(-1));
        multRow(rowId, e);
        this.updateCols(rowId);
    }

    public void swapRow(int fromId, int toId){
        Row temp = this.rows.get(fromId);
        this.rows.set(fromId,this.rows.get(toId));
        this.rows.set(toId,temp);
        updateCols(fromId);
        updateCols(toId);
    }
    //endregion

    //region elementary operations on columns

    public void multCol(int colId, IExpr coef){
        var temp = cols.get(colId).mult(coef);
        cols.set(colId,temp);
        updateRows(colId);
    }
    public void addCol(int from, int to, IExpr coef) throws Exception {
        var temp = cols.get(to).add(cols.get(from), coef);
        cols.set(to,temp);
        updateRows(to);
    }
    public void divCol(int at, IExpr coef){
        IExpr e = F.Power(coef,IntegerSym.valueOf(-1));
        multCol(at,e);
        updateRows(at);

    }
    public void swapCol(int from, int to){
        var temp = cols.get(from);
        cols.set(from,cols.get(to));
        cols.set(to, temp);
        updateRows(to);
        updateRows(from);
    }

    //endregion

    //region matrix basic operations
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

    public Matrix mult(Matrix matrix) {
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

    //endregion

    //region static matrix generators
    public static Matrix randomMatrix(Random rand, int boundL, int boundR, int height, int width){
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(ExprUtils.getPositiveRandom(rand,boundL,boundR));
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

    //endregion


    public int getHeight() {
        return height;
    }
    public ArrayList<Row> getRows() {
        return rows;
    }
    public IExpr get(int row, int col){
        return this.rows.get(row).get(col);
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

    private void setCol(int i, int j, IExpr newValue){
        this.cols.get(j).set(i,newValue);
    }
    private void setRow(int i, int j, IExpr newValue){
        this.rows.get(i).set(j,newValue);
    }

    private void updateCols(int at){
        for (int colId = 0; colId < cols.size(); colId++) {
                setCol(at,colId,
                        rows.get(at).get(colId));

        }
    }
    private void updateRows(int at){
        for (int i = 0; i < rows.size(); i++) {
            setRow(i,at,cols.get(at).get(i));
        }
    }

    @Override
    public String toString(){
        StringBuilder s= new StringBuilder();
        for (Row row:rows){
            s.append(row.toString()).append("\n");
        }
        return s.toString();
    }

    public Matrix strongShuffle(Random rand, int left, int right){
        var inverseMatrix = new Matrix(this.rows);

        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            order.add(i);
        }
        for (int i = 0; i < height; i++) {
            Collections.swap(order, rand.nextInt(height), rand.nextInt(height));
        }
        int first = order.get(0);
        for (int i = 0; i < height - 2; i++) {
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);

            this.multCurrent(
                    elementaryOpAdd(first, order.get(i+1),coef,width)
            );

            inverseMatrix = elementaryOpAdd(first, order.get(i+1),coef.negative(),width).mult(inverseMatrix);

        }

        int second = order.get(height-1);

        for (int i = 0; i < height - 2; i++) {
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);

            this.multCurrent(
                    elementaryOpAdd(second, order.get(i),coef,width)
            );
            inverseMatrix = elementaryOpAdd(second, order.get(i),coef.negative(),width).mult(inverseMatrix);
        }

        int nonZero = 0;
        for (Column col : cols) {
            if (col.isWeak()) {
                for (int j = 0; j < col.getSize(); j++) {
                    if (!col.get(j).equals(IntegerSym.valueOf(0))) {
                        nonZero = j;
                        break;
                    }
                }
            }
        }
        IExpr tempCoef = ExprUtils.getRandomNonNull(rand,left, right);
        int tempId = getId(height, nonZero,rand);
        this.multCurrent(
                elementaryOpAdd(nonZero,tempId,tempCoef,width)
        );

        inverseMatrix = elementaryOpAdd(nonZero, tempId, tempCoef.negative(),width).mult(inverseMatrix);

        for (int i = 0; i < height; i++) {
            int id = getId(height, i,rand);
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);

            this.multCurrent(
                    elementaryOpAdd(i,id, coef,width)
            );
            inverseMatrix = elementaryOpAdd(i, id, coef.negative(),width).mult(inverseMatrix);

        }
        return inverseMatrix;
    }

    public void simpleShuffle(Random rand, int left, int right){
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            order.add(i);
        }
        for (int i = 0; i < height; i++) {
            Collections.swap(order, rand.nextInt(height), rand.nextInt(height));
        }

        int first = order.get(0);
        for (int i = 0; i < height - 2; i++) {
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);
            this.addRow(first, order.get(i),coef);
        }

        int second = order.get(height-1);

        for (int i = 0; i < height - 2; i++) {
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);
            this.addRow(second, order.get(i),coef);
        }

        int nonZero = 0;
        for (Column col : cols) {
            if (col.isWeak()) {
                for (int j = 0; j < col.getSize(); j++) {
                    if (!col.get(j).equals(IntegerSym.valueOf(0))) {
                        nonZero = j;
                        break;
                    }
                }
            }
        }
        IExpr tempCoef = ExprUtils.getRandomNonNull(rand,left, right);
        int tempId = getId(height, nonZero,rand);
        this.addRow(nonZero,tempId,tempCoef);

        for (int i = 0; i < height; i++) {
            int id = getId(height, i,rand);
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);
            addRow(order.get(i), id, coef);
        }

        for (int i = 0; i < height; i++) {
            int id = getId(height, i,rand);
            swapRow(order.get(i), id);
        }
    }


    private Matrix elementaryOpAdd(int from, int to, IExpr coef, int size){
        var identity = Matrix.identity(size);
        identity.addRow(from, to, coef);
        return identity;
    }

    private int getId(int size, int current, Random random){
        return (current + random.nextInt(size-1)+1)%size;
    }

    private void multCurrent(Matrix matrix){
        for (int i = 0; i < rows.size(); i++) {
            ArrayList<IExpr> row = new ArrayList<>();
            for (Column col : matrix.cols) {
                IExpr val = rows.get(i).mult(col);
                row.add(val);
            }
            this.rows.set(i,new Row(row));
        }
        for (int i = 0; i < cols.size(); i++) {
            updateCols(i);
        }
    }


    private final ArrayList<Row> rows;
    private ArrayList<Column> cols;
    private final int width;
    private final int height;
}
