package com.deltawhiskeyalpha;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application
{
    private final double SCENE_WIDTH = 360;
    private final double SCENE_HEIGHT = 360;
    private final int N = 9;
    private final int M = 9;
    double gridWidth = SCENE_WIDTH / N;
    double gridHeight = SCENE_HEIGHT / M;
    MyNode[][] playfield = new MyNode[N][M];
    private static List<List<Integer>> start;
    private static List<List<Integer>> complete;

    public static void main(String[] args)
    {
        Sudoku game = new Sudoku(true);
        game.printBoard(); // Before solve
        game.solve();
        game.printBoard(); // After solve
        complete = game.board;
        launch();
    }

    @Override
    public void start(Stage primaryStage)
    {
        Group root = new Group();

        // Initialize playfield
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // Create node
                String val = Integer.toString(complete.get(j).get(i));
                MyNode node = new MyNode(val, i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                // Add node to group
                root.getChildren().add(node);
                // Add to playfield for further reference using an array
                playfield[i][j] = node;
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