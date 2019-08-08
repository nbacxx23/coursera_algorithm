import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    private double[] fraction;
    private Percolation percolation;
    private int trial;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        fraction = new double[trials];
        trial = trials;
        for (int t=0;t<trials;t++){
            percolation = new Percolation(n);
            while (!percolation.percolates()){
                int row = StdRandom.uniform(n+1);
                int col = StdRandom.uniform(n+1);
                if (row == 0 || col==0) {
                    continue;
                }
                if (((row-1)*n+col)!=0 && !percolation.isOpen(row,col)){
                    percolation.open(row,col);
                }
            }
            double frc = (double) percolation.numberOfOpenSites()/(n*n);
            fraction[t] = frc;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()- 1.96*Math.sqrt(stddev())/Math.sqrt(trial);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+ 1.96*Math.sqrt(stddev())/Math.sqrt(trial);
    }

   // test client (see below)
   public static void main(String[] args){
       PercolationStats stats = new PercolationStats(200,100);
       System.out.println("mean        = "+stats.mean());
       System.out.println("stddev        = "+stats.stddev());
       System.out.println("95% confidence interval     = "+stats.confidenceLo()+" , "+stats.confidenceHi());
   }

}