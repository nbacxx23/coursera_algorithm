import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] board;
    private final int[][] goal;
    private Board twin;
    private boolean twinCalculation = false;

    public Board(int[][] tiles) {
        if (tiles==null) throw new IllegalArgumentException();
        board = new int[tiles.length][tiles.length];
        for(int i = 0; i< tiles.length; i++) {
            System.arraycopy(tiles[i], 0, board[i], 0, board.length);
        }
        goal = calculateGoal(dimension());
    }

    private int[][] calculateGoal(int dimension) {
        int[][] goal = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                goal[i][j] = i * dimension + j + 1;
            }
        }
        goal[dimension - 1][dimension - 1] = 0;
        return goal;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(board.length);
        for (int[] ints : board) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                line.append(ints[j]).append(" ");
            }
            sb.append("\n").append(line);
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != (i * board.length + j + 1)) {
                    hammingDistance += 1;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int number = board[i][j];
                if (number == 0) continue;
                int x = (number-1)/board.length;
                int y;
                if (number%board.length==0) y=board.length-1;
                else y = number%board.length - 1;
                manhattanDistance += Math.abs(x-i) + Math.abs(y-j);
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board.length;j++){
                if (this.board[i][j] != goal[i][j]) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        if (((Board) y).dimension() != this.dimension()) return false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != ((Board) y).board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
        List<int[]> candidates = getCandidates(x, y);
        for (int[] pair : candidates) {
            int[][] clonedBoard = new int[board.length][board.length];
            for(int i = 0; i< board.length; i++) {
                System.arraycopy(board[i], 0, clonedBoard[i], 0, board.length);
            }
            Board newBoard = new Board(clonedBoard);
            newBoard.board[x][y] = board[pair[0]][pair[1]];
            newBoard.board[pair[0]][pair[1]] = 0;
            neighbors.add(newBoard);
        }
        return neighbors;

    }

    private List<int[]> getCandidates(int x, int y) {
        List<int[]> candidatesResult = new ArrayList<>();
        if (!isOutOfBorder(x - 1) && !isOutOfBorder(y)) {
            candidatesResult.add(new int[]{x - 1, y});
        }
        if (!isOutOfBorder(x + 1) && !isOutOfBorder(y)) {
            candidatesResult.add(new int[]{x + 1, y});
        }
        if (!isOutOfBorder(x) && !isOutOfBorder(y + 1)) {
            candidatesResult.add(new int[]{x, y + 1});
        }
        if (!isOutOfBorder(x) && !isOutOfBorder(y - 1)) {
            candidatesResult.add(new int[]{x, y - 1});
        }
        return candidatesResult;
    }

    private boolean isOutOfBorder(int n) {
        return n < 0 || n > dimension() - 1;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twinCalculation) return twin;
        int[][] clonedBoard = new int[board.length][board.length];
        for(int i = 0; i< board.length; i++) {
            System.arraycopy(board[i], 0, clonedBoard[i], 0, board.length);
        }
        Board newBoard = new Board(clonedBoard);
        //Random ran = new Random();
        boolean isQualified = false;
        int x1 = -1;
        int y1 = -1;
        int x2 = -1;
        int y2 = -1;
        while (!isQualified){
            x1 = StdRandom.uniform(dimension());
            y1 = StdRandom.uniform(dimension());
            x2 = StdRandom.uniform(dimension());
            y2 = StdRandom.uniform(dimension());
            if (board[x1][y1]!=0 && board[x2][y2]!=0 && !(x1==x2 && y1==y2)){
                isQualified = true;
            }
        }
        newBoard.board[x2][y2] = board[x1][y1];
        newBoard.board[x1][y1] = board[x2][y2];
        twinCalculation = true;
        twin = newBoard;
        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][]{{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};
        Board testBoard = new Board(tiles);
        System.out.println(testBoard.toString());
        System.out.println(new Board(testBoard.goal).toString());
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.manhattan());
        System.out.println(testBoard.isGoal());
        System.out.println("test goal:");
        System.out.println(new Board(testBoard.goal).isGoal());
        for(Board x:testBoard.neighbors()){
            System.out.println(x.toString());
        }
        System.out.println(testBoard.equals(null));
        System.out.println("origin");
        System.out.println(testBoard.toString());
        System.out.println("twin");
        System.out.println(testBoard.twin().toString());
        System.out.println(testBoard.twin().toString());
    }
}
