import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private final int sizeLattice;
  private double[] thresholds;
  private final int numTrials;
  private double mean;
  private double sharpness;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) throws Exception {
    if (n <= 0 || trials <= 0)
      throw new IllegalAccessError();
    else {
      numTrials = trials;
      sizeLattice = (n * n);
      thresholds = new double[trials];

      for (int i = 0; i < trials; i++) {
        try {
          Percolation perc = new Percolation(n);
          RandomSiteRowColGenerator rand = new RandomSiteRowColGenerator(n, perc);
          //while lattice is not percolating
          //continue opening new sites
          //until the lattice does percolate
          while (!perc.percolates()) {
            int[] pair = rand.GenerateRandomInteger();
            perc.open(pair[0], pair[1]);
          }
          //once lattice percolates add threshold to thresholds
          int open = getOpenSites(perc);
          thresholds[i] = (double) open / (double) (n * n);
          //System.out.println("finished "+thresholds[i]);
        } catch (Exception e) {
          if (e.getMessage().contains("Exceeded") & e.getMessage().contains("attempts")) {
            i--;
            continue;
          } else
            throw e;
        }
      }
      CalculateSharpness();
      mean = mean();
    }
  }

  private int getSideDimension() throws Exception
    {
        return (int) Math.sqrt(sizeLattice);
    }
  
  private int[] getRowColumn(int index) throws Exception
    {
        int side = getSideDimension();
        int row = index % side == 0 ? (int) Math.ceil(index / side) : 
            (int) Math.ceil(index / side)+1;
        int col = index % side == 0 ? side : index % side;
        return new int[] { row, col };
    }

  private int getOpenSites(Percolation perc) throws Exception
  {
      int openSites = 0;
      for (int i = 1; i < sizeLattice+1; i++) {
          if (perc.isOpen(getRowColumn(i)[0], getRowColumn(i)[1])) {
              openSites += 1;
          }
      }
      
      return openSites;
  }
  
  //Calculate the 'sharpness' coefficient of a dataset
  private void CalculateSharpness()
  {
    double rollingSum = 0.0;
    for (double d : thresholds) {
      rollingSum += Math.pow((d - mean), 2);
    }
    sharpness = Math.sqrt(rollingSum / (numTrials - 1));
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(thresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(thresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean - ((1.96 * sharpness) / Math.sqrt(numTrials));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean + ((1.96 * sharpness) / Math.sqrt(numTrials));
  }

 // test client (see below)
 public static void main(String[] args) throws NumberFormatException, Exception {
    PercolationStats percStats = 
        new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    // String[] newArgs = new String[] { "10", "10" };
    // PercolationStats percStats = 
    //     new PercolationStats(Integer.parseInt(newArgs[0]), Integer.parseInt(newArgs[1]));
    String[] headers = { "mean", "stddev", "95% confidence interval" };
    String meanStr = String.format("%-24s= %2s",headers[0],percStats.mean());
    String stddevStr = String.format("%-24s= %2s",headers[1],percStats.stddev());
    String confIntervalStr = String.format("%-24s= [%2s, %3s]", headers[2], 
    percStats.confidenceLo(), percStats.confidenceHi());
    System.out.println(meanStr);
    System.out.println(stddevStr);
    System.out.println(confIntervalStr);
 }
}