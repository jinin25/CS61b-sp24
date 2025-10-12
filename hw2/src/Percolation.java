import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF ufPercolation;
    private WeightedQuickUnionUF ufFull;
    private int virtualTop;
    private  int virtualBottom;
    private int N;
    private int openNumber;

    public Percolation(int N) {
        this.N = N;
        grid = new boolean[N][N];
        ufPercolation = new WeightedQuickUnionUF(N * N + 2);
        ufFull = new WeightedQuickUnionUF(N * N + 1);
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        openNumber = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        openNumber += 1;

        int index = xyTo1D(row, col);
        int[] rowArray = new int[]{1, 0, 0, -1};
        int[] colArray = new int[]{0, -1, 1, 0};

        for (int i = 0; i < 4; i++) {
            if(row + rowArray[i] >= 0 && row + rowArray[i] < N && col + colArray[i] >= 0 && col + colArray[i] < N) {
                if (isOpen(row + rowArray[i], col + colArray[i])) {
                    int newIndex = xyTo1D(row + rowArray[i], col + colArray[i]);
                    ufPercolation.union(index, newIndex);
                    ufFull.union(index, newIndex);
                }
            }
        }

        if (row == 0) {
            ufPercolation.union(virtualTop, index);
            ufFull.union(virtualTop, index);
        }
        if (row == N - 1) {
            ufPercolation.union(virtualBottom, index);
        }

    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        return ufFull.connected(virtualTop, index);
    }

    public int numberOfOpenSites() {
        return openNumber;
    }

    public boolean percolates() {
        return ufPercolation.connected(virtualTop, virtualBottom);
    }



    public int xyTo1D(int row, int col) {
        int index = row * N + col;
        return index;
    }
}
