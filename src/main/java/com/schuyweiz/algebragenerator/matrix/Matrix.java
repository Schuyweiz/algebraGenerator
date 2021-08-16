package com.schuyweiz.algebragenerator.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.schuyweiz.algebragenerator.utility.ExprUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.FractionSym;
import org.matheclipse.core.expression.IntegerSym;
import org.matheclipse.core.interfaces.IExpr;


public class Matrix implements Cloneable{

    @SuppressWarnings(value = "all")
    public static Matrix ofRank(int width, int rank, Random random) {
        return Matrix.ofRank(width, width, rank, random);
    }


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


    public void swapRow(int fromId, int toId){
        Row temp = this.rows.get(fromId);
        this.rows.set(fromId,this.rows.get(toId));
        this.rows.set(toId,temp);
        updateCols(fromId);
        updateCols(toId);
    }
    //endregion


    //endregion

    //region matrix basic operations
    public Matrix sub(Matrix another)  {
        ArrayList<Row> newRows = new ArrayList<>();
        for (int i = 0; i < this.rows.size(); i++) {
            newRows.add(getRows().get(i).sub(another.rows.get(i)));
        }
        return new Matrix(newRows);
    }

    public Matrix add(Matrix another)  {
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

    public static Matrix diag(ArrayList<IExpr> diagonal){
        int width = diagonal.size();
        Matrix matrix = identity(width);
        for (int i = 0; i < width; i++) {
            var temp = matrix.rows.get(i).mult(diagonal.get(i));
            matrix.rows.set(i,temp);
        }
        return matrix;
    }

    public static Matrix randDiag(int width, Random rand){
        ArrayList<IExpr> diagonal = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            diagonal.add(ExprUtils.getPositiveRandom(rand,-3,3));
        }
        return Matrix.diag(diagonal);
    }

    public static Matrix orthogonal(Random rand, ArrayList<Integer> order, int n){

        for (int i = 0; i < order.size(); i++) {
            Collections.swap(order, rand.nextInt(order.size()), rand.nextInt(order.size()));
        }

        int a = order.get(0);
        int b = order.get(1);
        int c = order.get(2);
        int d = order.get(3);

        ArrayList<Row> rows = new ArrayList<Row>();
        ArrayList<IExpr> row1 = new ArrayList<>();
        row1.add(FractionSym.valueOf(a*a+b*b-c*c-d*d,n));
        row1.add(FractionSym.valueOf(2*(-a*d+b*c),n));
        row1.add(FractionSym.valueOf(2*(a*c+b*d),n));


        ArrayList<IExpr> row2 = new ArrayList<>();
        row2.add(FractionSym.valueOf(2*(a*d+b*c),n));
        row2.add(FractionSym.valueOf(a*a-b*b+c*c-d*d,n));
        row2.add(FractionSym.valueOf(2*(-a*b + c*d),n));

        ArrayList<IExpr> row3 = new ArrayList<>();
        row3.add(FractionSym.valueOf(2*(-a*c+b*d),n));
        row3.add(FractionSym.valueOf(2*(a*b+c*d),n));
        row3.add(FractionSym.valueOf(a*a-b*b-c*c+d*d,n));

        rows.add(new Row(row1));
        rows.add(new Row(row2));
        rows.add(new Row(row3));


        return new Matrix(rows);
    }

    public static Matrix ofRank(int width,int height, int rank, Random rand){
        ArrayList<Row> rows = new ArrayList<>();
        int indent = 0;
        int remains = rank;

        for (int i = 0; i < height; i++) {
            ArrayList<IExpr> exprs = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (j>=indent && i<rank)
                    exprs.add(ExprUtils.getRandomNonNull(rand,1,5));
                else
                    exprs.add(IntegerSym.valueOf(0));
            }
            remains--;
            rows.add(new Row(exprs));
            indent += (rand.nextInt(height-indent-remains)+1);
        }

        return new Matrix(rows);
    }

    //endregion

    public Matrix transpose(){
        ArrayList<Row> rows = new ArrayList<>();
        for (Column col: cols){
            rows.add(col.toRow());
        }
        return new Matrix(rows);
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

    private void updateCols(int at){
        for (int colId = 0; colId < cols.size(); colId++) {
                setCol(at,colId,
                        rows.get(at).get(colId));

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

    public Matrix strongShuffle(Random rand, int left, int right, int cycles){
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
        for (int i = 0; i < height; i++) {
            if (i!=nonZero){
                IExpr tempCoef = IntegerSym.valueOf(rand.nextBoolean()?1:-1);
                this.multCurrent(
                        elementaryOpAdd(i,nonZero,tempCoef,width)
                );

                inverseMatrix = elementaryOpAdd(i, nonZero, tempCoef.negative(),width).mult(inverseMatrix);
            }
        }

        for (int i = 0; i < height*cycles; i++) {
            int id = getRandomId(height, i,rand);
            IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);

            this.multCurrent(
                    elementaryOpAdd(i%height,id, coef,width)
            );
            inverseMatrix = elementaryOpAdd(i%height, id, coef.negative(),width).mult(inverseMatrix);

        }
        return inverseMatrix;
    }

    public Matrix weakShuffle(Random rand, int left, int right){
        var inverseMatrix = new Matrix(this.rows);

        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            order.add(i);
        }
        for (int i = 0; i < height; i++) {
            Collections.swap(order, rand.nextInt(height), rand.nextInt(height));
        }

        IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);

        this.multCurrent(
                elementaryOpAdd(order.get(0), order.get(1),coef,width)
        );

        inverseMatrix = elementaryOpAdd(order.get(0), order.get(1),coef.negative(),width).mult(inverseMatrix);


        coef = ExprUtils.getRandomNonNull(rand,left, right);

        this.multCurrent(
                elementaryOpAdd(order.get(order.size()-1), order.get(order.size()-2),coef,width)
        );

        inverseMatrix = elementaryOpAdd(order.size()-1, order.size()-2,coef.negative(),width).mult(inverseMatrix);

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

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                if (i!=j){
                    IExpr coef = ExprUtils.getRandomNonNull(rand,left, right);
                    addRow(order.get(i), order.get(j), coef);
                }

            }
        }

        for (int i = 0; i < height; i++) {
            int id = getRandomId(height, i,rand);
            swapRow(order.get(i), id);
        }
    }


    private Matrix elementaryOpAdd(int from, int to, IExpr coef, int size){
        var identity = Matrix.identity(size);
        identity.addRow(from, to, coef);
        return identity;
    }

    private int getRandomId(int size, int current, Random random){
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
