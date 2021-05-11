import java.util.ArrayList;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class RandomSiteRowColGenerator 
{
    Percolation percolation;
    private ArrayList<String> pairs = new ArrayList<String>();
    private int min;
    private int max;

    public RandomSiteRowColGenerator(int minimum, int maximum, Percolation perc)
    {
        min = minimum;
        max = maximum;
        percolation = perc;
    }
    
    //helper to quickly convert an int to string
    private String toStr(int num)
    {
        return String.valueOf(num);
    }

    public int[] GenerateRandomInteger() throws Exception
        {
            int randomRow = StdRandom.uniform(min, max+1);
            int randomCol = StdRandom.uniform(min, max + 1);
            int attempts = 0;
            while (pairs.contains(toStr(randomRow) + toStr(randomCol)) | percolation.isOpen(randomRow, randomCol)) {
                randomRow = StdRandom.uniform(min, max + 1);
                randomCol = StdRandom.uniform(min, max + 1);
                if(attempts>percolation.lattice.length) 
                    throw new Exception(String.format("Exceeded %s attempts", percolation.lattice.length));
                attempts += 1;
            }
            pairs.add(toStr(randomRow) + toStr(randomCol));

            return new int[] { randomRow, randomCol };
        }
    }