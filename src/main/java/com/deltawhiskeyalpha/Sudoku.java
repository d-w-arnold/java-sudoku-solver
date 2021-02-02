package com.deltawhiskeyalpha;

import javafx.util.Pair;

import java.util.*;

/**
 * @author David W. Arnold
 * @version 02/02/2021
 */
public class Sudoku
{
    private final int[][] board;
    private final List<Pair<Integer, Integer>> tbcCells;
    private final Map<Pair<Integer, Integer>, Integer> tbcCellsLastTries;

    public Sudoku(int[][] board)
    {
        this.board = board;
        tbcCells = new ArrayList<>();
        tbcCellsLastTries = new HashMap<>();
    }

    /**
     * Generates a random Sudoku board.
     */
    public static int[][] randomiseBoard(int[][] board)
    {
        return randomiseBoard(board, 5);
    }

    /**
     * Generates a random Sudoku board.
     */
    public static int[][] randomiseBoard(int[][] board, int perRow)
    {
        for (int x = 0; x < board.length; x++) {
            int count = 0;
            for (int y = 0; y < board[x].length; y++) {
                if (new Random().nextBoolean() && count < perRow) {
                    board[x][y] = 0;
                    count++;
                }
            }
        }
        return board;
    }

    /**
     * Prints the Sudoku board to standard output.
     */
    public static void printBoard(String msg, int[][] board)
    {
        System.out.println(msg);
        String xSep = "-------------------------";
        String ySep = "| ";
        System.out.println(xSep);
        for (int x = 0; x < board.length; x++) {
            StringBuilder xStr = new StringBuilder(ySep);
            for (int y = 0; y < board[x].length; y++) {
                xStr.append(board[x][y]).append(" ");
                if ((y + 1) % 3 == 0) {
                    xStr.append(ySep);
                }
            }
            System.out.println(xStr);
            if ((x + 1) % 3 == 0) {
                System.out.println(xSep);
            }
        }
        System.out.println();
    }

    public int[][] getBoard()
    {
        return board;
    }

    private void popDataStructure()
    {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 0) {
                    Pair<Integer, Integer> cell = new Pair<>(x, y);
                    tbcCells.add(cell);
                    tbcCellsLastTries.put(cell, 0);
                }
            }
        }
    }

    private boolean validRow(int n, int x, int y)
    {
        for (int i = 0; i < board[x].length; i++) {
            if (i == y) {
                continue;
            }
            if (board[x][i] == n) {
                return false;
            }
        }
        return true;
    }

    private boolean validCol(int n, int x, int y)
    {
        for (int i = 0; i < board.length; i++) {
            if (i == x) {
                continue;
            }
            if (board[i][y] == n) {
                return false;
            }
        }
        return true;
    }

    private Set<Integer> getSubSquareHelper(int x, int y, int xBound, int yBound)
    {
        Set<Integer> subSquareRes = new HashSet<>();
        for (int i = xBound - 3; i < xBound; i++) {
            for (int j = yBound - 3; j < yBound; j++) {
                if (i == x && j == y) {
                    continue;
                }
                subSquareRes.add(board[i][j]);
            }
        }
        return subSquareRes;
    }

    private Set<Integer> getSubSquare(int x, int y)
    {
        if (x < 3 && y < 3) {
            return getSubSquareHelper(x, y, 3, 3);
        } else if (x < 3 && y < 6) {
            return getSubSquareHelper(x, y, 3, 6);
        } else if (x < 3 && y < 9) {
            return getSubSquareHelper(x, y, 3, 9);
        } else if (x < 6 && y < 3) {
            return getSubSquareHelper(x, y, 6, 3);
        } else if (x < 6 && y < 6) {
            return getSubSquareHelper(x, y, 6, 6);
        } else if (x < 6 && y < 9) {
            return getSubSquareHelper(x, y, 6, 9);
        } else if (x < 9 && y < 3) {
            return getSubSquareHelper(x, y, 9, 3);
        } else if (x < 9 && y < 6) {
            return getSubSquareHelper(x, y, 9, 6);
        } else if (x < 9 && y < 9) {
            return getSubSquareHelper(x, y, 9, 9);
        }
        return new HashSet<>();
    }

    private boolean validSubSquare(int n, int x, int y)
    {
        return !getSubSquare(x, y).contains(n);
    }

    public void solve()
    {
        popDataStructure();
        int index = 0;
        while (index < tbcCells.size()) {
            Pair<Integer, Integer> cell = tbcCells.get(index);
            int x = cell.getKey();
            int y = cell.getValue();
            boolean forceBr = false;
            for (int n = tbcCellsLastTries.get(cell) + 1; n <= board.length; n++) {
                if (validRow(n, x, y) && validCol(n, x, y) && validSubSquare(n, x, y)) {
                    tbcCellsLastTries.put(cell, n);
                    board[x][y] = n;
                    forceBr = true;
                    break;
                }
            }
            if (forceBr) {
                // Found a valid number option for this cell - no row, column or sub square collisions
                index++;
            } else {
                // Cannot find a valid number option for this cell, so backtrack to the last to be completed cell
                tbcCellsLastTries.put(cell, 0);
                board[x][y] = 0;
                index--;
            }
        }
    }
}
