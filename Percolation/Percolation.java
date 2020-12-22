/*
Name: Aryo Patel

Desc: This is my percolation class which handles the logic of creating and modifying the perc
*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int n;
    private WeightedQuickUnionUF unionArr;
    private int count;
    public Percolation(int n) {
        if (n >= 1) {
            this.n = n;
            count = 0;
            grid = new int[n][n];
            int temp  = 1;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = 0;
                    temp++;
                }
            }
        }
        else {
            throw new IllegalArgumentException("Enter an integer larger than 0");
        }
        unionArr = new WeightedQuickUnionUF(n*n + 2);
    }
    public void open(int row, int col) {

        // TOP ROOT node will be the node n*n, BOT ROOT node will be the node n*n + 1

        // This should catch all errors ideally, so below we don't need to worry about out of bounds
        if (row-1 >= n || row - 1 < 0 || col-1 >= n || col -1 < 0) {
            throw new IllegalArgumentException(
                    "Error in open. Make sure inputs are less than the size and greater than or equal to 1");
        }

        int usableRow = row-1;
        int usableCol = col -1;


        // set the index to 1 of the target node
        grid[usableRow][usableCol] = 1;
        count++;

        // if in first row, connect to root node
        if (usableRow == 0) {
            unionArr.union(usableCol, n*n);
        }

        // if in the lsat row, connect to the bottom root node
        if (usableRow == n-1) {
            unionArr.union(usableRow * n + usableCol, n*n + 1);
        }

        // check the connection above
        if (usableRow > 0) {
            if (isOpen(row - 1, col)) {
                unionArr.union(usableRow*n + usableCol, (usableRow-1)*n + usableCol);
            }
        }

        // check the connection to the left
        if (usableCol > 0) {
            if(isOpen(row, col -1)) {
                unionArr.union(usableRow*n + usableCol, (usableRow)*n + usableCol -1);
            }
        }

        // check the connection to the right
        if (usableCol < n -1) {
            if (isOpen(row, col + 1)) {
                unionArr.union(usableRow*n + usableCol, (usableRow)*n + usableCol +1);
            }
        }
        // check the connection below
        if (usableRow < n -1) {
            if (isOpen(row + 1, col)) {
                unionArr.union(usableRow*n + usableCol, (usableRow+1)*n + usableCol);
            }
        }




    }
    public boolean isOpen(int row, int col) {
        if (row-1 >= n || row - 1 < 0 || col-1 >= n || col -1 < 0) {
            throw new IllegalArgumentException("Error in isOpen. Make sure inputs are less than the size and greater than or equal to 1");
        }
        return grid[row -1][col-1] == 1;
    }
    public boolean isFull(int row, int col) {
        if (row-1 >= n || row - 1 < 0 || col-1 >= n || col -1 < 0) {
            throw new IllegalArgumentException("Error in isOpen. Make sure inputs are less than the size and greater than or equal to 1");
        }
        // return top node's parent equals this node's parent
        return unionArr.find((row-1) * n + col - 1) == unionArr.find( n * n);
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return unionArr.find(n*n +1) == unionArr.find(n*n);
    }

    public static void main(String[] args) {
        new Percolation(3);
        System.out.println("Program executed");
    }
}
