/*************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats
 *  Dependencies: StdOut.java
 *
 *************************************************************************/

public class PercolationStats {
   
   private Percolation p;

   public PercolationStats(int N, int T)    // perform T independent experiments on an N-by-N grid
   {
       if (N <= 0 || T <=0) {
            throw new java.lang.IllegalArgumentException(
                "N and T must be larger than 0"
            );
       }
    
   }     
                
   public double mean()                      // sample mean of percolation threshold
   {
   return -1;
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
   return -1;
   }
   public double confidenceLow()             // low  endpoint of 95% confidence interval
   {
   return -1;
   }
   public double confidenceHigh()            // high endpoint of 95% confidence interval
   {
   return -1;
   }
   
   public static void main(String[] args)    // test client (described below)
   { 
       int N = 1800;
       int T = 5;
       int x = 0;
       int y = 0;
       double[] times = new double[T];
       Percolation p = new Percolation(N);
       
       for (int i = 1; i <= T; i++) {
           Stopwatch s = new Stopwatch();
           while (!p.percolates()){
               x = StdRandom.uniform(1,N+1);
               y = StdRandom.uniform(1,N+1);
               p.open(x,y);
               //StdOut.printf("%s %s\n", x, y);
               if (p.percolates()) {
                   times[i] = s.elapsedTime();
                   StdOut.printf("Time: %s\n", times[i]);                    
               }
       } //while
       } //for
       StdOut.println("mean: \t" + StdStats.mean(times));
       } //main
}