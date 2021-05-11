import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

public class RandomSiteRowColGenerator 
{
    Percolation percolation;
    private ArrayList<String> pairs = new ArrayList<String>();
    private final int min = 1;
    private final int max;
    private final int latticeSize;

    /**
     *
     * @param n = side dimension of site to generate random # for
     * @param perc = Percolation object
     */
    public RandomSiteRowColGenerator(final int n, final Percolation perc)
    {
        max = n;
        percolation = perc;
        latticeSize = (max * max);
    }

    /**
     * @param num
     * @return String
     */
    //helper to quickly convert an int to string
    private String toStr(final int num)
    {
        return String.valueOf(num);
    }

    /**
     * @return int[]
     * @throws Exception
     */
    public final int[] generateRandomInteger() throws Exception
        {
            int randomRow = StdRandom.uniform(min, max+1);
            int randomCol = StdRandom.uniform(min, max + 1);
            int attempts = 0;
            while (pairs.contains(toStr(randomRow) + toStr(randomCol)) |
             percolation.isOpen(randomRow, randomCol)) {
                randomRow = StdRandom.uniform(min, max + 1);
                randomCol = StdRandom.uniform(min, max + 1);
                if (attempts > latticeSize) {
                    throw new Exception(String.format("Exceeded %s attempts",
                  latticeSize));
                }
                attempts += 1;
            }
            pairs.add(toStr(randomRow) + toStr(randomCol));

            return new int[] {randomRow, randomCol };
        }
    }