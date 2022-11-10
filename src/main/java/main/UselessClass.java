package main;

import main.view.View;


public class UselessClass extends Object {
    private int column;
    private int row ;
    Object[][] matrix;

    public UselessClass (int c, int r){
        column=c;
        row = r;
        matrix = new Object[c][r];

    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Object[][] getMatrix() {
        return matrix;
    }
    public Object getMatrixItem(int c, int r){
        return matrix[c][r];
    }
    public void add(int c, int r, Object o){
        matrix[c][r]=o;
    }
    public void print(){
        for (int i=0 ; i < column; i++
        ){
            for (int j=0 ; j <row;j++
                 ) {
                if (this.getMatrixItem(i,j) != null)

                    System.out.println(this.getMatrixItem(i,j));

            }

        }

    }

}


