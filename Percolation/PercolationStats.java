/*
    Author: Aryo

    This is my creation of simulating many percolation tests to predict what the value of p needs to be in order to have a high liklihood of percolation
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.InputMismatchException;

public class PercolationStats {

    private int trials;

    private double[] thresholds;
    private double confidenceValue = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Make the size and the number of trials greater than zero");
        }
        else {
            this.trials = trials;
            thresholds = new double[this.trials];

            for(int i = 0; i < trials; i++) {
                Percolation trial = new Percolation(n);
                int count = 0;
                int iterCount = 0;
                // get the number of iterations that it took till percolation
                while (!trial.percolates()) {
                    int row;
                    int col;

                    do {

                        // random row
                        // random col

                        row = (int) StdRandom.uniform(1, n+1);
                        col = (int) StdRandom.uniform(1 , n+1);

                        // System.out.println("inside dowhile: " + row);
                        // System.out.println("inside dowhile: " + col);
                        //System.out.println(trial.isOpen(row, col));

                    } while(trial.isOpen(row, col));

                    // System.out.println(row);
                    // System.out.println(col);

                    trial.open(row, col);

                    // System.out.println("Outside the do while loop: " + trial.isOpen(row, col));
                    // System.out.println("Percolated status: " + trial.percolates());
                    // System.out.println("\n");
                    count++;
                }
                System.out.println("Executed trial " + i);
                double percentage = (double) count/(n*n);
                thresholds[i] = percentage;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;

        for (int i = 0; i < trials; i++) {
            sum += thresholds[i];
        }

        return (double) sum/trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = this.mean();
        double stdSum = 0;

        for (int i = 0; i < trials; i++) {
            stdSum += Math.pow(thresholds[i] - mean, 2);
        }

        return (double) stdSum/(trials -1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - confidenceValue*this.stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + confidenceValue*this.stddev()/Math.sqrt(trials);
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            throw new InputMismatchException("Provide exactly 2 input args: size, trials");
        }

        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(size, trials);

        System.out.println("Mean percolation threshold was " + stats.mean());
        System.out.println("The percolation standard deviation was " + stats.stddev());
        System.out.println("The lower confidence bound on the percolation threshold was " + stats.confidenceLo());
        System.out.println("The upper confidence bound on the percolation threshold was " + stats.confidenceHi());
    }
}
