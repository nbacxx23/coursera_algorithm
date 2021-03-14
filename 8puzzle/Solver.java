import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private boolean solvable;
    private int moves = -1;
    private List<Board> solution = new ArrayList<>();

    private static class SearchNode{

        private final Board board;
        private final int moves;
        private final SearchNode previousSearchNode;
        private final int manhattanDistance;
        private final int hammingDistance;

        public  SearchNode(Board board, int moves, SearchNode previousSearchNode){
            this.board = board;
            this.moves = moves;
            this.previousSearchNode = previousSearchNode;
            this.manhattanDistance = board.manhattan();
            this.hammingDistance = board.hamming();
        }

        static class HammingComparator implements Comparator<SearchNode> {

            @Override
            public int compare(SearchNode searchNode1, SearchNode searchNode2) {

                // for comparison
                int hammingCompare = Integer.compare(searchNode1.hammingDistance+searchNode1.moves,searchNode2.hammingDistance+searchNode2.moves);
                return hammingCompare;
            }
        }

        static class ManhattanComparator implements Comparator<SearchNode> {

            @Override
            public int compare(SearchNode searchNode1, SearchNode searchNode2) {

                // for comparison
                int manhattanCompare = Integer.compare(searchNode1.manhattanDistance+searchNode1.moves,searchNode2.manhattanDistance+searchNode2.moves);
                return manhattanCompare;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        Board twin = initial.twin();
        MinPQ<SearchNode> minPQ = new MinPQ<>(new SearchNode.ManhattanComparator());
        MinPQ<SearchNode> minPQTwin = new MinPQ<>(new SearchNode.ManhattanComparator());
        minPQ.insert(new SearchNode(initial, 0, null));
        minPQTwin.insert(new SearchNode(twin, 0, null));
        SearchNode finalNode = detectSovablility(minPQ,minPQTwin);
        if (finalNode==null){
            solvable = false;
            return;
        }
        moves = finalNode.moves;
        solvable = true;
    }

    private SearchNode detectSovablility(MinPQ<SearchNode> minPQInitial, MinPQ<SearchNode> minPQTwin) {
        while (minPQInitial.size() > 0 && minPQTwin.size() > 0) {
            SearchNode nextNodeInitial = minPQInitial.delMin();
            SearchNode nextNodeTwin = minPQTwin.delMin();
            if (nextNodeInitial.previousSearchNode != null){
                solution.add(nextNodeInitial.previousSearchNode.board);
            }
            if (nextNodeInitial.board.isGoal() || nextNodeTwin.board.isGoal()) {
                if (nextNodeInitial.board.isGoal()){
                    solution.add(nextNodeInitial.board);
                    return nextNodeInitial;
                }else{
                    return null;
                }
            }
            addNeighbors(minPQInitial, nextNodeInitial);
            addNeighbors(minPQTwin, nextNodeTwin);
        }
        return null;
    }

    private void addNeighbors(MinPQ<SearchNode> minPQ,SearchNode node){
        for (Board neighbor : node.board.neighbors()) {
            if (node.previousSearchNode==null || !neighbor.equals(node.previousSearchNode.board)) {
                minPQ.insert(new SearchNode(neighbor, node.moves + 1, node));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if (!isSolvable()){
        return -1;
    }else{
            return moves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } else {
            return solution;
        }
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        System.out.println(args[0]);
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
