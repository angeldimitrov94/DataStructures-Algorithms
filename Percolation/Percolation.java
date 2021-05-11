import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    //#region Data Structures fields
    private WeightedQuickUnionUF wquf;
    //1 = blocked
    //0 = open.
    private int[] lattice;
    private int lengthWithoutVP;
    private int lengthWithVP;
    //#endregion

    //#region Type of Site fields
    //constants defining type of site 
    //in terms of location within lattice
    private final int INNER = 0;
    private final int EDGE_RIGHT = 1;
    private final int EDGE_LEFT = 2;
    private final int EDGE_TOP = 3;
    private final int EDGE_BOTTOM = 4;
    private final int CORNER_LEFT_TOP = 5;
    private final int CORNER_LEFT_BOTTOM = 6;
    private final int CORNER_RIGHT_TOP = 7;
    private final int CORNER_RIGHT_BOTTOM = 8;
    //#endregion

    //#region Type of neighbor fields
    //constants defining type of neighbor
    private final int TOP = 0;
    private final int RIGHT = 1;
    private final int BOTTOM = 2;
    private final int LEFT = 3;
    ////#endregion

    //#region Helpers
    //get nxn side dimension of lattice
    private int getSideDimension() throws Exception
    {
        if (lattice != null) {
            return (int) Math.sqrt(lengthWithoutVP);
        } else {
            throw new Exception("Lattice not initialized");
        }
    }

    private int[] getRowColumn(int index) throws Exception
    {
        int side = getSideDimension();
        int row = index % side == 0 ? (int) Math.ceil(index / side) : 
            (int) Math.ceil(index / side)+1;
        int col = index % side == 0 ? side : index % side;
        return new int[] { row, col };
    }

    private int getIndex(int row, int col) throws Exception
    {
        int side = getSideDimension();
        return side * (row - 1) + col;
    }
    
    private void validateRowColumn(int row, int col) throws Exception
    {
        int side = getSideDimension();
        if (row <= 0 || col <= 0 || row > side || col > side)
            throw new IllegalArgumentException();
    }
        
    private int getLocation(int row, int col) throws Exception
    {
        validateRowColumn(row, col);
        try {
            int sideDimension = getSideDimension();
            if (row == 1 && col != 1 && col != sideDimension)
                return EDGE_TOP;
            else if (col == 1 && row != 1 && row != sideDimension)
                return EDGE_LEFT;
            else if (row == sideDimension && col != 1 && col != sideDimension)
                return EDGE_BOTTOM;
            else if (col == sideDimension && row != 1 && row != sideDimension)
                return EDGE_RIGHT;
            else if (row == 1 && col == 1)
                return CORNER_LEFT_TOP;
            else if (row == 1 && col == sideDimension)
                return CORNER_RIGHT_TOP;
            else if (row == sideDimension && col == 1)
                return CORNER_LEFT_BOTTOM;
            else if (row == sideDimension && col == sideDimension)
                return CORNER_RIGHT_BOTTOM;
            else
                return INNER;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
    }
    //#endregion

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws Exception {
        if (n <= 0)
            throw new IllegalArgumentException("n is less than or equal to 0 - illegal value");
        else {
            //n+2 because of the 2 virtual top and bottom sites
            //n=0 is top virtual site
            //n=n is bottom virtual site
            //lattice is 1-indexed because of this
            lengthWithoutVP = n*n;
            lengthWithVP = lengthWithoutVP + 2;
            
            wquf = new WeightedQuickUnionUF(lengthWithVP);
            lattice = new int[lengthWithoutVP];
            //fill lattice with 1s representing all blocked sites
            for (int i = 0; i < lattice.length; i++) {
                lattice[i] = 1;
            }
            MakeVirtualConnections();
        }
    }
    
    private void MakeVirtualConnections() throws Exception
    {
        try {
            int sideDimension = getSideDimension();
            for (int i = 1; i <= sideDimension; i++) {
                //0 is top virtual site
                //connect it to first row of sites
                wquf.union(0, i);
                //n is bottom virtual site
                //connect it to bottom row of sites
                wquf.union(lengthWithVP - 1, lengthWithVP - 1 - i);
            }
        } catch (Exception e) {
            throw e;
        }

    }
    
    private void PerformUnionOnNeighbors(int row, int col, boolean top, boolean right, boolean bottom, boolean left) throws Exception
    {
        validateRowColumn(row, col);
        if (top)
        {
            if (lattice[getIndex((row - 1), col) - 1] == 0)
            {
                wquf.union(getIndex(row, col), getIndex((row - 1), col));
            }
        }
        if (right)
        {
            if (lattice[getIndex(row, (col + 1)) - 1] == 0)
            {
                wquf.union(getIndex(row, col), getIndex(row, (col + 1)));
            }
        }
        if (bottom)
        {
            if (lattice[getIndex((row + 1), col) - 1] == 0)
            {
                wquf.union(getIndex(row, col), getIndex((row + 1), col));
            }
        }
        if (left)
        {
            if (lattice[getIndex(row, (col - 1)) - 1] == 0)
            {
                wquf.union(getIndex(row, col), getIndex(row, (col - 1)));
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws Exception {
        if (lattice[getIndex(row, col)-1] != 0)
        {
            lattice[getIndex(row, col) - 1] = 0;
            switch (getLocation(row, col)) {
                case INNER:
                    PerformUnionOnNeighbors(row, col, true, true, true, true);
                    break;
                case EDGE_RIGHT:
                    PerformUnionOnNeighbors(row, col, true, false, true, true);
                    break;
                case EDGE_LEFT:
                    PerformUnionOnNeighbors(row, col, true, true, true, false);
                    break;
                case EDGE_TOP:
                    PerformUnionOnNeighbors(row, col, false, true, true, true);
                    break;
                case EDGE_BOTTOM:
                    PerformUnionOnNeighbors(row, col, true, true, false, true);
                    break;
                case CORNER_LEFT_BOTTOM:
                    PerformUnionOnNeighbors(row, col, true, true, false, false);
                    break;
                case CORNER_LEFT_TOP:
                    PerformUnionOnNeighbors(row, col, false, true, true, false);
                    break;
                case CORNER_RIGHT_BOTTOM:
                    PerformUnionOnNeighbors(row, col, true, false, false, true);
                    break;
                case CORNER_RIGHT_TOP:
                    PerformUnionOnNeighbors(row, col, false, false, true, true);
                    break;
                default:
                    break;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws Exception {
        validateRowColumn(row, col);
        return lattice[getIndex(row, col)-1] == 0 ? true : false;
	}

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws Exception {
        validateRowColumn(row, col);
        return wquf.find(getIndex(row, col)) == wquf.find(0)? true : false;
	}

    // returns the number of open sites
    public int numberOfOpenSites() {
        return wquf.count();
	}

    // does the system percolate?
    public boolean percolates() {
        //if top virtual site (0) 
        //and bottom virtual site (lengthWithVP)
        //have same canonical element, then there is a 
        //top-to-bottom connection and percolates
        if (wquf.find(0) == wquf.find(lengthWithVP - 1))
            return true;
        else
            return false;
    }
    
    private void ShowImageOfSites() throws Exception
    {
        int side = getSideDimension();
        System.out.print("   ");
        for (int i = 1; i <= side; i++) {
            System.out.print(i + " ");
        }
        int row = 1;
        int col = 1;
        for (int i = 1; i <= lattice.length; i++) {
            row = getRowColumn(i)[0];
            col = getRowColumn(i)[1];
            if ((col-1) % side == 0 | i == 1) {
                System.out.println();
                int colNumLen = String.valueOf(col).length();
                System.out.print(col+" ".repeat(3-colNumLen));
                if (isFull(row, col))
                    System.out.print("○ ");
                else if(lattice[i-1]==0)
                    System.out.print("¤ ");
                else
                    System.out.print("◙ ");
            } else {
                if (isFull(row, col))
                    System.out.print("○ ");
                else if(lattice[i-1]==0)
                    System.out.print("¤ ");
                else
                    System.out.print("◙ ");
            }
        }
        System.out.println();
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc;
        try {
            int n = 5;
            perc = new Percolation(n);
            int attempt = 1;
            RandomSiteRowColGenerator rand = new RandomSiteRowColGenerator(n, perc);
            perc.ShowImageOfSites();
            while (!perc.percolates()) {
                System.out.println("not percolating...attempt : " + attempt + " ...number of sites open : "
                        + (n * n - attempt + 1));
                int[] pair = rand.GenerateRandomInteger();
                perc.open(pair[0], pair[1]);
                perc.ShowImageOfSites();
                attempt += 1;
                if(attempt>perc.wquf.count()) 
                    throw new Exception("No solution found");
            }
            System.out.println("system percolating...attempt : " + attempt + " ...number of sites open : "
                    + (n * n - attempt + 1));
            perc.ShowImageOfSites();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}