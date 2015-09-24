public class Percolation {
  
  private int rowLen;
  private int topIndex;
  private int bottomIndex;
  private int gridSize;
  private boolean[] grid;
  private boolean percolates;
  
  private WeightedQuickUnionUF uf;

  public Percolation(int N)               // create N-by-N grid, with all sites blocked
    { 
    if (N <= 0) {
            throw new java.lang.IllegalArgumentException(
                "N must be larger than 0"
            );
        }
        rowLen = N;
        gridSize = N*N;
        uf = new WeightedQuickUnionUF(gridSize + 2);
        grid = new boolean[gridSize];
        topIndex = gridSize;
        bottomIndex = gridSize + 1;
   }
  
   public void open(int iOne, int jOne)          // open site (row i, column j) if it is not open already
   {
     checkInput(iOne, jOne);

        // Change indexes to start at 1, not 0
        int i = iOne - 1;
        int j = jOne - 1;

        int index = getIndex(i, j);

        if (!grid[index]) {
          
            grid[index] = true;

            // If the spot we just opened has any open neighbors, connect them
            int n; // Neighbor's index
            boolean hasN = false;
            for (int d = 0; d < 4; d++) {
                n = getNeighborIndex(i, j, d);
                if (n != -1  && isOpen(n)) {
                    uf.union(index, n);
                    hasN = true;
                }
          }

            // If it is in the top row, connect it with the top node
            if (i == 0) {
                uf.union(index, topIndex);
            }
            if (hasN) {
                // check if this made any of the bottom nodes connected
                // to the top
                for (int b = gridSize-1; b >= gridSize-rowLen; b--) {
                    if (isOpen(b) && uf.connected(topIndex, b)) {
                        uf.union(b, bottomIndex);
                        break;
                    }
                }
            } else if (1 == gridSize) {
                uf.union(index, bottomIndex);
            }
        }
    }
   
   public boolean isOpen(int index) {    // is site (row i, column j) open?
     return grid[index];
   }
   
   public boolean isFull(int i, int j) {    // is site (row i, column j) full?
      checkInput(i, j);
      return uf.connected(topIndex, getIndex(i-1, j-1));
    }
   
   public boolean percolates() {
        if (!percolates) {
            percolates = uf.connected(topIndex, bottomIndex);
        }
        return percolates;
   }
   
   private void checkInput(int i, int j) {
        if (i < 1 || i > rowLen) {
            throw new java.lang.IndexOutOfBoundsException(
                "i must be between 1 and "+ rowLen
            );
        }
        if (j < 1 || j > rowLen){
            throw new java.lang.IndexOutOfBoundsException(
                "j must be between 1 and "+ rowLen
            );
        }
    }
   
    public int getIndex(int i, int j) {
        return i*rowLen + j;
    }
   
    private int getNeighborIndex(int i, int j, int d) {
        if (d < 0 || d > 3) {
            throw new java.lang.IllegalArgumentException(
                "Direction must be between 0 and 3"
            );
        }
        switch (d) {
            case 0:  // UP
                if (0 == i) {
                    return -1;
                }
                return getIndex(i-1, j);
            case 1:  // RIGHT
                if (j+1 == rowLen) {
                    return -1;
                }
                return getIndex(i, j+1);
            case 2:  // DOWN
                if (1+i == rowLen) {
                    return -1;
                }
                return getIndex(i+1, j);
            case 3:  // LEFT
                if (0 == j) {
                    return -1;
                }
                return getIndex(i, j-1);
            default:
        }
        return -1;        
    }

   public static void main(String[] args) {  // test client (optional)
   
     Percolation p = new Percolation(4);
     p.open(1, 1);
     p.open(2, 1);
     p.open(2, 2);
     p.open(3, 2);
     p.open(3, 3);
     p.open(4, 3);
     System.out.println("Percolates: " + p.percolates());
   }
}