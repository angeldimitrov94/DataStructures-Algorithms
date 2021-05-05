public class PercolationStats {

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0)
      throw new IllegalAccessError();
    else {
      //something
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return 0.9;}

  // sample standard deviation of percolation threshold
  public double stddev() {
    return 0.0;}

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return 0.0;}

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return 0.0;}

 // test client (see below)
  public static void main(String[] args) {
   
 }
}