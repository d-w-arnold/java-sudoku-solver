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
    private List<List<Integer>> board;
    private List<Pair<Pair<Integer, Integer>, Set<Integer>>> emptyCoords;

    public Sudoku()
    {
        genBoard();
    }

    /**
     * Generates a random Sudoku board from the basic static 9x9 matrix Sudoku board.
     */
    // TODO Could be improves to generate legal Suduko board without having to refer to a static board.
    private void genBoard()
    {
        board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (new Random().nextBoolean()) {
                    tmp.add(BASE[i][j]);
                } else {
                    tmp.add(0);
                }
            }
            board.add(tmp);
        }

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
     * Starting solving the Sudoku game using backtracking algorithm:
     * Source: https://dev.to/aspittel/how-i-finally-wrote-a-sudoku-solver-177g
     */
    public void solve()
    {
        setEmptyCoords();
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
                    emptyCoords.add(new Pair<>(new Pair<>(i, j), new HashSet<>()));
                }
            }
        }
    }


}
