import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Boolean[] openFlag;
    private int size;
    private WeightedQuickUnionUF unionFind;
    // creates n-by-n grid, with all sites initially blocked

    public Percolation(int n){
        size = n;
        openFlag = new Boolean[n*n+2];
        unionFind = new WeightedQuickUnionUF(n*n+2);
        for (int i=1;i<=n;i++)
            for(int j=1;j<=n;j++){
                openFlag[(i-1)*n+j]=false;
            }
    }

    private void openRight(int row,int col){
        if (col <size){
        if (isOpen(row, col + 1)) {
            unionFind.union((row - 1) * size + col, (row - 1) * size + col + 1);
        }
    }
    }

    private void openLeft(int row,int col){
        if (col >1){
        if (isOpen(row, col - 1)) {
            unionFind.union((row - 1) * size + col, (row - 1) * size + col - 1);
        }
    }
    }

    private void openUp(int row,int col) {
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                unionFind.union((row - 1) * size + col, (row - 2) * size + col);
            }
        }
    }

    private void openDown(int row,int col) {
        if (row<size) {
            if (isOpen(row + 1, col)) {
                unionFind.union((row - 1) * size + col, row * size + col);
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        openFlag[(row - 1) * size + col] = true;
        if (row == 1) {
            unionFind.union((row - 1) * size + col, 0);
            openRight(row,col);
            openLeft(row,col);
            openDown(row,col);
        }
        if (row == size) {
            unionFind.union((row - 1) * size + col, size * size + 1);
            openLeft(row,col);
            openRight(row,col);
            openUp(row,col);
        } else{
            openRight(row,col);
            openDown(row,col);
            openUp(row,col);
            openLeft(row,col);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        return openFlag[(row-1)*size + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row!=size+1 && !isOpen(row, col) ){
            return false;
        }
        else{
            int r = unionFind.find((row-1)*size+col);
            if (r==unionFind.find(0)){
                return true;
            }
            else{
                return false;
            }
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int count = 0;
        for(int i=1;i<=size;i++)
            for(int j=1;j<=size;j++){
                if (openFlag[(i-1)*size+j]==true){
                    count +=1;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return isFull(size+1,1);
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation per = new Percolation(5);
    }
}