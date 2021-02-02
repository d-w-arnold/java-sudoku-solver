package com.deltawhiskeyalpha;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * JavaFX App
 *
 * @author David W. Arnold
 * @version 02/02/2021
 */
public class App extends Application
{
    private static int[][] solvedBoard;

    public static void main(String[] args)
    {
        int[][] board = {
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
        int[][] r = Sudoku.randomiseBoard(board);
        Sudoku.printBoard("Randomised Sudoku Board:", r);
        Sudoku s = new Sudoku(r);
        s.solve();
        solvedBoard = s.getBoard();
        Sudoku.printBoard("Completed Sudoku Board:", solvedBoard);
        launch();
    }

    @Override
    public void start(Stage primaryStage)
    {
        int X = 9;
        int Y = 9;
        MyNode[][] myBoard = new MyNode[X][Y];
        Group root = new Group();

        // Initialize myBoard
        double SCENE_WIDTH = 360;
        double SCENE_HEIGHT = 360;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                // Create node
                String val = Integer.toString(solvedBoard[j][i]);
                double gridWidth = SCENE_WIDTH / X;
                double gridHeight = SCENE_HEIGHT / Y;
                MyNode node = new MyNode(val, i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                // Add node to group
                root.getChildren().add(node);
                // Add to myBoard for further reference using an array
                myBoard[i][j] = node;
            }
        }

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Completed Sudoku Board");
        primaryStage.show();
    }

    public static class MyNode extends StackPane
    {
        public MyNode(String name, double x, double y, double width, double height)
        {
            // create rectangle
            Rectangle rectangle = new Rectangle(width, height);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.WHITE);

            // create label
            Label label = new Label(name);

            // set position
            setTranslateX(x);
            setTranslateY(y);

            getChildren().addAll(rectangle, label);
        }
    }
}