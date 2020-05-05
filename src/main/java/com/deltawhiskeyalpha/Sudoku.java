package com.deltawhiskeyalpha;

import javafx.util.Pair;

import java.util.*;

/**
 * @author David W. Arnold
 * @version 05/05/2020
 */
public class Sudoku
{
    private static final int[][] BASE = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {3, 4, 5, 6, 7, 8, 9, 1, 2},
            {6, 7, 8, 9, 1, 2, 3, 4, 5},
            {9, 1, 2, 3, 4, 5, 6, 7, 8}
    };
    public List<List<Integer>> board;
    private List<Pair<Integer, Integer>> emptyCoords;
    private Map<Pair<Integer, Integer>, Set<Integer>> tries;

    public Sudoku(boolean randomBoard)
    {
        genBoard(randomBoard);
    }

    /**
     * Generates a random Sudoku board from the basic static 9x9 matrix Sudoku board.
     */
    // TODO Could be improves to generate legal Sudoku board without having to refer to a static board.
    private void genBoard(boolean randomBoard)
    {
        board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (randomBoard) {
                    if (new Random().nextBoolean()) {
                        tmp.add(BASE[i][j]);
                    } else {
                        tmp.add(0);
                    }
                } else {
                    tmp.add(BASE[i][j]);
                }
            }
            board.add(tmp);
        }
        setEmptyCoords();
    }

    /**
     * Prints the Sudoku board to standard output.
     */
    public void printBoard()
    {
        for (List<Integer> integers : board) {
            System.out.println(integers);
        }
    }

    /**
     * Prints the basic static 9x9 matrix Sudoku board to standard output.
     */
    public void printBASE()
    {
        System.out.println(Arrays.deepToString(Sudoku.BASE).replace("[", "\n["));
    }

    /**
     * Starting solving the Sudoku game using backtracking algorithm:
     * Source: https://dev.to/aspittel/how-i-finally-wrote-a-sudoku-solver-177g
     */
    public void solve()
    {
        int index = 0;
        while (index < emptyCoords.size()) {
            var cd = emptyCoords.get(index);
            for (int i = 1; i <= 9; i++) {
                if (!prevUsed(cd, i) && validRow(cd, i) && validCol(cd, i) && validSubMatrix(cd, i)) {
                    board.get(cd.getKey()).set(cd.getValue(), i);
                    Set<Integer> tmp = new HashSet<>();
                    for (int j = 1; j <= i; j++) {
                        tmp.add(j);
                    }
                    tries.put(cd, tmp);
                    index++;
                    break;
                } else if (i == 9) {
                    tries.put(cd, new HashSet<>());
                    index--;
                    break;
                }
            }
        }
        System.out.println("\nSudoku board completed!\n");
    }

    /**
     * Generates a list of all coordinates in the Sudoku game which are empty.
     * each coordinate has an associate HashSet containing values tried at that coordinate.
     */
    private void setEmptyCoords()
    {
        emptyCoords = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            var row = board.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == 0) {
                    emptyCoords.add(new Pair<>(i, j));
                }
            }
        }
        setTries();
    }

    /**
     * Generate the HashMap for recording the numbers tried at each given coordinate.
     */
    private void setTries()
    {
        tries = new HashMap<>();
        for (var coordinate : emptyCoords) {
            tries.put(coordinate, new HashSet<>());
        }
    }

    /**
     * Check is a value has been used at that coordinate before.
     */
    private boolean prevUsed(Pair<Integer, Integer> coord, int value)
    {
        return tries.get(coord).contains(value);
    }

    /**
     * Is a value legal at a given coordinate, for a given row.
     */
    private boolean validRow(Pair<Integer, Integer> coord, int value)
    {
        int rowInd = coord.getKey();
        int colInd = coord.getValue();
        List<Integer> row = board.get(rowInd);
        for (int i = 0; i < row.size(); i++) {
            if (i == colInd) {
                continue;
            }
            if (row.get(i) == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is a value legal at a given coordinate, for a given column.
     */
    private boolean validCol(Pair<Integer, Integer> coord, int value)
    {
        int rowInd = coord.getKey();
        int colInd = coord.getValue();
        for (int i = 0; i < board.size(); i++) {
            if (i == rowInd) {
                continue;
            }
            int cellVal = board.get(i).get(colInd);
            if (cellVal == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is a value legal at a given coordinate, for a given column.
     */
    private boolean validSubMatrix(Pair<Integer, Integer> coord, int value)
    {
        Set<Integer> subMatrixVals = getSubMatrixSet(coord);
        return !subMatrixVals.contains(value);
    }

    /**
     * Get a HashSet of integers in the 3x3 sub matrix of the Sudoku board in which a coordinate resides.
     */
    private Set<Integer> getSubMatrixSet(Pair<Integer, Integer> coord)
    {
        int rowInd = coord.getKey();
        int colInd = coord.getValue();
        if (rowInd < 3 && colInd < 3) {
            return getSubMatrixSetHelper(coord, 3, 3);
        } else if (rowInd < 3 && colInd < 6) {
            return getSubMatrixSetHelper(coord, 3, 6);
        } else if (rowInd < 3 && colInd < 9) {
            return getSubMatrixSetHelper(coord, 3, 9);
        } else if (rowInd < 6 && colInd < 3) {
            return getSubMatrixSetHelper(coord, 6, 3);
        } else if (rowInd < 6 && colInd < 6) {
            return getSubMatrixSetHelper(coord, 6, 6);
        } else if (rowInd < 6 && colInd < 9) {
            return getSubMatrixSetHelper(coord, 6, 9);
        } else if (rowInd < 9 && colInd < 3) {
            return getSubMatrixSetHelper(coord, 9, 3);
        } else if (rowInd < 9 && colInd < 6) {
            return getSubMatrixSetHelper(coord, 9, 6);
        } else if (rowInd < 9 && colInd < 9) {
            return getSubMatrixSetHelper(coord, 9, 9);
        }
        return new HashSet<>();
    }

    /**
     * Helper method for getSubMatrixSet().
     */
    private Set<Integer> getSubMatrixSetHelper(Pair<Integer, Integer> coord, int r, int c)
    {
        Set<Integer> tmp = new HashSet<>();
        for (int i = (r - 1); i >= (r - 3); i--) {
            for (int j = (c - 1); j >= (c - 3); j--) {
                if (coord.getKey() == i && coord.getValue() == j) {
                    continue;
                }
                tmp.add(board.get(i).get(j));
            }
        }
        return tmp;
    }

}
